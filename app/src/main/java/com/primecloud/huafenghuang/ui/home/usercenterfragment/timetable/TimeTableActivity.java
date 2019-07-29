package com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.TimeTableAdapter;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.share.ShareUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class TimeTableActivity extends BasePresenterActivity<TimeTablePresenter, TimeTableModel> implements TimeTableContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.income_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.income_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;
    @BindView(R.id.line)
    LinearLayout linearLayout;
    private int pageNumber = 1;
    private int pageSize = 10;
    private boolean isMoreEnd = false;
    private List<TimeTableBean.DataBean> list;
    private TimeTableAdapter adapter;
    private int position;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_income);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent("精品课程");
        ViewUtils.setGone(linearLayout);
        Utils.setSwipeRefreshLayout(refreshLayout);
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setRefreshing(true);
        list = new ArrayList<>();
        initAdapter();
        requestData();
    }

    public void initAdapter() {
        if (adapter == null) {
            adapter = new TimeTableAdapter(R.layout.item_timetable, list);
            recyclerView.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
            adapter.setOnItemChildClickListener(this);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showTimeTableList(TimeTableBean timeTableBean) {
        if (timeTableBean != null) {
            if(timeTableBean.getData().size()==0 &&pageNumber == 1){
                errorLayout.setErrorType(LoadingLayout.NODATA);
            }else {
                errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);;//隐藏暂无内容
            }

            if (refreshLayout.isRefreshing()) {// 正在刷新
                adapter.setNewData(timeTableBean.getData());
            } else {
                adapter.addData(timeTableBean.getData());
            }
            stopRefreshOrLoadMore(timeTableBean.getData());
        }
    }




    private void stopRefreshOrLoadMore(List<TimeTableBean.DataBean> picDocuments) {

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (adapter.isLoading()) {
            if (picDocuments == null || picDocuments.size() == 0) {// 说明是最后一页
                isMoreEnd = true;
                adapter.loadMoreEnd();
            } else {
                isMoreEnd = false;
                adapter.loadMoreComplete();
            }
        }

    }

    @Override
    public void onLoadMoreRequested() {
        if (isMoreEnd) {// 最后一页
            stopRefreshOrLoadMore(null);
        } else {
            ++pageNumber;
            requestData();
        }
    }

    private void requestData() {
        if (NetUtils.isConnected(this)) {
            mPresenter.showTimeTable(MyApplication.getInstance().getUserInfo().getId(), pageNumber, pageSize);
        } else {
            ToastUtils.showToast(this, getResources().getString(R.string.detection_network));
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isMoreEnd = false;
        requestData();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<TimeTableBean.DataBean> dataBeans = adapter.getData();
        TimeTableBean.DataBean dataBean = dataBeans.get(position);
        switch (view.getId()) {
            case R.id.time_good:
                this.position = position;

                int isLike = dataBean.getIsLike();

                if(isLike == 0){// 没有点赞调用点赞接口
                    mPresenter.courseLike(MyApplication.getInstance().getUserInfo().getId(), String.valueOf(dataBean.getId()),position);
                }else{
                    mPresenter.courseDisLike(MyApplication.getInstance().getUserInfo().getId(), String.valueOf(dataBean.getLikedId()),position);
                }
                break;
            case R.id.time_copy:
                Utils.copyToClipBoard(dataBean.getContent(), this);
                mPresenter.handleAmountCourese(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 2, position);
                break;
            case R.id.time_forward:
                ShareUtils shareUtils = new ShareUtils(this);
                mPresenter.handleAmountCourese(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 3, position);
                shareUtils.shareWX(dataBean.getCourseName(), "", "", dataBean.getCoursePic(), dataBean.getShareUrl(), Platform.SHARE_WEBPAGE, null);
                break;
            case R.id.line_course:
                Intent intent = new Intent(this, CourseDetailsActivity.class);
                String chapterId = dataBean.getCourseChapterId();
                if(TextUtils.isEmpty(chapterId)){
                    ToastUtils.showToast(this, "未知异常");
                    return;
                }
                try{
                    intent.putExtra("chapterId", Integer.parseInt(dataBean.getCourseChapterId()));
                    intent.putExtra("courseId", dataBean.getCourseId());
                    startActivity(intent);
                }catch (Exception e){
                    ToastUtils.showToast(this, "未知异常");
                }
                break;
            default:
                break;
        }
    }


    // 分享回調

    private  class ShareCallBack implements PlatformActionListener {

        private  TimeTableBean.DataBean dataBean;
        private int position;
        public ShareCallBack( TimeTableBean.DataBean dataBean, int position){
            this.dataBean = dataBean;
            this.position = position;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            mPresenter.handleAmountCourese(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"", 1, 3, position);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }







    @Override
    public void tableLike(boolean isLike,LikeResultBean.DataBean dataBean,int position) {

        TimeTableBean.DataBean bean = adapter.getItem(position);
        if(isLike){// 点赞成功
            bean.setIsLike(1);
            bean.setLikedId(dataBean.getId());
            bean.setLikeCount(bean.getLikeCount() + 1);
        }else{// 取消点赞成功
            bean.setIsLike(0);
            bean.setLikedId(0);
            bean.setLikeCount(bean.getLikeCount() - 1);
        }
        adapter.notifyItemChanged(position);
    }

    @Override
    public void showHandleAmoutResult(int operateType, int position) {
        TimeTableBean.DataBean dataBean = adapter.getItem(position);
        if(operateType == 2){// 复制
            dataBean.setCopys(dataBean.getCopys() + 1);
        }else{// 转发
            dataBean.setForwards(dataBean.getForwards() + 1);
        }
        adapter.notifyItemChanged(position);
    }
}

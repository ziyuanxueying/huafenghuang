package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.OfflineCourseAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.video.VideoUtils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OfflineCourseFragment extends BasePresenterFragment<CoursePresenter, CourseModel> implements CourseContract.View, SwipeRefreshLayout.OnRefreshListener, OfflineCourseAdapter.RequestLoadMoreListener, OfflineCourseAdapter.OnItemClickListener {


    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.offline_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_offline_no_data)
    TextView no_data;

    private List<CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean> list = null;
    private OfflineCourseAdapter adapter;
    private int currentPage;//当前页数
    private int totalPage;//总页数
    private int chapterId;
    private int courseId;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offline, null);
    }

    @Override
    public void initData() {

        list = new ArrayList<>();

        chapterId = getArguments().getInt("chapterId",-1);
        courseId = getArguments().getInt("courseId",-1);

        iniData();
        swipeRefresh.setRefreshing(true);
        onRefresh();

    }


    @Override
    public void initListener() {
        swipeRefresh.setOnRefreshListener(this);
    }


    public void iniData() {


        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new OfflineCourseAdapter(R.layout.offline_recycler_item, list);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.disableLoadMoreIfNotFullPage();
        } else {
            adapter.notifyDataSetChanged();
        }

        stopRefreshLoading();
    }


    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {
        if (courseDetailBean != null && courseDetailBean.getDataBean() != null) {
            CourseDetailBean.DataBean dataBean = courseDetailBean.getDataBean();
            if (dataBean != null && dataBean.getOfflineCoursePage() != null) {


                currentPage = Integer.parseInt(dataBean.getOfflineCoursePage().getCurPage());
                totalPage = Integer.parseInt(dataBean.getOfflineCoursePage().getTotalPage());

                if (dataBean.getOfflineCoursePage().getRecords() != null && dataBean.getOfflineCoursePage().getRecords().size() > 0) {
                    no_data.setVisibility(View.GONE);

                    if (swipeRefresh.isRefreshing()) {
                        list.clear();
                    }

                    list.addAll(dataBean.getOfflineCoursePage().getRecords());


                }
                else
                {
                    no_data.setVisibility(View.VISIBLE);
                }


            }
            else
            {
                no_data.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            no_data.setVisibility(View.VISIBLE);
        }

        iniData();
    }


    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        iniData();
        no_data.setVisibility(View.VISIBLE);
    }

    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }


    @Override
    public void commentView(CommentBean commentBean) {

    }

    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter.getData()!=null&&position<adapter.getData().size())
        {

            TextView textView = view.findViewById(R.id.offline_recycler_item_title);

            CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean data = (CourseDetailBean.DataBean.OfflineCourseBean.OfflineRecordsBean)adapter.getData().get(position);
            Intent intent = new Intent(getActivity(), OfflineCourseDetailsActivity.class);
            intent.putExtra("courseId",data.getId());

            if (StringUtils.notBlank(textView.getText().toString())&&textView.getText().toString().contains(getContext().getResources().getString(R.string.activity_end)))
            {
                intent.putExtra("isPastDue",true);
            }


            CourseDetailsEvent event = new CourseDetailsEvent();
            event.setType(4);
            EventBus.getDefault().post(event);

            startActivity(intent);
        }

    }


    @Override
    public void onLoadMoreRequested() {

        if (swipeRefresh.isRefreshing())
            return;
        if (currentPage + 1 <= totalPage) {
            refresh((currentPage + 1));
        }
    }


    @Override
    public void onRefresh() {
        refresh(1);
    }


    public void refresh(int pageNumber) {
        if (chapterId!=-1&&courseId!=-1)
        {
            if (MyApplication.getInstance().getUserInfo()!=null&&StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId()))
            {
                mPresenter.courseDetailPresenter(courseId,pageNumber+"","2",chapterId,MyApplication.getInstance().getUserInfo().getId());
            }
            else
            {
                mPresenter.courseDetailPresenter(courseId,pageNumber+"","2",chapterId,null);
            }
        }

    }


    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            adapter.setEnableLoadMore(true);
        }

        if (currentPage >= totalPage) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        list = null;
        adapter = null;
    }
}

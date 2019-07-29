package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.CollectionAdapter;
import com.primecloud.huafenghuang.ui.search.bean.AllDataBean;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectionActivity extends BasePresenterActivity<CollectionPresenter,CollectionModel> implements CollectionContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<CollectionBean.DataBean.recordsBean> collectionBeans;
    CollectionAdapter adapter;
    private int currentPage = 1;
    private int total;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_shouchang));
        recyclerView.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

    }

    @Override
    protected void initEvent() {
        collectionBeans  = new ArrayList<>();
        initAdapter();
        getColletion();
    }

    private void initAdapter() {
        if(adapter == null){
            adapter = new CollectionAdapter(R.layout.item_collection,collectionBeans);
            recyclerView.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this);
            adapter.setOnItemClickListener(this);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCollectionList(CollectionBean collectionBean) {
        List<CollectionBean.DataBean.recordsBean> collectionList = collectionBean.getData().getRecords();
        if (collectionList != null) {
            currentPage = collectionBean.getData().getCurPage();
            total = collectionBean.getData().getTotalPage();
            if (refreshLayout.isRefreshing()) {
                collectionBeans.clear();
            }
            if (collectionList != null &&collectionList.size()>0) {
                errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);//隐藏暂无内容
                collectionBeans.addAll(collectionList);
            } else {
                if(currentPage ==1 ){
                    errorLayout.setErrorType(LoadingLayout.NODATA);//暂无内容
                }
            }
            initAdapter();
        } else {
            if (currentPage == 1) {
                errorLayout.setErrorType(LoadingLayout.NODATA);
            }
        }

    }

    @Override
    public void stopLoad() {
        stopRefreshLoading();
    }

    @Override
    public void noDate() {
        if(currentPage == 1){
            errorLayout.setErrorType(LoadingLayout.NODATA);
        }
        stopRefreshLoading();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        getColletion();
    }

    @Override
    public void onLoadMoreRequested() {
        if (refreshLayout.isRefreshing())
            return;
        if (currentPage + 1 <= total) {
            if (NetUtils.isConnected(CollectionActivity.this)) {
                currentPage++;
                mPresenter.getCollectionList(MyApplication.getInstance().getUserInfo().getId(), currentPage);
            } else {
                errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
                ToastUtils.showToast(CollectionActivity.this, getResources().getString(R.string.detection_network));
            }

        }
    }
    //请求数据
    public void getColletion() {
        if (NetUtils.isConnected(this)) {
            mPresenter.getCollectionList(MyApplication.getInstance().getUserInfo().getId(), currentPage);
        } else {
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(CollectionActivity.this, getResources().getString(R.string.detection_network));
        }
    }



    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            adapter.setEnableLoadMore(true);
        }

        if (currentPage >= total) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<CollectionBean.DataBean.recordsBean> data = adapter.getData();
        if(data.get(position).getStatus() ==1){
            ToastUtils.showToast(this,"课程已下架");
            return;
        }
        Intent intent = new Intent(this,CourseDetailsActivity.class);
        intent.putExtra("chapterId",data.get(position).getId());
        intent.putExtra("courseId",data.get(position).getCourseId());
        startActivity(intent);
    }
}

package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.ExpandInfoAdapter;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.IncomeInfoAdapter;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.library.baselibrary.log.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IncomeInfoActivity extends BasePresenterActivity<IncomeInfoPresenter, IncomeInfoModel> implements IncomeInfoContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.income_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.income_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;

    private int pageNumber = 1;
    private int pageSize = 10;
    private boolean isMoreEnd = false;
    private List<IncomeInfoBean.DataBean> list;
    private List<ExpenditureInfoBean.DataBean> expandList;
    private IncomeInfoAdapter adapter;//收益
    private ExpandInfoAdapter expandInfoAdapter;//支出
    private int index;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_income);
    }

    @Override
    protected void initData() {
        index = getIntent().getIntExtra("flag",0);
        Utils.setSwipeRefreshLayout(refreshLayout);
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setRefreshing(true);
        if(index == 1){
            mToolbar.setToolbarTitleContent("收益明细");
            list = new ArrayList<>();
            initAdapter();
        }else if(index == 2){
            mToolbar.setToolbarTitleContent("支出明细");
            expandList = new ArrayList<>();
            initAdapterIncome();
        }
        requestData();
    }

    public void initAdapter(){
        if (adapter == null) {
            adapter = new IncomeInfoAdapter(R.layout.item_income, list);
            recyclerView.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this, recyclerView);
            adapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void initAdapterIncome(){
        if (expandInfoAdapter == null) {
            expandInfoAdapter = new ExpandInfoAdapter(R.layout.item_expand, expandList);
            recyclerView.setAdapter(expandInfoAdapter);
            expandInfoAdapter.setOnLoadMoreListener(this, recyclerView);
            expandInfoAdapter.disableLoadMoreIfNotFullPage(recyclerView);
        } else {
            expandInfoAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void initEvent() {
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showIncomeInfo(IncomeInfoBean infoBean) {
        if(infoBean!=null){
            if(infoBean.getData().size()==0 && pageNumber == 1){
                errorLayout.setErrorType(LoadingLayout.NODATA);
            }else {
                errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);;//隐藏暂无内容
            }
            if (refreshLayout.isRefreshing()) {// 正在刷新
                adapter.setNewData(infoBean.getData());
            } else {
                adapter.addData(infoBean.getData());
            }
            stopRefreshOrLoadMore(infoBean.getData(),null);
        }
    }

    @Override
    public void showExpandInfo(ExpenditureInfoBean infoBean) {
        if(infoBean!=null){
            if(infoBean.getData().size()==0 && pageNumber == 1){
                errorLayout.setErrorType(LoadingLayout.NODATA);
            }else {
                errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);//隐藏暂无内容
            }
            if (refreshLayout.isRefreshing()) {// 正在刷新
                expandInfoAdapter.setNewData(infoBean.getData());
            } else {
                expandInfoAdapter.addData(infoBean.getData());
            }
            stopRefreshOrLoadMore(null,infoBean.getData());
        }
    }

    private void stopRefreshOrLoadMore(List<IncomeInfoBean.DataBean> picDocuments,List<ExpenditureInfoBean.DataBean> expandList) {

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
       if(index==1){
           if (adapter.isLoading()) {
               if (picDocuments == null || picDocuments.size() == 0) {// 说明是最后一页
                   isMoreEnd = true;
                   adapter.loadMoreEnd();
               } else {
                   isMoreEnd = false;
                   adapter.loadMoreComplete();
               }
           }
       }else if(index ==2){
           if (expandInfoAdapter.isLoading()) {
               if (expandList == null || expandList.size() == 0) {// 说明是最后一页
                   isMoreEnd = true;
                   expandInfoAdapter.loadMoreEnd();
               } else {
                   isMoreEnd = false;
                   expandInfoAdapter.loadMoreComplete();
               }
           }
       }
    }

    @Override
    public void onLoadMoreRequested() {
        if (isMoreEnd) {// 最后一页
            stopRefreshOrLoadMore(null,null);
        } else {
            ++pageNumber;
            requestData();
        }
    }

    private void requestData() {
        if(NetUtils.isConnected(this)){
            if(index == 1){
                mPresenter.getIncomeIncoList(MyApplication.getInstance().getUserInfo().getId(),pageNumber,pageSize);
            }else if(index == 2){
                mPresenter.getExpandInfoList(MyApplication.getInstance().getUserInfo().getId(),pageNumber,pageSize);
            }
        }else {
            ToastUtils.showToast(this, getResources().getString(R.string.detection_network));
        }
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        isMoreEnd = false;
        requestData();
    }
}

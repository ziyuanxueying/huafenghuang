package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.OrderAdapter;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderActivity extends BasePresenterActivity<OrderPresenter,OrderModel> implements OrderContract.View,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView orderRecycle;
    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;
    @BindView(R.id.fragment_refresh)
    SwipeRefreshLayout refreshLayout;
    private OrderAdapter orderAdapter;

    private int currentPage = 1;//当前页数
    private int total;//总页数
    private List<OrderBean.DataBean.RecordsBean> orderList;//当前订单数据

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.text_order_title));
        orderRecycle.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);

    }

    @Override
    protected void initEvent() {
        orderList = new ArrayList<>();
        initOrderAdapter();
        getOderList();
    }

    private void initOrderAdapter() {
        if (orderAdapter == null) {
            orderAdapter = new OrderAdapter(R.layout.item_order, orderList);
            orderRecycle.setAdapter(orderAdapter);
            orderAdapter.setOnLoadMoreListener(this);
            orderAdapter.setOnItemClickListener(this);
        } else {
            orderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showOrderList(OrderBean orderBean1) {
        OrderBean.DataBean orderBean = orderBean1.getData();
        total= orderBean.getTotalPage();
        currentPage = orderBean.getCurPage();
        List<OrderBean.DataBean.RecordsBean> list = orderBean.getRecords();
        if(list!=null){
            if(list.size() == 0 && currentPage == 1){
                errorLayout.setErrorType(LoadingLayout.NODATA);//隐藏暂无内容//暂无内容
            }else {
                if (refreshLayout.isRefreshing()) {
                    orderList.clear();
                }
                if (list != null) {
                    errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);;//隐藏暂无内容
                    orderList.addAll(list);
                } else {
                    if(currentPage ==1 ){
                        errorLayout.setErrorType(LoadingLayout.NODATA);//暂无内容
                    }
                }
                initOrderAdapter();
            }

        }else {
            if(currentPage == 1){
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
    public void onRefresh() {//刷新
        refreshLayout.setRefreshing(true);
        currentPage = 1;
        getOderList();
    }

    @Override
    public void onLoadMoreRequested() {
        if (refreshLayout.isRefreshing())
            return;
        if (currentPage + 1 <= total) {
            if (NetUtils.isConnected(OrderActivity.this)) {
                currentPage ++;
                mPresenter.getOrderList(MyApplication.getInstance().getUserInfo().getId(), currentPage);
            } else {
                errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
                ToastUtils.showToast(OrderActivity.this, getResources().getString(R.string.detection_network));
            }

        }
    }

    public void getOderList(){
        if(NetUtils.isConnected(OrderActivity.this)){
            mPresenter.getOrderList(MyApplication.getInstance().getUserInfo().getId(),currentPage);
        }else{
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(OrderActivity.this, getResources().getString(R.string.detection_network));
        }
    }

    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            orderAdapter.setEnableLoadMore(true);
        }
        if (currentPage >= total) {
            orderAdapter.loadMoreEnd();
        } else {
            orderAdapter.loadMoreComplete();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<OrderBean.DataBean.RecordsBean> recordsBeans = adapter.getData();
        OrderBean.DataBean.RecordsBean recordsBean = recordsBeans.get(position);
        //0 -> 线上课、 1 -> 线下课程、 2 -> VIP订单
        if(recordsBean.getOrderType() == 0 || recordsBean.getOrderType() == 1){
            Intent intent = new Intent(this, CourseDetailsActivity.class);
            intent.putExtra("chapterId", recordsBean.getCourseId());
            startActivity(intent);
        }
    }
}

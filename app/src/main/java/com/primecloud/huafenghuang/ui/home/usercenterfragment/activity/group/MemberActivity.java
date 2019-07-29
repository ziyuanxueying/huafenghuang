package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberContract;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberModel;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberPresenter;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.MemberAdapter;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.LoadingLayout;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberBean;
import com.primecloud.library.baselibrary.log.XLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 1.我的会员 2.我的合伙人 3.我的分公司 4.间接分公司
 */
public class MemberActivity extends BasePresenterActivity<MemberPresenter,MemberModel> implements MemberContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.error_layout)
    LoadingLayout errorLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_refresh)
    SwipeRefreshLayout refreshLayout;

    MemberAdapter adapter;
    int flag;//1会员 2 合伙人 3 分公司 4间接分公司
    String title;
    List<MemberBean.DataBean.RecordsBean> memberList;
    int total;
    int currentPage = 1;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void initData() {
        flag = getIntent().getIntExtra("flag",1);
        if(flag==1){
            title = getResources().getString(R.string.text_team_huiyuan);
        }else if(flag==2){
            title = getResources().getString(R.string.text_team_hehuoren);
        }else if(flag==3){
            title = getResources().getString(R.string.text_team_fen);
        }else if(flag==4){
            title = getResources().getString(R.string.text_team_zong);
        }
        mToolbar.setToolbarTitleContent(title);
        errorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
        recyclerView.setLayoutManager(new LinearLayoutManager(MemberActivity.this, LinearLayoutManager.VERTICAL, false));
        memberList = new ArrayList<>();
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        initAdapter();
        getMemberList();
    }

    @Override
    protected void initEvent() {
        initAdapterLinstener();
    }

    private void initAdapterLinstener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<MemberBean.DataBean.RecordsBean> recordsBeanList = adapter.getData();
                MemberBean.DataBean.RecordsBean recordsBean = recordsBeanList.get(position);
                switch (view.getId()){
                    case R.id.huiyuan_call:
                        doSendPhoneTo(recordsBean.getPhone());
                        break;
                    case R.id.huiyuan_liuyan:
                        doSendSMSTo(recordsBean.getPhone(),"");
                        break;
                }
            }
        });
    }

    public void initAdapter(){
        if(adapter == null){
            adapter = new MemberAdapter(R.layout.item_huiyuan,memberList,flag);
            recyclerView.setAdapter(adapter);
            adapter.setOnLoadMoreListener(this);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMemberList(MemberBean  memberBean1) {
        MemberBean.DataBean memberBean = memberBean1.getData();
        total= memberBean.getTotalPage();
        currentPage = memberBean.getCurPage();
        List<MemberBean.DataBean.RecordsBean> list = memberBean.getRecords();

        if(list!=null){
            if(list.size() == 0 && currentPage == 1){
                errorLayout.setErrorType(LoadingLayout.NODATA);//隐藏暂无内容//暂无内容
            }else {
                if (refreshLayout.isRefreshing()) {
                    memberList.clear();
                }
                if (list != null) {
                    errorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);;//隐藏暂无内容
                    memberList.addAll(list);
                } else {
                    if(currentPage ==1 ){
                        errorLayout.setErrorType(LoadingLayout.NODATA);//暂无内容
                    }
                }
                initAdapter();
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
    }

    @Override
    public void onLoadMoreRequested() {
        if (refreshLayout.isRefreshing())
            return;
        if (currentPage + 1 <= total) {
            if (NetUtils.isConnected(MemberActivity.this)) {
                currentPage ++;
                mPresenter.getMemberList(MyApplication.getInstance().getUserInfo().getId(), flag,currentPage);
            } else {
                errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
                ToastUtils.showToast(MemberActivity.this, getResources().getString(R.string.detection_network));
            }

        }
    }
    public void getMemberList(){
        if(NetUtils.isConnected(MemberActivity.this)){
            mPresenter.getMemberList(MyApplication.getInstance().getUserInfo().getId(),flag,currentPage);
        }else{
            errorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
            ToastUtils.showToast(MemberActivity.this, getResources().getString(R.string.detection_network));
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
    public void onRefresh() {
        currentPage = 1;
        getMemberList();
    }


    /**
     * 调起系统发短信功能
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber,String message){
        boolean readSIMCard = Utils.readSIMCard(this);
        if (readSIMCard == true) {
            if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
                intent.putExtra("sms_body", message);
                startActivity(intent);
            }
        }else {
            ToastUtils.showToast(this, "发送失败，请检测是否有SMI卡");
        }
    }

    /**
     * 打电话
     * @param phoneNumber
     */
    @SuppressLint("MissingPermission")
    public void doSendPhoneTo(String phoneNumber){
        boolean readSIMCard = Utils.readSIMCard(MemberActivity.this);
        if (readSIMCard == true) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNumber);
            intent.setData(data);
            startActivity(intent);
        } else {
            ToastUtils.showToast(this, "呼叫失败，请检测是否有SMI卡");
        }
    }
}

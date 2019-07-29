package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.BindBankListAdapter;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BankCardActivity extends BasePresenterActivity<AccountPresenter, AccountModel> implements AccountContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.act_bankcard_rv)
    RecyclerView actBankcardRv;
    @BindView(R.id.act_bankcard_bind)
    Button actBankcardBind;

    private List<BankCardBean> list;
    private BindBankListAdapter adapter;



    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_bankcard);
    }

    @Override
    protected void initData() {
        actBankcardRv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new BindBankListAdapter(R.layout.item_userbankcard_list, list, false);
        actBankcardRv.setAdapter(adapter);
        mToolbar.setToolbarTitleContent("银行卡");

    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserBankcardList(MyApplication.getInstance().getUserInfo().getId());
    }

    @OnClick(R.id.act_bankcard_bind)
    public void onViewClicked() {
        startActivity(BindBankNumActivity.class);
    }

    @Override
    public void showMyAccountData(AccountBean.DataBean accountBean) {

    }

    @Override
    public void bindBankCard() {

    }

    @Override
    public void showBankList(List<BankListBean.DataBean> banks) {

    }

    @Override
    public void showUserBankcardList(List<BankCardBean> bankCardLists) {
        if(bankCardLists != null){
            adapter.setNewData(bankCardLists);
        }
    }

    @Override
    public void unBindBankCard() {
        ToastUtils.showToast(this, "解绑成功");
        mPresenter.getUserBankcardList(MyApplication.getInstance().getUserInfo().getId());
    }

    @Override
    public void bingWxResult() {

    }

    @Override
    public void showBindMethodResult(CatchoutChannelBean catchoutChannelBean) {

    }

    @Override
    public void UnbindingWechat() {

    }

    @Override
    public void catchoutResult() {

    }

    @Override
    public void setDefaultResult() {
        mPresenter.getUserBankcardList(MyApplication.getInstance().getUserInfo().getId());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<BankCardBean> tempList = adapter.getData();
        BankCardBean dataBean = tempList.get(position);

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<BankCardBean> tempList = adapter.getData();
        BankCardBean dataBean = tempList.get(position);

        switch (view.getId()){
            case R.id.item_bankcard_setdefault:
                mPresenter.setDefaultBankcard(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"");
                break;
            case R.id.item_bankcard_delete:
                mPresenter.userUnbindingBankcard(MyApplication.getInstance().getUserInfo().getId(), dataBean.getId()+"");
                break;
        }



    }
}

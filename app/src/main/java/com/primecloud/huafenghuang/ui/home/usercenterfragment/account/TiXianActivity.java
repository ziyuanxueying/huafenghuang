package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.adapter.BindBankListAdapter;
import com.primecloud.huafenghuang.utils.MoneyValueFilter;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.TixianMethodPopup;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TiXianActivity extends BasePresenterActivity<AccountPresenter, AccountModel> implements AccountContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.act_tixian_money)
    TextView actTixianMoney;
    @BindView(R.id.act_tixian_name)
    TextView actTixianName;
    @BindView(R.id.act_tixian_cardnum)
    TextView actTixianCardnum;
    @BindView(R.id.act_tixian_fill)
    EditText actTixianFill;
    @BindView(R.id.act_tixian_ok)
    Button actTixianOk;

    private int cardId;
    private String bankName;
    private String cardNum;
    private int bankcardId;
    private List<BankCardBean> bindBankList;
    private TixianMethodPopup tixianMethodPopup;
    private RecyclerView popupRecyclerView;
    private BindBankListAdapter bindBankListAdapter;
    private int type = -1;// 1 微信 2 银行卡
    private String account = "0.00";


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_tixian);
    }

    @Override
    protected void initData() {

        mToolbar.setToolbarTitleContent("提现");
        bindBankList = new ArrayList<>();

        account = getIntent().getStringExtra("account");
        actTixianMoney.setText(account);

        actTixianFill.setFilters(new InputFilter[]{new MoneyValueFilter(), new InputFilter.LengthFilter(9)});


        tixianMethodPopup = new TixianMethodPopup(this);
        popupRecyclerView = tixianMethodPopup.findViewById(R.id.popup_tixian_recyclerview);
        popupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bindBankListAdapter = new BindBankListAdapter(R.layout.item_userbankcard_list, bindBankList, true);
        popupRecyclerView.setAdapter(bindBankListAdapter);
        mPresenter.getCatchoutChannel(MyApplication.getInstance().getUserInfo().getId());
    }

    @Override
    protected void initEvent() {

        bindBankListAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.act_tixian_ok, R.id.act_tixian_blankview})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.act_tixian_ok:// 提现按钮

                if(type == -1){
                    ToastUtils.showToast(this, "请选择提现方式");
                    return;
                }

                String amount = actTixianFill.getText().toString().trim();
                if(TextUtils.isEmpty(amount)){
                    ToastUtils.showToast(this, "请输入提现金额");
                    return;
                }

                int amountD = Integer.parseInt(amount);
                double accountD = Double.parseDouble(account);

                if(amountD <=0 ){
                    ToastUtils.showToast(this, "提现金额大于0元");
                    return;
                }

                if(amountD > accountD){
                    ToastUtils.showToast(this, "输入金额超过可提现金额");
                    return;
                }

                if(accountD < 10){
                    ToastUtils.showToast(this, "提现金额必须为10元整数倍");
                    return;
                }

                if(amountD % 10 != 0){
                    ToastUtils.showToast(this, "提现金额必须为10元整数倍");
                    actTixianFill.setText((int)(amountD - amountD % 10)+"");
                    actTixianFill.setSelection(actTixianFill.getText().toString().trim().length());
                    return;
                }

                if(type == 1)
                    bankcardId = -1;

                mPresenter.catchout(MyApplication.getInstance().getUserInfo().getId(), type, amountD * 100, bankcardId);

                break;
            case R.id.act_tixian_blankview:// 选择银行卡
                tixianMethodPopup.showPopupWindow();
                break;
        }
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

    }

    @Override
    public void unBindBankCard() {

    }

    @Override
    public void bingWxResult() {

    }

    @Override
    public void showBindMethodResult(CatchoutChannelBean catchoutChannelBean) {
        if(catchoutChannelBean != null && catchoutChannelBean.getData() != null ){
            if(catchoutChannelBean.getData().getBankcardList() != null){
                List<BankCardBean> tempList = catchoutChannelBean.getData().getBankcardList();
                getDefaultCard(tempList);
                CatchoutChannelBean.DataBean.WeChatBean weChatBean = catchoutChannelBean.getData().getWeChat();
                if(weChatBean != null){
                    BankCardBean bankCardBean = new BankCardBean();
                    bankCardBean.setRealName(weChatBean.getRealName());
                    bankCardBean.setOpenId(weChatBean.getOpenId());
                    bankCardBean.setWx(true);
                    tempList.add(0, bankCardBean);
                }
                bindBankListAdapter.setNewData(tempList);
            }
        }
    }

    @Override
    public void UnbindingWechat() {

    }

    @Override
    public void catchoutResult() {

        startActivity(new Intent(this, TiXianSuccessActivity.class));
        finish();
    }

    @Override
    public void setDefaultResult() {

    }

    private void getDefaultCard(List<BankCardBean> tempList) {
        for(BankCardBean bankCardBean: tempList){

            if(bankCardBean.isIsDefault()){
                actTixianName.setText(bankCardBean.getBankName());
                cardNum = bankCardBean.getCardNumber();
                actTixianCardnum.setText(Utils.cardNumFormat(cardNum));
                bankcardId = bankCardBean.getId();
                type = 2;
                break;
            }

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BankCardBean bankCardBean = (BankCardBean) adapter.getItem(position);
        if(bankCardBean.isWx()){// 选择微信提现
            actTixianName.setText("微信提现");
            actTixianName.setText(bankCardBean.getRealName());
            type = 1;
        }else{// 银行卡
            actTixianName.setText(bankCardBean.getBankName());
            cardNum = bankCardBean.getCardNumber();
            actTixianCardnum.setText(Utils.cardNumFormat(cardNum));
            bankcardId = bankCardBean.getId();
            type = 2;
        }

        tixianMethodPopup.dismiss();
    }


}

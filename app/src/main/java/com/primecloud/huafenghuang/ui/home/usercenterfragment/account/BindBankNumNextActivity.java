package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.AppManager;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BindBankNumNextActivity extends BasePresenterActivity<AccountPresenter, AccountModel> implements AccountContract.View {
    @BindView(R.id.change_phone1)
    EditText changePhone1;
    @BindView(R.id.change_yanzhengma1)
    EditText changeYanzhengma1;
    @BindView(R.id.count_phone1)
    CountdownView countPhone1;
    @BindView(R.id.btn_next_change01)
    Button btnNextChange01;

    private String cardNumber;
    private String cardName;// 银行卡名
    private String name;// 持卡人名字
    private String bankId;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.fragment_change_phone01);
    }

    @Override
    protected void initData() {

        mToolbar.setToolbarTitleContent("绑定银行卡");

        btnNextChange01.setText("确认绑定");

        cardNumber = getIntent().getStringExtra("cardNum");
        cardName = getIntent().getStringExtra("bankName");// 银行卡名
        name = getIntent().getStringExtra("name");// 持卡人名字
        bankId = getIntent().getStringExtra("bankId");
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.count_phone1, R.id.btn_next_change01})
    public void onViewClicked(View view) {

        String phone = changePhone1.getText().toString().trim();
        if(TextUtils.isEmpty(phone)){
            // 重置验证码倒计时控件
            countPhone1.resetState();
            ToastUtils.showToast(this, "请输入手机号");
            return;
        }

        if (!RegexValidateUtil.checkCellphone(phone)){
            countPhone1.resetState();
            ToastUtils.showToast(this, "手机号格式不正确");
            return;
        }

        switch (view.getId()) {
            case R.id.count_phone1:

                mPresenter.sendBankSmsCode(phone);
                break;
            case R.id.btn_next_change01:
                String smsCode = changeYanzhengma1.getText().toString().trim();

                if(TextUtils.isEmpty(smsCode)){
                    ToastUtils.showToast(this, "请输入验证码");
                    return;
                }


                mPresenter.userBindingBankcard(MyApplication.getInstance().getUserInfo().getId()
                , cardNumber, name, bankId, smsCode, phone);

                break;
        }
    }

    @Override
    public void showMyAccountData(AccountBean.DataBean accountBean) {

    }

    @Override
    public void bindBankCard() {
        Activity activity = AppManager.getInstance().getActivity(BindBankNumActivity.class);
        if(activity != null){
            activity.finish();
        }
        finish();
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

    }

    @Override
    public void UnbindingWechat() {

    }

    @Override
    public void catchoutResult() {

    }

    @Override
    public void setDefaultResult() {

    }
}

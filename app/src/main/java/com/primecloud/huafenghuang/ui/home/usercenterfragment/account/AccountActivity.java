package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankCardBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.realnameauth.RealNameAuthActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo.IncomeInfoActivity;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.ShareUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;

public class AccountActivity extends BasePresenterActivity<AccountPresenter, AccountModel> implements AccountContract.View, ShareUtils.WQLoginLinstener {
    @BindView(R.id.act_aacount_money)
    TextView actAacountMoney;
    @BindView(R.id.act_aacount_total)
    TextView actAacountTotal;
    @BindView(R.id.act_aacount_bindcard)
    SettingBar actAacountBindcard;
    @BindView(R.id.act_aacount_bindwx)
    SettingBar actAacountBindwx;
    @BindView(R.id.act_aacount_auth)
    SettingBar actAacountAuth;
    @BindView(R.id.act_aacount_getdetail)
    SettingBar actAacountGetdetail;
    @BindView(R.id.act_aacount_paydetail)
    SettingBar actAacountPaydetail;
    @BindView(R.id.line)
    LinearLayout linearLayout;
    private boolean isEnableCatchout;// 是否允许提现
    private boolean isIsBindingRealName;// 是否已经实名认证
    private boolean isIsBindingWechat;//微信绑定
    private boolean isIsBindingBandCard;

    private String idCard;
    private String realName;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_account);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent("我的账户");
        ViewUtils.setGone(linearLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyAccountFirstPage(MyApplication.getInstance().getUserInfo().getId());
    }

    @Override
    protected void initEvent() {

    }



    @OnClick({R.id.act_aacount_bindcard, R.id.act_aacount_bindwx, R.id.act_aacount_auth, R.id.act_aacount_getdetail, R.id.act_aacount_paydetail, R.id.act_aacount_tixian})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.act_aacount_bindcard:
                intent = new Intent(this, BankCardActivity.class);
                break;
            case R.id.act_aacount_bindwx:
                if(isIsBindingWechat){
                    setDialog();
                }else{
                    ShareUtils.qqWxLogin(this, 1, this);
                }
                break;
            case R.id.act_aacount_auth:
                intent = new Intent(this, RealNameAuthActivity.class);
                intent.putExtra("idCard", idCard);
                intent.putExtra("realName", realName);
                break;
            case R.id.act_aacount_getdetail:
                intent = new Intent(this, IncomeInfoActivity.class);
                intent.putExtra("flag",1);
                break;
            case R.id.act_aacount_paydetail:
                intent = new Intent(this, IncomeInfoActivity.class);
                intent.putExtra("flag",2);
                break;
            case R.id.act_aacount_tixian:
                if(!isEnableCatchout){
                    ToastUtils.showToast(this, "您可在每月6-8日进行提现申请");
                    return;
                }

                if(!isIsBindingRealName){
                    intent = new Intent(this, RealNameAuthActivity.class);
                    intent.putExtra("idCard", idCard);
                    intent.putExtra("realName", realName);
                    startActivity(intent);
                    return;
                }

                if(!isIsBindingWechat && !isIsBindingBandCard){
                    ToastUtils.showToast(this, "请绑定微信或者添加银行卡");
                    return;
                }

                intent = new Intent(this, TiXianActivity.class);
                intent.putExtra("account", actAacountMoney.getText().toString().trim());

                break;
        }

        if(intent != null){
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        if(saveDialog != null && saveDialog.isShowing()){
            saveDialog.dismiss();
        }
        super.onDestroy();
    }

    private Dialog saveDialog;
    public void setDialog(){
        saveDialog = DialogUtils.saveDialog(this, getResources().getString(R.string.string_bang_no_safe), getResources().getString(R.string.choose_photo_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.userUnbindingWechat(MyApplication.getInstance().getUserInfo().getId());
                saveDialog.dismiss();
            }
        });
        saveDialog.show();
    }

    @Override
    public void showMyAccountData(AccountBean.DataBean accountBean) {


        DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        actAacountMoney.setText(decimalFormat.format(accountBean.getBalance()/100.00));
        actAacountTotal.setText("累计总收入："+decimalFormat.format(accountBean.getTotalIncome()/100.00));

        isEnableCatchout = accountBean.isIsEnableCatchout();

        isIsBindingBandCard = accountBean.isBindingBankCard();

        isIsBindingRealName = accountBean.isIsBindingRealName();
        if(isIsBindingRealName){
            actAacountAuth.setRightText("已认证");
        }else{
            actAacountAuth.setRightText("未认证");
        }

        isIsBindingWechat = accountBean.isIsBindingWechat();
        if(isIsBindingWechat){
            actAacountBindwx.setRightText("已绑定");
        }else{
            actAacountBindwx.setRightText("未绑定");
        }

        idCard = accountBean.getiDCard();
        realName = accountBean.getRealName();
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


    /**
     * 微信绑定成功
     */
    @Override
    public void bingWxResult() {
        mPresenter.getMyAccountFirstPage(MyApplication.getInstance().userInfo.getId());
    }

    @Override
    public void showBindMethodResult(CatchoutChannelBean catchoutChannelBean) {

    }

    @Override
    public void UnbindingWechat() {
        mPresenter.getMyAccountFirstPage(MyApplication.getInstance().userInfo.getId());
    }

    @Override
    public void catchoutResult() {

    }

    @Override
    public void setDefaultResult() {

    }

    // 微信授权成功
    @Override
    public void onSuccess(Platform platform) {

        PlatformDb platDB = platform.getDb();//获取数平台数据DB
        //通过DB获取各种数据
        mPresenter.userBindingWechat(MyApplication.getInstance().getUserInfo().getId(), platDB.getUserId());

    }
    // 微信授权失败
    @Override
    public void onError(Platform platform, String errMsg) {
        ToastUtils.showToast(this, "授权失败");
    }
}

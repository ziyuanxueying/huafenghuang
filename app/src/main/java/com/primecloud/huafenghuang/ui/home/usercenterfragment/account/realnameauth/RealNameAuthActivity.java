package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.realnameauth;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.TipsFillBar;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RealNameAuthActivity extends BasePresenterActivity<RealNameAuthPresenter, RealNameAuthModel> implements RealNameAuthContract.View {
    @BindView(R.id.act_realname_name)
    TipsFillBar actRealnameName;
    @BindView(R.id.act_realname_idcard)
    TipsFillBar actRealnameIdcard;
    @BindView(R.id.act_realname_ok)
    Button actRealnameOk;

    private boolean isIsBindingRealName;// 是否已经实名认证

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_realname_auth);
    }

    @Override
    protected void initData() {

        mToolbar.setToolbarTitleContent("实名认证");
        String idCard = getIntent().getStringExtra("idCard");
        if(!TextUtils.isEmpty(idCard)){
            actRealnameIdcard.setTipEtText(idCard);
        }

        String realName = getIntent().getStringExtra("realName");
        if(!TextUtils.isEmpty(realName)){
            actRealnameName.setTipEtText(realName);
            actRealnameName.getTipEt().setSelection(realName.length());
        }
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.act_realname_ok)
    public void onViewClicked() {
        String realName = actRealnameName.getTipEtText();
        String cardNum = actRealnameIdcard.getTipEtText();
        if(TextUtils.isEmpty(realName)){
            ToastUtils.showToast(this, "请输入真实姓名");
            return;
        }
        if(TextUtils.isEmpty(cardNum)){
            ToastUtils.showToast(this, "请输入身份证号");
            return;
        }
        if(!RegexValidateUtil.checkIDCardNum(cardNum)){
            ToastUtils.showToast(this, "请输入正确的身份证号");
            return;
        }
        mPresenter.userBindingNameAndIDCard(MyApplication.getInstance().getUserInfo().getId(), realName, cardNum);

    }

    @Override
    public void authSuccess() {

        ToastUtils.showToast(this, "实名认证成功");
        finish();
    }

}

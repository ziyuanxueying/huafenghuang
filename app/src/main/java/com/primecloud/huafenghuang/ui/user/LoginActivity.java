package com.primecloud.huafenghuang.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.InputTextHelper;
import com.primecloud.huafenghuang.ui.home.HomeActivity;
import com.primecloud.huafenghuang.ui.user.login.LoginBean;
import com.primecloud.huafenghuang.ui.user.login.LoginContact;
import com.primecloud.huafenghuang.ui.user.login.LoginModel;
import com.primecloud.huafenghuang.ui.user.login.LoginPresenter;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.MD5Utils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.ShareUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BasePresenterActivity<LoginPresenter,LoginModel> implements LoginContact.View {


    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.text_zhuce)
    TextView textZhuce;
    @BindView(R.id.text_forget)
    TextView textForget;

    private InputTextHelper mInputTextHelper;
    private int isLogin;
    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData() {
        mInputTextHelper = new InputTextHelper(btnLogin);
        mInputTextHelper.addViews(loginPhone, loginPassword);
        isLogin = getIntent().getIntExtra("isLogin",0);
    }

    @Override
    protected void initEvent() {

    }
    @OnClick({R.id.text_zhuce, R.id.text_forget, R.id.btn_login,R.id.login_img_wechat,R.id.login_img_qq})
    public void onViewClicked(View view) {
        String phone = loginPhone.getText().toString().trim();
        String pass = loginPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.text_zhuce://注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.text_forget://忘记密码
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.btn_login://登陆
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(LoginActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(pass)) {
                    ToastUtils.showToast(LoginActivity.this, getResources().getString(R.string.string_reminder_password_null));
                    return;
                }
                if (RegexValidateUtil.checkCellphone(phone)) {
                    if(NetUtils.isConnected(LoginActivity.this)){
                        mPresenter.goLogin(phone, MD5Utils.toMD5(pass));
                    }else {
                        ToastUtils.showToast(LoginActivity.this, getResources().getString(R.string.detection_network));
                    }
                } else {
                    ToastUtils.showToast(LoginActivity.this, getResources().getString(R.string.string_reminder_phone));
                }

                break;
            case R.id.login_img_wechat:
                ShareUtils.qqWxLogin(LoginActivity.this,1,null);
                break;
            case R.id.login_img_qq:
                ShareUtils.qqWxLogin(LoginActivity.this,2,null);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }

    @Override
    public void loginResult(String msg) {
        ToastUtils.showToast(this,msg);
    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void loginToActivity(LoginBean loginBean) {
        DialogUtils.showProgressDialogWithMessage(LoginActivity.this,getResources().getString(R.string.loading_user_info));
        MyApplication.doLogin(LoginActivity.this, loginBean.getData());

        if(isLogin == 1){
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            EventBus.getDefault().post(2);
            startActivity(intent);
        }
        DialogUtils.dismiss();
        finish();
    }
}

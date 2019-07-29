package com.primecloud.huafenghuang.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.InputTextHelper;
import com.primecloud.huafenghuang.ui.home.HomeActivity;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.MD5Utils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.ShareUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends CommonBaseActivity {

    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_yanzhengma)
    EditText registerYanZheng;
    @BindView(R.id.register_yaoqing)
    EditText registerYaoqing;//邀请码 选填
    @BindView(R.id.cv_password_forget_countdown)
    CountdownView mCountdownView;
    //    @BindView(R.id.tv_check_number)
//    TextView tvCheckNumber;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.img_register_wechat)
    ImageView imgRegisterWechat;
    @BindView(R.id.img_register_qq)
    ImageView imgRegisterQq;


    private InputTextHelper mInputTextHelper;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.string_text_regedit_title));
        mToolbar.setToolbarBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mInputTextHelper = new InputTextHelper(btnRegister);
        mInputTextHelper.addViews(registerPhone, registerYanZheng, registerPassword);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.btn_register, R.id.cv_password_forget_countdown, R.id.img_register_wechat, R.id.img_register_qq})
    public void onViewClicked(View view) {
        String phone = registerPhone.getText().toString().trim();//手机号
        String pass = registerPassword.getText().toString().trim();//密码
        String yaoqingma = registerYaoqing.getText().toString().trim();//邀请码
        String num = registerYanZheng.getText().toString().trim();//验证码
        switch (view.getId()) {
            case R.id.btn_register://注册
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(num)) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_yan_null));
                    return;
                }
                if (StringUtils.isEmpty(pass)) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_password_null));
                    return;
                }
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_phone));
                    return;
                }
                if (!RegexValidateUtil.checkPassWord(pass)) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_password));
                    return;
                }
                registerUser(phone, pass, num, yaoqingma);
                break;
            case R.id.cv_password_forget_countdown://获取验证码
                if (StringUtils.notBlank(phone)) {
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_phone));
                        mCountdownView.resetState();
                        return;
                    }
                    getCheckNumber(phone);
                } else {
                    // 重置验证码倒计时控件
                    mCountdownView.resetState();
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                }
                break;
            case R.id.img_register_wechat:
                ShareUtils.qqWxLogin(RegisterActivity.this, 1, null);
                break;
            case R.id.img_register_qq:
                ShareUtils.qqWxLogin(RegisterActivity.this, 2, null);
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    public void registerUser(String phone, String pass, String code, String yaoqingma) {
        if (NetUtils.isConnected(RegisterActivity.this)) {
            FengHuangApi.register(phone, MD5Utils.toMD5(pass), code, yaoqingma, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    UserInfo userInfo = JSON.parseObject(body.getData(), UserInfo.class);
                    DialogUtils.showProgressDialogWithMessage(RegisterActivity.this, getResources().getString(R.string.loading_user_info));
                    MyApplication.doLogin(RegisterActivity.this, userInfo);
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    EventBus.getDefault().post(3);
                    startActivity(intent);
                    DialogUtils.dismiss();
                    finish();
                }

                @Override
                public void onFailure(String data, String errorMsg) {
                }
            });
        } else {
            ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.detection_network));
        }

    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    public void getCheckNumber(String phone) {
        DialogUtils.showProgressDialogWithMessage(RegisterActivity.this, "正在获取验证码");
        if (NetUtils.isConnected(RegisterActivity.this)) {
            FengHuangApi.sendMsg(phone, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.string_reminder_yan));
                    DialogUtils.dismiss();
                }

                @Override
                public void onFailure(String data, String errorMsg) {
                    DialogUtils.dismiss();
                }
            });
        } else {
            ToastUtils.showToast(RegisterActivity.this, getResources().getString(R.string.detection_network));
            DialogUtils.dismiss();
        }

    }

    @Override
    protected void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }
}

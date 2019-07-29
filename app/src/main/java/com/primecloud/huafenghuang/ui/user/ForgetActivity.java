package com.primecloud.huafenghuang.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.helper.InputTextHelper;
import com.primecloud.huafenghuang.utils.MD5Utils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends CommonBaseActivity {

    @BindView(R.id.forget_phone)
    EditText forgetPhone;
    @BindView(R.id.forget_yanzhengma)
    EditText forgetYanzhengma;
    @BindView(R.id.tv_check_number_forget)
    CountdownView tvCheckNumber;
    @BindView(R.id.forget_password)
    EditText forgetPassword;
    @BindView(R.id.btn_forget)
    Button btnForget;

    InputTextHelper inputTextHelper;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_forget);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.string_text_forget_title));
        mToolbar.setToolbarBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputTextHelper = new InputTextHelper(btnForget);
        inputTextHelper.addViews(forgetPhone, forgetPassword, forgetYanzhengma);

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.btn_forget, R.id.tv_check_number_forget})
    public void onViewClicked(View view) {
        String phone = forgetPhone.getText().toString().trim();//手机号
        String pass = forgetPassword.getText().toString().trim();//新密码
        String num = forgetYanzhengma.getText().toString().trim();//验证码
        switch (view.getId()) {
            case R.id.btn_forget://忘记密码
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(num)) {
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_yan_null));
                    return;
                }
                if (StringUtils.isEmpty(pass)) {
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_password_null));
                    return;
                }
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_phone));
                    return;
                }
                if (!RegexValidateUtil.checkPassWord(pass)) {
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_password));
                    return;
                }
                forgetPassword(phone, pass, num);
                break;
            case R.id.tv_check_number_forget://获取验证码
                if (StringUtils.notBlank(phone)) {
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_phone));
                        tvCheckNumber.resetState();
                        return;
                    }
                    getCheckNumber(phone);
                } else {
                    tvCheckNumber.resetState();
                    ToastUtils.showToast(ForgetActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                }
                break;
            default:
                break;
        }

    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getCheckNumber(String phone) {
        DialogUtils.showProgressDialogWithMessage(ForgetActivity.this, getResources().getString(R.string.string_reminder_yan_load));
        FengHuangApi.userForgetPassword(phone, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ToastUtils.showToast(ForgetActivity.this,getResources().getString(R.string.string_reminder_yan));
                DialogUtils.dismiss();
            }

            @Override
            public void onFailure(String data, String errorMsg) {
                DialogUtils.dismiss();
            }
        });
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param num
     */
    public void forgetPassword(String phone, String password, String num) {
        FengHuangApi.forgetPassword(phone, MD5Utils.toMD5(password), num, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ToastUtils.showToast(ForgetActivity.this, body.getMessage());
                finish();
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        inputTextHelper.removeViews();
        super.onDestroy();
    }

}

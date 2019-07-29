package com.primecloud.huafenghuang.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class BangDingActivity extends CommonBaseActivity {

    @BindView(R.id.bangding_phone)
    EditText bangdingPhone;
    @BindView(R.id.bangding_yanzhengma)
    EditText bangdingYanzhengma;
    @BindView(R.id.tv_check_number_bangding)
    CountdownView tvCheckNumberBangding;
    @BindView(R.id.btn_bangding)
    Button btnBangding;
    @BindView(R.id.bangding_yaoqing)
    EditText bangdingYaoqing;
    @BindView(R.id.bangding_password)
    EditText bandingPassword;

    private InputTextHelper inputTextHelper;
    private String type; //0=Wechat，1=QQ
    private String uninId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_bang_ding);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent(getResources().getString(R.string.string_text_bangding_title));
        mToolbar.setToolbarBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputTextHelper = new InputTextHelper(btnBangding);
        inputTextHelper.addViews(bangdingPhone, bangdingYanzhengma);
        type = getIntent().getStringExtra("type");
        uninId = getIntent().getStringExtra("uninId");
    }

    @Override
    protected void initEvent() {

    }


    @OnClick({R.id.btn_bangding, R.id.tv_check_number_bangding})
    public void onViewClicked(View view) {
        String phone = bangdingPhone.getText().toString().trim();//手机号
        String num = bangdingYanzhengma.getText().toString().trim();//验证码
        String password = bandingPassword.getText().toString().trim();//密码
        String yao = bangdingYaoqing.getText().toString().trim();//邀请码
        switch (view.getId()) {
            case R.id.btn_bangding://绑定手机号
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(num)) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_yan_null));
                    return;
                }
                if (StringUtils.isEmpty(password)) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_password_null));
                    return;
                }
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_phone));
                    return;
                }
                if (!RegexValidateUtil.checkPassWord(phone)) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_password));
                    return;
                }
                //绑定手机号操作
                bangding(phone, num, yao , password);
                break;
            case R.id.tv_check_number_bangding://获取验证码
                if (StringUtils.notBlank(phone)) {
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_phone));
                        tvCheckNumberBangding.resetState();
                        return;
                    }
                    getCheckNumber(phone);
                } else {
                    tvCheckNumberBangding.resetState();
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_phone_null));
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
        DialogUtils.showProgressDialogWithMessage(BangDingActivity.this, getResources().getString(R.string.string_reminder_yan_load));
        if (NetUtils.isConnected(BangDingActivity.this)) {
            FengHuangApi.userThirdPartyBindingPhone(phone, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.string_reminder_yan));
                    DialogUtils.dismiss();
                }

                @Override
                public void onFailure(String data, String errorMsg) {
                    DialogUtils.dismiss();
                }
            });
        } else {
            ToastUtils.showToast(BangDingActivity.this, getResources().getString(R.string.detection_network));
            DialogUtils.dismiss();
        }
    }

    /**
     * 绑定手机号
     *
     * @param phone
     * @param num
     * @param yao
     */
    public void bangding(String phone, String num, String yao,String password) {
        FengHuangApi.userThirdPartyBindingPhone(type, uninId, phone, num, yao, MD5Utils.toMD5(password),new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                UserInfo userInfo = JSON.parseObject(body.getData(), UserInfo.class);
                userInfo.setToken(MyApplication.getInstance().getToken());
                DialogUtils.showProgressDialogWithMessage(BangDingActivity.this, getResources().getString(R.string.loading_user_info));
                MyApplication.doLogin(BangDingActivity.this, userInfo);
                Intent intent = new Intent(BangDingActivity.this, HomeActivity.class);
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
    }

    @Override
    protected void onDestroy() {
        inputTextHelper.removeViews();
        super.onDestroy();
    }

}

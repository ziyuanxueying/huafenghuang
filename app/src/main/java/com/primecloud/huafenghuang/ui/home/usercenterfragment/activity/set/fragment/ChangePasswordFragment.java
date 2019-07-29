/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.InputTextHelper;
import com.primecloud.huafenghuang.ui.user.BangDingActivity;
import com.primecloud.huafenghuang.ui.user.ForgetActivity;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.MD5Utils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;
import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.widget.toolbar.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 修改密码
 */
public class ChangePasswordFragment extends BaseFragmentV4 implements View.OnClickListener {

    TextView toolbarBack;
    TextView toolbarTitle;
    @BindView(R.id.password_phone)
    EditText passwordPhone1;
    @BindView(R.id.change_yanzhengma)
    EditText changeYanzhengma;
    @BindView(R.id.count_password)
    CountdownView countPassword;
    @BindView(R.id.change_password)
    EditText changePassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.btn_change)
    Button btnChange;

    InputTextHelper inputTextHelper;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        passwordPhone1 = currentView.findViewById(R.id.password_phone);
        changeYanzhengma = currentView.findViewById(R.id.change_yanzhengma);
        countPassword = currentView.findViewById(R.id.count_password);
        changePassword = currentView.findViewById(R.id.change_password);
        newPassword = currentView.findViewById(R.id.new_password);
        btnChange = currentView.findViewById(R.id.btn_change);
        inputTextHelper = new InputTextHelper(btnChange);
        inputTextHelper.addViews(changeYanzhengma,changePassword,newPassword);
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_safe_password));
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        passwordPhone1.setText(MyApplication.getInstance().getUserInfo().getPhone());
        passwordPhone1.setEnabled(false);
        passwordPhone1.setFocusable(false);
        passwordPhone1.setKeyListener(null);//重点

    }


    @Override
    public void initListener() {
        countPassword.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = passwordPhone1.getText().toString().trim();
        String yanzhengma = changeYanzhengma.getText().toString().trim();
        String changeP = changePassword.getText().toString().trim();
        String newP = newPassword.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_change:
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(yanzhengma)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_yan_null));
                    return;
                }
                if (StringUtils.isEmpty(changeP) || StringUtils.isEmpty(newP)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_password_null));
                    return;
                }
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone));
                    return;
                }
                if (!RegexValidateUtil.checkPassWord(changeP) || !RegexValidateUtil.checkPassWord(newP)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_password));
                    return;
                }
                //忘记密码操作
                forgetPassword(phone,MD5Utils.toMD5(changeP),yanzhengma);
                break;
            case R.id.count_password:
                if(StringUtils.notBlank(phone)){
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone));
                        countPassword.resetState();
                        return;
                    }
                    //获取验证码操作
                    getCheckNumber(phone);
                }else{
                    countPassword.resetState();
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone_null));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        inputTextHelper.removeViews();
        super.onDestroy();
    }
    /**
     * 获取验证码
     * @param phone
     */
    public void getCheckNumber(String phone) {
        DialogUtils.showProgressDialogWithMessage(getActivity(), getResources().getString(R.string.string_reminder_yan_load));
        FengHuangApi.userForgetPassword(phone, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ToastUtils.showToast(getActivity(),getResources().getString(R.string.string_reminder_yan));
                DialogUtils.dismiss();
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

    /**
     * 修改密码
     * @param phone
     * @param password
     * @param num
     */
    public void forgetPassword(String phone, String password, String num) {
        FengHuangApi.forgetPassword(phone, MD5Utils.toMD5(password), num, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ToastUtils.showToast(getActivity(), body.getMessage());
                getActivity().finish();
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }
}


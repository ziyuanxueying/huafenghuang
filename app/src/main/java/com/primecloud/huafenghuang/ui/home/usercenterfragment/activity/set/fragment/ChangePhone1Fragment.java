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
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.ui.user.RegisterActivity;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;

/**
 * 修改手机号1
 */
public class ChangePhone1Fragment extends BaseFragmentV4 implements View.OnClickListener {

    TextView toolbarBack;
    TextView toolbarTitle;
    EditText changePhone1;
    EditText changeYanzhengma1;
    CountdownView countPhone1;
    Button btnNextChange01;

    private InputTextHelper mInputTextHelper;

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_phone01, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        changePhone1 = currentView.findViewById(R.id.change_phone1);
        changeYanzhengma1 = currentView.findViewById(R.id.change_yanzhengma1);
        countPhone1 = currentView.findViewById(R.id.count_phone1);
        btnNextChange01 = currentView.findViewById(R.id.btn_next_change01);
        mInputTextHelper = new InputTextHelper(btnNextChange01);
        mInputTextHelper.addViews(changePhone1, changeYanzhengma1);
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_phone_title));
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        changePhone1.setText(MyApplication.getInstance().getUserInfo().getPhone());
        changePhone1.setEnabled(false);
        changePhone1.setFocusable(false);
        changePhone1.setKeyListener(null);//重点
    }


    @Override
    public void initListener() {
        btnNextChange01.setOnClickListener(this);
        countPhone1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = changePhone1.getText().toString().trim();
        String yanzhengma = changeYanzhengma1.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_next_change01:
                if (StringUtils.isEmpty(phone)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone_null));
                    return;
                }
                if (StringUtils.isEmpty(yanzhengma)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_yan_null));
                    return;
                }
                if (!RegexValidateUtil.checkCellphone(phone)) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone));
                    return;
                }
                //验证手机号 --下一步操作
                UIHelper.showSimpleBack(getContext(), SimpleBackPage.Change_Phone2);
                break;
            case R.id.count_phone1:
                if (StringUtils.notBlank(phone)) {
                    //获取验证码操作
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone));
                        countPhone1.resetState();
                        return;
                    }
                    getCheckNumber(phone);
                } else {
                    countPhone1.resetState();
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone_null));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        mInputTextHelper.removeViews();
        super.onDestroy();
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    public void getCheckNumber(String phone) {
        DialogUtils.showProgressDialogWithMessage(getActivity(), "正在获取验证码");
        if (NetUtils.isConnected(getActivity())) {
            FengHuangApi.sendUnbindingPhoneSMS(phone, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_yan));
                    DialogUtils.dismiss();
                }

                @Override
                public void onFailure(String data, String errorMsg) {
                    ToastUtils.showToast(getActivity(), errorMsg);
                    DialogUtils.dismiss();
                }
            });
        } else {
            ToastUtils.showToast(getActivity(), getResources().getString(R.string.detection_network));
            DialogUtils.dismiss();
        }
    }

}


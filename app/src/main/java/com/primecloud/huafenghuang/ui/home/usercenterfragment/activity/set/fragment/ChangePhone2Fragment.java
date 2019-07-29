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
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.RegexValidateUtil;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.CountdownView;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 修改手机号2
 */
public class ChangePhone2Fragment extends BaseFragmentV4 implements View.OnClickListener {

    TextView toolbarBack;
    TextView toolbarTitle;
    EditText changePhone2;
    EditText changeYanzhengma2;
    CountdownView countPhone2;
    Button btnFinish;

    private InputTextHelper mInputTextHelper;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_phone02, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        changePhone2 = currentView.findViewById(R.id.change_phone2);
        changeYanzhengma2 = currentView.findViewById(R.id.change_yanzhengma2);
        countPhone2 = currentView.findViewById(R.id.count_phone2);
        btnFinish = currentView.findViewById(R.id.btn_finish);
        mInputTextHelper = new InputTextHelper(btnFinish);
        mInputTextHelper.addViews(changePhone2, changeYanzhengma2);
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
    }


    @Override
    public void initListener() {
        btnFinish.setOnClickListener(this);
        countPhone2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String phone = changePhone2.getText().toString().trim();
        String yanzhengma = changeYanzhengma2.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_finish:
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
                //验证手机号 完成手机号的修改
                unbind(phone,yanzhengma);
                break;
            case R.id.count_phone2:
                if(StringUtils.notBlank(phone)){
                    //获取验证码操作
                    if (!RegexValidateUtil.checkCellphone(phone)) {
                        ToastUtils.showToast(getActivity(), getResources().getString(R.string.string_reminder_phone));
                        countPhone2.resetState();
                        return;
                    }
                    getCheckNumber(phone);
                }else{
                    countPhone2.resetState();
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
            FengHuangApi.sendBindingNewPhoneSMS(phone, new HttpCallBack<BizResult>() {
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

    public void unbind(String phone,String code){
        if(NetUtils.isConnected(getActivity())){
            FengHuangApi.userUnbindingPhone(MyApplication.getInstance().getUserInfo().getId(),phone,code, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    FengHuangApi.userBindingNewPhone(MyApplication.getInstance().getUserInfo().getId(), phone, code, new HttpCallBack<BizResult>() {
                        @Override
                        public void onSuccess(String data, BizResult body) {
                            ToastUtils.showToast(getActivity(), "修改成功");
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(String data, String errorMsg) {

                        }
                    });
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }
    }
}


/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SettingActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.utils.DeleteDialog;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;
import com.primecloud.library.baselibrary.log.XLog;


/**
 * 关于我们
 */
public class AboutFragment extends BaseFragmentV4 implements View.OnClickListener {


    TextView toolbarBack;
    TextView toolbarTitle;
    TextView aboutVersion;
    SettingBar sbAboutXieyi;
    SettingBar sbAboutPhone;
    String phone;
    String xieyi;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        aboutVersion = currentView.findViewById(R.id.about_version);
        sbAboutXieyi = currentView.findViewById(R.id.sb_about_xieyi);
        sbAboutPhone = currentView.findViewById(R.id.sb_about_phone);
    }

    @Override
    public void initData() {
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        String version = Utils.getVersionName(getActivity());
        aboutVersion.setText("v "+version);
        getAboutInfo();
    }

    @Override
    public void initListener() {
        sbAboutXieyi.setOnClickListener(this);
        sbAboutPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_about_xieyi:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.Change_XieYi);
                break;
            case R.id.sb_about_phone:
                setDialog(phone);
                break;
                default:
                    break;
        }
    }
    DeleteDialog callDialig;
    /**
     * 弹出框
     */
    public void setDialog(final String server_numer) {
        callDialig = DialogUtils.showDeleteDialog(getContext(), server_numer, getResources().getString(R.string.choose_photo_cancel),
                getResources().getString(R.string.call), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO 自动生成的方法存根
                        switch (v.getId()) {
                            case R.id.iknow_alert_dialog_button1:
                                callDialig.dismiss();
                                break;

                            case R.id.iknow_alert_dialog_button2:
                                boolean readSIMCard = Utils.readSIMCard(getContext());
                                if (readSIMCard == true) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    Uri data = Uri.parse("tel:" + server_numer);
                                    intent.setData(data);
                                    startActivity(intent);
                                } else {
                                    ToastUtils.showToast(getContext(), "呼叫失败");
                                }
                                callDialig.dismiss();
                                break;

                            default:
                                break;
                        }
                    }
                });
        callDialig.show();
    }

    public void getAboutInfo(){
        FengHuangApi.aboutUs(new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                JSONObject object = JSONObject.parseObject(body.getData());
                phone = object.getString("tel");
                xieyi = object.getString("agreement");
                sbAboutPhone.setRightText(phone);
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

}


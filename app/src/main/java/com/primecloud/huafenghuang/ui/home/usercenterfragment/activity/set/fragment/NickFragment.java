/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.user.BangDingActivity;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.CircleImageView;
import com.primecloud.huafenghuang.widget.ClearEditText;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;

import org.greenrobot.eventbus.EventBus;


/**
 * 个人中心--修改昵称
 */
public class NickFragment extends BaseFragmentV4{


    TextView toolbarBack;
    TextView toolbarTitle,toolbarConfirm;
    ClearEditText infoNick;
    LinearLayout line;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_nick, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        line = currentView.findViewById(R.id.line);
        toolbarConfirm = currentView.findViewById(R.id.toolbar_confirm);
        infoNick = currentView.findViewById(R.id.info_nick_clear);
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getActivity().getString(R.string.text_nick_title));
        toolbarConfirm.setText(getActivity().getString(R.string.text_nick_save));
        ViewUtils.setGone(line);
        toolbarConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存信息
                UpdataUserName(infoNick.getText().toString().trim());
            }
        });
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        infoNick.setText(MyApplication.getInstance().getUserInfo().getUsername());
    }

    @Override
    public void initListener() {

    }

    public void UpdataUserName(String userName){

        FengHuangApi.updateUserName(MyApplication.getInstance().getUserInfo().getId(), userName, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                UserInfo userInfo = JSON.parseObject(body.getData(), UserInfo.class);
                MyApplication.doLogin(getActivity(), userInfo);
                EventBus.getDefault().post(userName);
                getActivity().finish();
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

}


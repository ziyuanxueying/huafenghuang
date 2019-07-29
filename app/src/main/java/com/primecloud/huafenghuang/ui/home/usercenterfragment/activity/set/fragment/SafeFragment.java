/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ShareBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.UserExtendInfo;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;
import com.primecloud.library.baselibrary.log.XLog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 账号与安全
 */
public class SafeFragment extends BaseFragmentV4 implements View.OnClickListener {

    TextView toolbarBack;
    TextView toolbarTitle;
    SettingBar sbSafePhone;
    SettingBar sbSafeWeixin;
    SettingBar sbSafeQq;
    SettingBar sbSafePassword;
    Dialog saveDialog;
    private int wx;//微信状态
    private int qq;//qq状态
    String weChatUnionId;//微信的unionId
    String qqUnionId;//qq的unionI

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int flag = msg.what; //0微信 1qq
            if(flag == 0){ // 解除绑定 wx
                sbSafeWeixin.setRightText(getResources().getString(R.string.text_safe_wei));
            }else if(flag == 1){
                sbSafeQq.setRightText(getResources().getString(R.string.text_safe_wei));
            }else if(flag == 2){
                sbSafeWeixin.setRightText(getResources().getString(R.string.text_safe_bangding));
            }else if(flag == 3){
                sbSafeQq.setRightText(getResources().getString(R.string.text_safe_bangding));
            }
        }
    };
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_safe, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        sbSafePhone = currentView.findViewById(R.id.sb_safe_phone);
        sbSafeWeixin = currentView.findViewById(R.id.sb_safe_weixin);
        sbSafeQq = currentView.findViewById(R.id.sb_safe_qq);
        sbSafePassword = currentView.findViewById(R.id.sb_safe_password);
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_safe_title));
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        if (null != MyApplication.getInstance().getUserInfo() && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getPhone())) {
            String maskNumber = MyApplication.getInstance().getUserInfo().getPhone().substring(0, 3) + "****" + MyApplication.getInstance().getUserInfo().getPhone().substring(7, MyApplication.getInstance().getUserInfo().getPhone().length());
            sbSafePhone.setRightText(maskNumber);
        }
        getUserInfo();
    }


    @Override
    public void initListener() {
        sbSafePhone.setOnClickListener(this);
        sbSafeWeixin.setOnClickListener(this);
        sbSafeQq.setOnClickListener(this);
        sbSafePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sb_safe_phone:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.Change_Phone1);
                break;
            case R.id.sb_safe_weixin:
                if(wx == 1){
                    setDialog("1");
                }else {
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    authorize(wechat, getActivity());
                }
                break;
            case R.id.sb_safe_qq:
                if(qq == 1){
                    setDialog("2");
                }else {
                    Platform wechat = ShareSDK.getPlatform(QQ.NAME);
                    authorize(wechat, getActivity());
                }
                break;
            case R.id.sb_safe_password:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.Change_Passwprd);
                break;
            default:
                break;
        }
    }

    /**
     * 获取当前用户绑定第三方信息
     */
    private void getUserInfo(){
        if(NetUtils.isConnected(getActivity())){
            FengHuangApi.getUserExtendInformation(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    UserExtendInfo shareInfo = JSON.parseObject(body.getData(), UserExtendInfo.class);
                    String WeiXin = shareInfo.getWeChat();
                    String QQ = shareInfo.getQq();

                    if(StringUtils.notBlank(WeiXin)){
                        wx = 1;//已绑定
                        weChatUnionId = WeiXin;
                        sbSafeWeixin.setRightText(getResources().getString(R.string.text_safe_bangding));
                    }else {
                        wx = 0;//未绑定
                        sbSafeWeixin.setRightText(getResources().getString(R.string.text_safe_wei));
                    }

                    if(StringUtils.notBlank(QQ)){
                        qq = 1;//已绑定
                        qqUnionId = QQ;
                        sbSafeQq.setRightText(getResources().getString(R.string.text_safe_bangding));
                    }else {
                        qq = 0;//未绑定
                        sbSafeQq.setRightText(getResources().getString(R.string.text_safe_wei));
                    }

                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }else {
            ToastUtils.showToast(getActivity(), getResources().getString(R.string.detection_network));
        }

    }

    public void setDialog(String from){
        saveDialog = DialogUtils.saveDialog(getActivity(), getResources().getString(R.string.string_bang_no_safe), getResources().getString(R.string.choose_photo_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from.equals("1")){//微信
                    jiechuBangding("0");
                }else if(from.equals("2")){//qq
                    jiechuBangding("1");
                }
            }
        });
        saveDialog.show();
    }


    // 授权
    private void authorize(Platform plat, final Context context) {
        if (plat == null) {
            return;
        }
//        final String mac = Utils.getMacAddr();
        if (plat.isAuthValid()) {// 是否已经授权
            // Utils.showLog("isAuthValid(");
            plat.removeAccount(true);// 删除授权信息
        }
        plat.setPlatformActionListener(new PlatformActionListener() {


            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onComplete(Platform platform, int arg1, HashMap<String, Object> arg2) {
                // TODO 自动生成的方法存根
                String platName = platform.getName();// 获取平台名称
                String userId = platform.getDb().getUserId();
                String InfoJson = platform.getDb().exportData();
                Gson gson = new Gson();
                ShareBean shareBean = gson.fromJson(InfoJson,ShareBean.class);//用户的基本信息

                String unionid = shareBean.getUnionid();
                XLog.i("plat平台=====" + platName + "   opneId=====" + userId +" unionid"+unionid);


                if(platName.equals("Wechat")){//微信
                    bangding("0",unionid);
                }else if(platName.equals("QQ")){//qq
                    bangding("1",userId);
                }

            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO 自动生成的方法存根

            }
        });
        // 关闭SSO授权
        plat.SSOSetting(false);
        plat.showUser(null);// 获取用户信息
    }
    /**
     * 解除绑定
     * @param from
     */
    public void jiechuBangding(String from){ //0 微信 1qq
        if(NetUtils.isConnected(getActivity())){
            FengHuangApi.userUnbindingOthers(MyApplication.getInstance().getUserInfo().getId(), from, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    Message message = new Message();
                    if(from.equals("0")){
                        wx = 0;
                        message.what = 0;
                    }
                    if(from.equals("1")){
                        qq = 0;
                        message.what = 1;
                    }
                    handler.sendMessage(message);
                    saveDialog.dismiss();
                }

                @Override
                public void onFailure(String data, String errorMsg) {
                    saveDialog.dismiss();
                }
            });
        }else {
            ToastUtils.showToast(getActivity(), getResources().getString(R.string.detection_network));
        }

    }

    /**
     * 绑定
     * @param from
     * @param unionId
     */
    public void bangding(String from,String unionId){ //0 微信 1qq
        if(NetUtils.isConnected(getActivity())){
            FengHuangApi.userBindingOthers(MyApplication.getInstance().getUserInfo().getId(), from, unionId, new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    Message message = new Message();
                    if(from.equals("0")){
                        wx = 1;
                        message.what = 2;
                    }
                    if(from.equals("1")){
                        qq = 1;
                        message.what = 3;
                    }
                    handler.sendMessage(message);
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }else {
            ToastUtils.showToast(getActivity(), getResources().getString(R.string.detection_network));
        }

    }
}


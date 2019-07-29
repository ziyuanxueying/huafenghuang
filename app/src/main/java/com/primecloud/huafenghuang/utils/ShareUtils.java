package com.primecloud.huafenghuang.utils;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ShareBean;
import com.primecloud.huafenghuang.ui.share.ShareInfo;
import com.primecloud.huafenghuang.ui.user.BangDingActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * sharesdk实现分享和第三方登陆
 */

public class ShareUtils {

    private static Context mContext;
    public interface WQLoginLinstener{
        public void onSuccess(Platform platform);
        public void onError(Platform platform,String errMsg);
    }

    /**
     * @param context
     * @param flag 1 微信 2 qq
     * @param linstener
     */
    public static void qqWxLogin(Context context,int flag,WQLoginLinstener linstener){
        Platform platform = null;
        mContext = context;
        switch (flag){
            case 1://wx
                platform = ShareSDK.getPlatform(Wechat.NAME);
                break;
            case 2://qq
                platform = ShareSDK.getPlatform(QQ.NAME);
                break;
        }
        authorize(platform,context,linstener);
    }

    private static void authorize(Platform platform, Context context, final WQLoginLinstener linstener) {



        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if(linstener != null){
                    linstener.onSuccess(platform);
                    return;
                }
                String platName = platform.getName();//获取平台名称
                String openId = platform.getDb().getUserId();//openId
//                String userName = platform.getDb().getUserName();//获取用户名
//                String userIcon = platform.getDb().getUserIcon();//获取用户头像
//                String userGender = platform.getDb().getUserGender();//获取用户性别、
                String infoJson = platform.getDb().exportData();
                Gson gson = new Gson();
                ShareBean shareBean = gson.fromJson(infoJson,ShareBean.class);//用户的基本信息

                if(platName.equals("Wechat")){//微信
                    yanzheng("0",shareBean.getUnionid());
                }else if(platName.equals("QQ")){//qq
                    yanzheng("1",openId);
                }

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if(linstener!=null){
                    linstener.onError(platform,mContext.getResources().getString(R.string.text_shre_error));
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        // 关闭SSO授权
        platform.SSOSetting(false);
        platform.showUser(null);// 获取用户信息
    }

    /**
     * 验证授权接口
     */
    public static  void yanzheng(String type,String uninId){
        FengHuangApi.userThirdPartyLogin(type, uninId, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                ShareInfo shareInfo = JSON.parseObject(body.getData(), ShareInfo.class);
                MyApplication.getInstance().setToken(shareInfo.getToken());
//                -1表示本次登录成功但尚未绑定手机号，0表示已关联手机号
                if(shareInfo.getState()== -1){
                    Intent intent = new Intent(mContext, BangDingActivity.class);
                    intent.putExtra("type",type);
                    intent.putExtra("uninId",uninId);
                    mContext.startActivity(intent);
                }else if(shareInfo.getState() == 0){
                    ShareInfo.UserBean userBean = shareInfo.getUser();
                    Utils.getUserInfo(mContext, String.valueOf(userBean.getId()),1);//加载用户信息
                }
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

}

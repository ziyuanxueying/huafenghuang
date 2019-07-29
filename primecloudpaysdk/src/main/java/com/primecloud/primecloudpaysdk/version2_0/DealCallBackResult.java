package com.primecloud.primecloudpaysdk.version2_0;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class DealCallBackResult implements PaySdkCallBack {

    /**
     * 获取订单信息成功
     */

    void onSuccess(JSONObject orderInfo, PaySDK.PayMethod payMethod) {
        // TODO Auto-generated method stub
        if (orderInfo == null) {
            onError("订单创建失败");
            return;
        }
        PayBean payBean = requestResult(orderInfo, payMethod);
        if (payBean != null) {
            if (payMethod == payMethod.AL_PAY) {// 阿里支付
                PaySDK.getPaySDKInstance(null).createPayInstance(payMethod.AL_PAY).pay(payBean);
            } else if (payMethod == payMethod.WX_PAY) {// 微信支付
                PaySDK.getPaySDKInstance(null).createPayInstance(payMethod.WX_PAY).pay(payBean);
            } else {
                onSuccess(orderInfo);
            }
        } else {
            onError("订单数据解析失败");
        }
    }

    @Override
    public PayBean requestResult(JSONObject orderInfo, PaySDK.PayMethod payMethod) {
        // TODO Auto-generated method stub
        PayBean payBean = null;
        if (payMethod == payMethod.AL_PAY) {// 阿里支付
            payBean = parseALiData(orderInfo);
        } else if (payMethod == payMethod.WX_PAY) {// 微信支付
            payBean = parseWXData(orderInfo);
        } else {
            payBean = parseBalance(orderInfo);
        }
        return payBean;
    }


    // 微信解析和支付宝解析要注意格式

    /**
     * 解析微信数据
     *
     * @param orderInfo
     * @return
     */
    public PayBean parseWXData(JSONObject orderInfo) {
        String appid;
        try {
            orderInfo = orderInfo.getJSONObject("data");
            appid = orderInfo.getString("appid");
            String partnerId = orderInfo.getString("partnerid");// 微信支付分配的商户号
            String prepayId = orderInfo.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
            String nonceStr = orderInfo.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
            String timeStamp = orderInfo.getString("timestamp");// 时间戳，app服务器小哥给出
            String sign = orderInfo.getString("sign");
            String packageValue = orderInfo.getString("package");

            PayBean payBean = new PayBean();
            payBean.setWxAppId(appid);
            payBean.setWxPartnerId(partnerId);
            payBean.setWxPrepayId(prepayId);
            payBean.setWxNonceStr(nonceStr);
            payBean.setWxTimeStamp(timeStamp);
            payBean.setComSign(sign);
            payBean.setWxPackageValue(packageValue);

            return payBean;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析阿里数据
     *
     * @param orderInfo
     * @return
     */

    public PayBean parseALiData(JSONObject orderInfo) {
        try {
            orderInfo = orderInfo.getJSONObject("data");
            String sign = orderInfo.getString("alipay_sign");

            PayBean payBean = new PayBean();
            payBean.setComSign(sign);

            return payBean;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析余额支付
     */
    public PayBean parseBalance(JSONObject orderInfo) {


        try {
            String message = orderInfo.getString("message");

            if (message.equals("订单创建成功!")) {
                PayBean payBean = new PayBean();
                return payBean;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

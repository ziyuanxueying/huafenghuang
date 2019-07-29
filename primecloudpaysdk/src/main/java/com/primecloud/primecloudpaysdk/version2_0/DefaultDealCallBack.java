package com.primecloud.primecloudpaysdk.version2_0;

import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

public class DefaultDealCallBack  extends DealCallBackResult{

    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        Log.e("SDK", "onError:"+error);
    }

    /**
     * 获取订单信息返回的结果,如果返回null，说明获取结果错误，不会向下执行，不会调用支付
     */
    @Override
    public PayBean requestResult(JSONObject orderInfo, PaySDK.PayMethod payMethod) {
        // TODO Auto-generated method stub
        return super.requestResult(orderInfo, payMethod);
    }



    @Override
    public void aLiPayResult(Map<String, String> result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(JSONObject orderInfo) {

    }
}

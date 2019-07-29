package com.primecloud.primecloudpaysdk.version2_0;

import org.json.JSONObject;

import java.util.Map;

interface PaySdkCallBack {

	/**
	 * 成功回调
	 * @param orderInfo
	 *
	 */
	public void onSuccess(JSONObject orderInfo);
	
	/**
	 * 失败的回调
	 * @param error
	 */
	public void onError(String error);
	
	/**
	 * 获取订单信息，解析订单信息，获取的结果进行判
	 * @param orderInfo
	 * @param payMethod
	 * @return true继续支付，false 停止支付
	 */
	public PayBean requestResult(JSONObject orderInfo, PaySDK.PayMethod payMethod);
	
	/**
	 * 阿里支付的结果
	 * @param result
	 */
	public void aLiPayResult(Map<String, String> result);
	
}

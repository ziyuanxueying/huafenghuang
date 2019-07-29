package com.primecloud.primecloudpaysdk;

import android.content.Context;
import android.util.Log;

import com.primecloud.primecloudpaysdk.version2_0.DealCallBackResult;
import com.primecloud.primecloudpaysdk.version2_0.PayBean;
import com.primecloud.primecloudpaysdk.version2_0.PaySDK;

import org.json.JSONObject;

import java.util.Map;

public class TestDealCallBack extends DealCallBackResult {

	private Context context;
	public TestDealCallBack(Context context){
		this.context = context;
	}
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		Log.i("sss", "error:"+error);
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
		Log.i("sss", "  aaaaaaaaaaaresult:"+result.toString());
	}


	@Override
	public void onSuccess(JSONObject orderInfo) {

	}
}

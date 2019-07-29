package com.primecloud.primecloudpaysdk.version2_0;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * 支付宝支付
 * @author zy
 *
 */
public class ALiPay implements PayInterface{

	private ConfigParameter configParameter;
	private Activity context;
	
	public ALiPay(ConfigParameter configParameter, Activity context) {
		this.configParameter = configParameter;
		this.context = context;
	}
	
	@Override
	public void pay(@NonNull PayBean payBean) {
		// TODO Auto-generated method stub
		
		if(payBean == null) {
			throw new NullPointerException("传入的payBean不能为空");
		}
		Log.i("sss", "payBean:"+payBean.toString());
		PayTask alipay = new PayTask(context);
		Map<String, String> result = alipay.payV2(payBean.getComSign(), true);
		Log.i("sss", "result:"+result.toString());
		
		configParameter.getCallBack().aLiPayResult(result);
	}
	

}

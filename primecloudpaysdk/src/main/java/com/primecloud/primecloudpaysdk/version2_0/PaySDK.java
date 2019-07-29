package com.primecloud.primecloudpaysdk.version2_0;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.primecloud.primecloudpaysdk.bean.RequestParamter;


public class PaySDK{

	private static PaySDK paySDK;
	private ConfigParameter configParameter;
	private Activity context;

	// 支付方式 微信， 阿里
	public enum PayMethod {
		WX_PAY, AL_PAY
	}

	
	
	private PaySDK(Activity context) {
		this.context = context;
	}
	
	

	public static PaySDK getPaySDKInstance(Activity context) {
		if (paySDK == null) {
			if(context == null) {
				throw new NullPointerException("Context不能为空");
			}
			paySDK = new PaySDK(context);
		}
		return paySDK;
	}
	
	
	
	

	/**
	 * 注册请求配置
	 * 
	 * @param configParameter
	 */
	public void registerRequestCofig(@NonNull ConfigParameter configParameter) {
		
		if (configParameter == null) {
			throw new NullPointerException("配置参数不能为空");
		}
		
		this.configParameter = configParameter;
	}
	
	
	
	
	/**
	 * 创建不同的支付对象
	 * @param payMethod
	 * @return
	 */
	public PayInterface createPayInstance(@NonNull PayMethod payMethod) {
		
		if (configParameter == null) {
			throw new NullPointerException("请首先注册请求配置ConfigParameter");
		}
		
		switch (payMethod) {
		case WX_PAY:
			return new WXPay(configParameter, context);
			
		case AL_PAY:
			return new ALiPay(configParameter, context);
			
		default:
			break;
		}
		return null;
	}

	
	
	
	/**
	 * 获取订单信息
	 * @param requestParamter 请求数据
	 * @param payMethod 请求方式    阿里支付，，微信支付
	 */
	public void getOrderInfo(@NonNull RequestParamter requestParamter, PayMethod payMethod) {
		
		if (configParameter == null) {
			throw new NullPointerException("请首先注册请求配置ConfigParameter");
		}
		
		if (requestParamter == null) {
			throw new NullPointerException("请求参数不能为空");
		}
		PayHttpUtils.sendRequest(requestParamter, configParameter, payMethod);
	}

	
	
	
	
}
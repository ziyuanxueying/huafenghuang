package com.primecloud.primecloudpaysdk.version2_0;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付
 * 
 * @author zy
 *
 */
public class WXPay implements PayInterface {

	private ConfigParameter configParameter;
	private Activity context;

	public WXPay(ConfigParameter configParameter, Activity context) {

		this.configParameter = configParameter;
		this.context = context;
	}

	/**
	 * 
	 * @param appid
	 * @param partnerid
	 *            微信支付分配的商户号
	 * @param prepayid
	 *            预支付订单号，app服务器调用“统一下单”接口获取
	 * @param noncestr
	 *            随机字符串，不长于32位，服务器小哥会给咱生成
	 * @param timestamp
	 *            时间戳，app服务器小哥给出
	 * @param sign
	 *            签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
	 */
	@Override
	public void pay(@NonNull PayBean payBean) {
		// TODO Auto-generated method stub

		if (payBean == null) {
			throw new NullPointerException("传入的payBean不能为空");
		}

		IWXAPI mWxApi = WXAPIFactory.createWXAPI(context, null);
		
		Log.i("sss", "appId:"+payBean.getWxAppId()+"....."+payBean.toString());
		mWxApi.registerApp(payBean.getWxAppId());

		PayReq req = new PayReq();
		req.appId = payBean.getWxAppId();
		
		req.partnerId = payBean.getWxPartnerId();// 微信支付分配的商户号
		req.prepayId = payBean.getWxPrepayId();// 预支付订单号，app服务器调用“统一下单”接口获取
		req.nonceStr = payBean.getWxNonceStr();// 随机字符串，不长于32位，服务器小哥会给咱生成
		req.timeStamp = payBean.getWxTimeStamp();// 时间戳，app服务器小哥给出
		req.packageValue = payBean.getWxPackageValue();// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
		req.sign = payBean.getComSign();// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
		req.extData = payBean.getWxExtData(); // optional
		mWxApi.sendReq(req);
		
		
		
//		req.appId = "wx895e7e6d1aafc2aa";
//		
//		req.partnerId = "1523073751";// 微信支付分配的商户号
//		req.prepayId = "wx22124118704135644e2f873b3582676243";// 预支付订单号，app服务器调用“统一下单”接口获取
//		req.nonceStr = "14nqefl3eq4ljellxwhvxsdamtyp9983";// 随机字符串，不长于32位，服务器小哥会给咱生成
//		req.timeStamp = "1548131953";// 时间戳，app服务器小哥给出
//		req.packageValue = "Sign=WXPay";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
//		req.sign = "850965EACDFE5B3BE8B8BE774F68221A";// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
//		req.extData = payBean.getWxExtData(); // optional
//		mWxApi.sendReq(req);
	}

}

package com.primecloud.primecloudpaysdk.version1_0;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.primecloud.primecloudpaysdk.version2_0.PaySDKReflectUtils;
import com.primecloud.primecloudpaysdk.version2_0.SensorData;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付管理类
 * 
 * @author qichuang
 *
 */
public class PayManagement {

	private Activity activity;
	private Handler mHandler;
	private String url;
	private Map<String, String> headMap;
	public static final int ALIPAY_CHANNEL = 0;
	public static final int WXPAY_CHANNEL = 1;
	// private static final int SDK_PAY_FLAG = 1;
	// private static final int SDK_AUTH_FLAG = 2;
	public static final int ALIPAY_CALLBACK = 1;// 支付宝支付的handler回调
	public static final int WXPAY_CALLBACK = 2;// 微信支付的handler回调

	public static final int PAY_ERROR = 3;// 订单获取失败的handler
	
	public static final int PAY_ACCOUNT = 4;//余额支付回调

	/**
	 * 
	 * @param activity
	 *            上下文环境
	 * @param mHandler
	 *            支付宝支付结果接收Handler
	 * @param url
	 *            支付服务端地址
	 */
	public PayManagement(Activity activity, Handler mHandler, String url) {
		super();
		this.activity = activity;
		this.mHandler = mHandler;
		this.url = url;
	}

	/**
	 * 
	 * @param activity
	 *            上下文环境
	 * @param mHandler
	 *            支付宝支付结果接收Handler
	 * @param url
	 *            支付服务端地址
	 * @param headMap
	 *            头信息集合
	 */
	public PayManagement(Activity activity, Handler mHandler, String url, Map<String, String> headMap) {
		super();
		this.activity = activity;
		this.mHandler = mHandler;
		this.url = url;
		this.headMap = headMap;
	}

	/**
	 * 刷新头部信息，最主要是刷新头部token
	 * @param headMap
	 */
	public void refreshHead(Map<String, String> headMap) {
		this.headMap = headMap;
	}
	
	
	public void Alipay(JSONObject orderInfo) {

		try {
			PayTask alipay = new PayTask(activity);
			String pay = orderInfo.getString("alipay_sign");
			Map<String, String> result = alipay.payV2(pay, true);
			if (!orderInfo.isNull("orderId")) {
				String orderId = orderInfo.getString("orderId");
				result.put("orderId", orderId);
			}
			Message msg = new Message();
			msg.what = ALIPAY_CALLBACK;
			msg.obj = result;
			mHandler.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * /** 支付方法不包含赠送
	 * 
	 * @param orderTitle
	 *            商品信息
	 * @param orderPrice
	 *            金额
	 * @param priceType
	 *            订阅类型 0:一年 1:半年 2:季度
	 * @param payType
	 *            0：支付宝 1：微信
	 * @param userId
	 *            用户ID
	 * @param teacherId
	 *            教师ID
	 * @param teacherName
	 *            教师名
	 * @param userName
	 *            用户名
	 */
	public void sendPay(final String orderTitle, final String orderPrice, final String priceType, final String payType,
			final String userId, final String teacherId, final String teacherName, final String userName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderTitle", orderTitle);
		map.put("orderPrice", orderPrice);
		map.put("priceType", priceType);
		map.put("payType", payType);
		map.put("userId", userId);
		map.put("teacherId", teacherId);
		map.put("teacherName", teacherName);
		map.put("userName", userName);
		goPay(map);

		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("orderTitle", orderTitle);
		// map.put("orderPrice", orderPrice);
		// map.put("priceType", priceType);
		// map.put("payType", payType);
		// map.put("userId", userId);
		// map.put("teacherId", teacherId);
		// map.put("teacherName", teacherName);
		// map.put("userName", userName);
		// // http://zhijing.zuren8.com/api/order/createOrder
		// // "http://zhijing.issac.top/api/order/createOrder"
		// HttpUtil http = new HttpUtil(0, url, headMap, map, "UTF-8", new PayCallBack()
		// {
		//
		// @Override
		// public void onSuccess(JSONObject back) {
		// // TODO Auto-generated method stub
		// int type;
		// try {
		// type = back.getInt("type");
		// switch (type) {
		// case ALIPAY_CHANNEL:
		// Alipay(back);
		// break;
		// case WXPAY_CHANNEL:
		// wxpay(back);
		// break;
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void onError(JSONObject back) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		//
		// }
		// }).start();
	}

	/**
	 * 支付，包含赠送
	 * 
	 * @param orderTitle
	 *            商品信息
	 * @param orderPrice
	 *            金额
	 * @param priceType
	 *            订阅类型 0:一年 1:半年 2:季度
	 * @param payType
	 *            0：支付宝 1：微信
	 * @param userId
	 *            用户ID
	 * @param teacherId
	 *            教师ID
	 * @param teacherName
	 *            教师名
	 * @param userName
	 *            用户名
	 * @param type
	 *            表示是赠送的，type == 1
	 * @param num
	 *            赠送的数量 num > 0
	 */
	public void sendPay(String orderTitle, String orderPrice, String priceType, String payType, String userId,
			String teacherId, String teacherName, String userName, String type, String num) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderTitle", orderTitle);
		map.put("orderPrice", orderPrice);
		map.put("priceType", priceType);
		map.put("payType", payType);
		map.put("userId", userId);
		map.put("teacherId", teacherId);
		map.put("teacherName", teacherName);
		map.put("userName", userName);
		map.put("type", type);
		map.put("num", num);
		goPay(map);
	}

	/**
	 * 支付 使用反射获取类的字段为map的键，获取字段对应的get方法的值为map的值
	 * 
	 * @param parameter
	 */
	public void sendPay(PayParameter parameter) {

		if (parameter == null) {
			throw new NullPointerException("传递的参数不能为空");
		}
		PayParameter tempParameter = parameter;

		Map<String, String> map = new HashMap<String, String>();
		// // 反射publicFiled类的所有字段
		// Class cla = tempParameter.getClass();
		// // 获得该类下面所有的字段集合
		// Field[] filed = cla.getDeclaredFields();
		// for (Field fd : filed) {
		// String filedName = fd.getName();
		// String firstLetter = filedName.substring(0, 1).toUpperCase(); // 获得字段第一个字母大写
		// String getMethodName = "get" + firstLetter + filedName.substring(1); //
		// 转换成字段的get方法
		// try {
		// Method getMethod = cla.getMethod(getMethodName, new Class[] {});
		// Object value = getMethod.invoke(tempParameter, new Object[] {}); //
		// 这个对象字段get方法的值
		// if(value == null) {
		// map.put(filedName, null); // 添加到Map集合
		// }else {
		// map.put(filedName, (String) value); // 添加到Map集合
		// }
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		//
		// }
		// }

		List<SensorData> list = PaySDKReflectUtils.sensorDataList(parameter);
		
		for (SensorData ss : list) {
			System.out.println(ss + "......." + ss.getSensorId() + "....." + ss.getSensorValue());
			Object value = ss.getSensorValue();
			String filedName = ss.getSensorId();
			if (value == null) {
				map.put(filedName, null); // 添加到Map集合
			} else {
				map.put(filedName, (String) value); // 添加到Map集合
			}
		}
		

		goPay(map);
		tempParameter = null;
	}

	/**
	 * 获取支付需要的信息
	 * 
	 * @param map
	 */
	private void goPay(final Map<String, String> map) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				// http://zhijing.zuren8.com/api/order/createOrder
				// "http://zhijing.issac.top/api/order/createOrder"
				HttpUtil http = new HttpUtil(0, url, headMap, map, "UTF-8", new PayCallBack() {

					@Override
					public void onSuccess(JSONObject back) {
						// TODO Auto-generated method stub
						if (back == null)
							return;
						try {
							int errorCode = back.getInt("errorCode");
							if (errorCode != 10001) {
								Message message = mHandler.obtainMessage();
								message.obj = errorCode;
								message.what = PAY_ERROR;
								mHandler.sendMessage(message);
								return;
							}
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						int type;
						try {
						
							// 余额支付
							if(back.isNull("type")) {
								Message msg = new Message();
								msg.what = PAY_ACCOUNT;
								msg.obj = back;
								mHandler.sendMessage(msg);
								return;
							}
							
							
							type = back.getInt("type");
							switch (type) {
							case ALIPAY_CHANNEL:
								Alipay(back);
								break;
							case WXPAY_CHANNEL:
								wxpay(back);
								break;
							default:// 其他类型
								
								break;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onError(JSONObject back) {
						// TODO Auto-generated method stub
						Message message = mHandler.obtainMessage();
						message.obj = -1;
						message.what = PAY_ERROR;
						mHandler.sendMessage(message);
						
					}
				});

			}
		}).start();
	}

	/**
	 * 微信支付
	 * 
	 * @param WX_APPID
	 *            微信APPID
	 */

	public void wxpay(JSONObject orderInfo) {
		try {
			String WX_APPID = orderInfo.getString("appid");
			Log.i("sss", WX_APPID);
			IWXAPI mWxApi = WXAPIFactory.createWXAPI(activity, null);
			mWxApi.registerApp(WX_APPID);
			if (mWxApi != null) {
				PayReq req = new PayReq();
				req.appId = WX_APPID;
				req.partnerId = orderInfo.getString("partnerid");// 微信支付分配的商户号
				req.prepayId = orderInfo.getString("prepayid");// 预支付订单号，app服务器调用“统一下单”接口获取
				req.nonceStr = orderInfo.getString("noncestr");// 随机字符串，不长于32位，服务器小哥会给咱生成
				req.timeStamp = orderInfo.getString("timestamp");// 时间戳，app服务器小哥给出
				req.packageValue = "Sign=WXPay";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
				req.sign = orderInfo.getString("sign");// 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
				req.extData = "app data"; // optional
				mWxApi.sendReq(req);
				Log.i("sss", "发起微信支付申请");
			}
			if (!orderInfo.isNull("orderId")) {
				String orderId = orderInfo.getString("orderId");
				Map<String, String> result = new HashMap<String, String>();
				result.put("orderId", orderId);
				Message message = mHandler.obtainMessage();
				message.obj = result;
				message.what = WXPAY_CALLBACK;
				mHandler.sendMessage(message);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 支付方法
	 * 
	 * @param orderTitle
	 *            商品信息
	 * @param orderPrice
	 *            金额
	 * @param payType
	 *            0：支付宝 1：微信
	 * @param dataMap
	 *            其他支付参数
	 */
	public void sendPay(Map dataMap) {
		goPay(dataMap);
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// Map<String, String> map = new HashMap<String, String>();
		// if (dataMap != null) {
		// map.putAll(dataMap);
		// }
		// // http://zhijing.zuren8.com/api/order/createOrder
		// // "http://zhijing.issac.top/api/order/createOrder"
		// HttpUtil http = new HttpUtil(0, url, headMap, map, "UTF-8", new PayCallBack()
		// {
		//
		// @Override
		// public void onSuccess(JSONObject back) {
		// // TODO Auto-generated method stub
		//
		//
		// int type;
		// try {
		// type = back.getInt("type");
		// switch (type) {
		// case ALIPAY_CHANNEL:
		// Alipay(back);
		// break;
		// case WXPAY_CHANNEL:
		// wxpay(back);
		// break;
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// @Override
		// public void onError(JSONObject back) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		//
		// }
		// }).start();
	}
}

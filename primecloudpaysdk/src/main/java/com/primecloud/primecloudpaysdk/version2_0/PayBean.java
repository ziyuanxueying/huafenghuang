package com.primecloud.primecloudpaysdk.version2_0;

/**
 * 支付需要的参数
 * 
 * @author zy
 *
 */
public class PayBean {

	/**
	 * req.appId = appid; req.partnerId = partnerid;// 微信支付分配的商户号 req.prepayId =
	 * prepayid;// 预支付订单号，app服务器调用“统一下单”接口获取 req.nonceStr = noncestr;//
	 * 随机字符串，不长于32位，服务器小哥会给咱生成 req.timeStamp = timestamp;// 时间戳，app服务器小哥给出
	 * req.packageValue = "Sign=WXPay";// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
	 * req.sign = sign;//
	 * 签名，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个
	 * req.extData = "app data"; // optional
	 */

	// 微信需要的参数 用wx开头
	private String wxAppId;
	private String wxPartnerId;// 微信支付分配的商户号
	private String wxPrepayId;// 预支付订单号，app服务器调用“统一下单”接口获取
	private String wxNonceStr;// 随机字符串，不长于32位，服务器小哥会给咱生成
	private String wxTimeStamp;// 时间戳，app服务器小哥给出
	private String wxPackageValue;// 固定值Sign=WXPay，可以直接写死，服务器返回的也是这个固定值
	private String wxExtData;// optional

	// 微信支付和支付宝支付公用的参数 用com开头，共用
	private String comSign;// 签名(微信、支付宝)，服务器小哥给出，他会根据：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3指导得到这个

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxPartnerId() {
		return wxPartnerId;
	}

	public void setWxPartnerId(String wxPartnerId) {
		this.wxPartnerId = wxPartnerId;
	}

	public String getWxPrepayId() {
		return wxPrepayId;
	}

	public void setWxPrepayId(String wxPrepayId) {
		this.wxPrepayId = wxPrepayId;
	}

	public String getWxNonceStr() {
		return wxNonceStr;
	}

	public void setWxNonceStr(String wxNonceStr) {
		this.wxNonceStr = wxNonceStr;
	}

	public String getWxTimeStamp() {
		return wxTimeStamp;
	}

	public void setWxTimeStamp(String wxTimeStamp) {
		this.wxTimeStamp = wxTimeStamp;
	}

	public String getWxPackageValue() {
		return wxPackageValue;
	}

	public void setWxPackageValue(String wxPackageValue) {
		this.wxPackageValue = wxPackageValue;
	}

	public String getWxExtData() {
		return wxExtData;
	}

	public void setWxExtData(String wxExtData) {
		this.wxExtData = wxExtData;
	}

	public String getComSign() {
		return comSign;
	}

	public void setComSign(String comSign) {
		this.comSign = comSign;
	}

	@Override
	public String toString() {
		return "PayBean [wxAppId=" + wxAppId + ", wxPartnerId=" + wxPartnerId + ", wxPrepayId=" + wxPrepayId
				+ ", wxNonceStr=" + wxNonceStr + ", wxTimeStamp=" + wxTimeStamp + ", wxPackageValue=" + wxPackageValue
				+ ", wxExtData=" + wxExtData + ", comSign=" + comSign + "]";
	}

}

package com.primecloud.primecloudpaysdk;

/**
 * 测试类
 * 
 * @author zy
 *
 */
public class testBean {

	// 商品ID
	private String productId;
	// 订单类型（0 -> 订阅讲师（1.3版本后废弃） 1 -> 公众号充值积分 2 -> ios内购买充值积分 3 -> 专栏订阅 4 -> 精品课程 5
	// -> 打包课程） 是
	private String orderType;
	private String userId;//
	private String userName;
	private String loginId;
	private String num;// 赠课的数量
	private String type;// 赠课类型，默认为1
	private String payType;// 支付方式（0 -> 支付宝 1 -> 微信支付 2 -> 积分购买 3 -> 邀约促销 4 -> ios内购买 5 -> 微信公众号购买 6 ->
							// 手充订单） 是 [int]
	private String couponId;// 优惠卷id
	private String couponCode;// 优惠卷码

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Override
	public String toString() {
		return "PayParameter [productId=" + productId + ", orderType=" + orderType + ", userId=" + userId
				+ ", userName=" + userName + ", loginId=" + loginId + ", num=" + num + ", type=" + type + ", payType="
				+ payType + ", couponId=" + couponId + ", couponCode=" + couponCode + "]";
	}

}

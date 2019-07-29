package com.primecloud.huafenghuang.utils.pay;

import com.primecloud.primecloudpaysdk.bean.RequestParamter;

public class PayBean extends RequestParamter {

    private String payType;//支付类型  0 支付宝 1 微信 5 微信h5
    private String productType;//商品类型 0线上课程支付，1线下课程支付，5vip会员支付
    private String productId;//	productId 课程Id（包括线上，线下）
    private String userId;//userId 用户账号
    private String userName;//	userName 线下课报名-用户名
    private String tel;//	线下课报名-手机号
    private String job;//	线下课报名-工作
    private String area;//	线下课报名-地区
    private String courseTime;//	线下课报名-开课时间
    private String platform;//platform 0 -> Android、1 -> iOS、2 -> H5
    private String openId;//openId;//openId 公众号 openID
    private String orderTitle;//订单标题


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }


    @Override
    public String toString() {
        return "PayBean{" +
                "payType='" + payType + '\'' +
                ", productType='" + productType + '\'' +
                ", productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", tel='" + tel + '\'' +
                ", job='" + job + '\'' +
                ", area='" + area + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", platform='" + platform + '\'' +
                ", openId='" + openId + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                '}';
    }
}

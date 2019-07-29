package com.primecloud.primecloudpaysdk.version1_0;


import com.primecloud.primecloudpaysdk.bean.RequestParamter;

/**
 * Created by zy on 2019/1/21.
 */

public class CreateOrderBean extends RequestParamter {

    private String userId;
    private String userName;
    private String eventId;
    private String groupId;
    private String platform = "2";
    private String orderType;
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "CreateOrderBean{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", eventId='" + eventId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", platform='" + platform + '\'' +
                ", orderType='" + orderType + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }
}

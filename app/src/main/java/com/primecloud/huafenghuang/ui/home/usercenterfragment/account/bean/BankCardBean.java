package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean;

public class BankCardBean {

    /**
     * bankId : 1002
     * bankName : 工商银行
     * cardName : 刘德华
     * cardNumber : 123456
     * id : 4
     * isDefault : true
     * userId : 7475
     *
     */

    private int bankId;
    private String bankName;
    private String cardName;
    private String cardNumber;
    private int id;
    private boolean isDefault;
    private int userId;

    private boolean isWx;// 是否是微信
    private String openId;
    private String realName;

    public boolean isWx() {
        return isWx;
    }

    public void setWx(boolean wx) {
        isWx = wx;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BankCardBean{" +
                "bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", id=" + id +
                ", isDefault=" + isDefault +
                ", userId=" + userId +
                '}';
    }
}

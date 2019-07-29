package com.primecloud.huafenghuang.ui.home.usercenterfragment.bean;

/**
 * Created by ${qc} on 2019/5/16.
 */

public class UserExtendInfo {


    /**
     * qq : 2222
     * weibo : 44444444
     * phone : 13260390051
     * weChat : 2222
     */

    private String qq;
    private String weibo;
    private String phone;
    private String weChat;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    @Override
    public String toString() {
        return "UserExtendInfo{" +
                "qq='" + qq + '\'' +
                ", weibo='" + weibo + '\'' +
                ", phone='" + phone + '\'' +
                ", weChat='" + weChat + '\'' +
                '}';
    }
}

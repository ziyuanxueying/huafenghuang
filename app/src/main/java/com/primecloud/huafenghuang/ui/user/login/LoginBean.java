package com.primecloud.huafenghuang.ui.user.login;

import com.primecloud.huafenghuang.ui.user.UserInfo;

/**
 * Created by ${qc} on 2019/5/16.
 */

public class LoginBean {

    /**
     * data : {"checks":0,"code":"5215111","id":7493,"lastLogin":"2019-05-13T15:00:26.732","phone":"18310616582","pic":"/home/image/layout/default.png","sex":0,"type":2,"username":"18310616582"}
     * message : 请求处理完成
     */

    private UserInfo data;
    private String message;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}

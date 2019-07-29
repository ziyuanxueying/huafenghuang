package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info;

import com.primecloud.huafenghuang.ui.user.UserInfo;

/**
 * Created by ${qc} on 2019/5/20.
 */

public class UserHead {

    /**
     * data : {"checks":0,"cityId":130400,"code":"3279901","id":7495,"lastLogin":"2019-05-16T18:06:27","password":"c5c80afd6410f576a0e9506fa5861e84","phone":"13693348305","pic":"9cee6c0edd0740259963b4a16a87a817.jpg","salt":"d2c36e14714546b29c9579509c42b17d","sex":0,"type":2,"username":"QQ"}
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
        return "UserHead{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}

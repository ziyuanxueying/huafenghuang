package com.primecloud.library.baselibrary.entity;

public class BaseEntity<T>  {

    public static final int LOGIN_ERROR_CODE = 10000;// 登陆异常

    public static final int MISS_BASEHEAD_CODE = 10003;// 缺少基础头信息

    public static final int MISS_LOGINHEAD_CODE = 10005;// 缺少登陆头信息

    public static final int MISS_LOGININFO_CODE = 10007;// 登陆信息不存在

    public static final int NOT_LOGIN_PRESSION = 10008;// 没有登陆权限

    public static final int TOKEN_INVALID_CODE = 10009;// token过期



    // 成功有数据
    public static final int DATA_EXCU_CODE = 1;
    // 成功没数据
    public static final int NO_DATA_EXCU_CODE = 0;


    /**
     * code : 10000
     * msg : 成功
     * data : null
     */

    private int state;
    private String message;
    private int errorCode;
    private int excuCode;
    private T data;



    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getExcuCode() {
        return excuCode;
    }

    public void setExcuCode(int excuCode) {
        this.excuCode = excuCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "state=" + state +
                ", message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", excuCode='" + excuCode + '\'' +
                ", data=" + data +
                '}';
    }
}


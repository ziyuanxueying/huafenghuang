package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean;

import java.util.List;

public class BingBankCardList {

    /**
     * data : [{"bankId":1002,"bankName":"工商银行","cardName":"刘德华","cardNumber":"987654321","id":2,"isDefault":true,"userId":7475},{"bankId":1002,"bankName":"工商银行","cardName":"刘德华","cardNumber":"9876543210","id":3,"isDefault":false,"userId":7475}]
     * message : 请求处理完成
     */

    private String message;
    private List<BankCardBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BankCardBean> getData() {
        return data;
    }

    public void setData(List<BankCardBean> data) {
        this.data = data;
    }

}

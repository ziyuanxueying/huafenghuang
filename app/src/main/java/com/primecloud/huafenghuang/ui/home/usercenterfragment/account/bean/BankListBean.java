package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

public class BankListBean {

    /**
     * data : [{"bankCode":"1002","bankName":"工商银行","id":1},{"bankCode":"1005","bankName":"农业银行","id":2},{"bankCode":"1026","bankName":"中国银行","id":3},{"bankCode":"1003","bankName":"建设银行","id":4},{"bankCode":"1001","bankName":"招商银行","id":5},{"bankCode":"1066","bankName":"邮储银行","id":6},{"bankCode":"1020","bankName":"交通银行","id":7},{"bankCode":"1004","bankName":"浦发银行","id":8},{"bankCode":"1006","bankName":"民生银行","id":9},{"bankCode":"1009","bankName":"兴业银行","id":10},{"bankCode":"1010","bankName":"平安银行","id":11},{"bankCode":"1021","bankName":"中信银行","id":12},{"bankCode":"1025","bankName":"华夏银行","id":13},{"bankCode":"1027","bankName":"广发银行","id":14},{"bankCode":"1022","bankName":"光大银行","id":15},{"bankCode":"4836","bankName":"北京银行","id":16},{"bankCode":"1056","bankName":"宁波银行","id":17}]
     * message : 请求处理完成
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements IPickerViewData {
        /**
         * bankCode : 1002
         * bankName : 工商银行
         * id : 1
         */

        private String bankCode;
        private String bankName;
        private int id;

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getPickerViewText() {
            return bankName;
        }
    }
}

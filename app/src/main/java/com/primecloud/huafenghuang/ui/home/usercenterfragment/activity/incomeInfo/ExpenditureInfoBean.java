package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class ExpenditureInfoBean {

    /**
     * data : [{"expenditureType":1,"amount":100,"created_at":1518675042000,"details":"微信提现","expenditureTitle":"收益提现"},{"expenditureType":4,"amount":0,"created_at":1557458892000,"details":"一年会员","expenditureTitle":"VIP会员购买"},{"expenditureType":1,"amount":100,"created_at":1557900642000,"details":"银行卡提现","expenditureTitle":"收益提现"}]
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

    public static class DataBean {
        /**
         * expenditureType : 1
         * amount : 100
         * created_at : 1518675042000
         * details : 微信提现
         * expenditureTitle : 收益提现
         */

        private int expenditureType;
        private int amount;
        private long created_at;
        private String details;
        private String expenditureTitle;

        public int getExpenditureType() {
            return expenditureType;
        }

        public void setExpenditureType(int expenditureType) {
            this.expenditureType = expenditureType;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getExpenditureTitle() {
            return expenditureTitle;
        }

        public void setExpenditureTitle(String expenditureTitle) {
            this.expenditureTitle = expenditureTitle;
        }
    }
}

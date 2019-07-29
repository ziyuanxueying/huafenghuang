package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class IncomeInfoBean {

    /**
     * data : [{"amount":10000,"createdAt":"2019-05-15T15:17:40","id":1,"type":7,"userId":7483,"username":"333"},{"amount":3000000,"createdAt":"2019-05-15T17:00:09","id":2,"type":7,"userId":7483,"username":"1"}]
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
         * amount : 10000
         * createdAt : 2019-05-15T15:17:40
         * id : 1
         * type : 7
         * userId : 7483
         * username : 333
         */

        private int amount;
        private String createdAt;
        private int id;
        private int type;
        private int userId;
        private String username;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "amount=" + amount +
                    ", createdAt='" + createdAt + '\'' +
                    ", id=" + id +
                    ", type=" + type +
                    ", userId=" + userId +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IncomeInfoBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

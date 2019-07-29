package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/10.
 */

public class OrderBean {


    /**
     * data : {"totalPage":1,"curPage":1,"records":[{"createdAt":"2019-05-09T16:48:09","expiraTime":"2020-05-09T16:48:09","id":15,"isDelete":0,"orderPrice":0,"orderSn":"201905091648092638","orderTitle":"后台手充-vip订单","orderType":2,"payPrice":0,"payTime":"2019-05-09T16:48:09","payType":3,"platform":0,"productId":1,"status":1,"tradeSn":"201905090000000000","type":0,"updatedAt":"2019-05-09T16:48:09","userId":344}]}
     * message : 请求处理完成
     */

    private DataBean data;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * totalPage : 1
         * curPage : 1
         * records : [{"createdAt":"2019-05-09T16:48:09","expiraTime":"2020-05-09T16:48:09","id":15,"isDelete":0,"orderPrice":0,"orderSn":"201905091648092638","orderTitle":"后台手充-vip订单","orderType":2,"payPrice":0,"payTime":"2019-05-09T16:48:09","payType":3,"platform":0,"productId":1,"status":1,"tradeSn":"201905090000000000","type":0,"updatedAt":"2019-05-09T16:48:09","userId":344}]
         */

        private int totalPage;
        private int curPage;
        private List<RecordsBean> records;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * createdAt : 2019-05-09T16:48:09
             * expiraTime : 2020-05-09T16:48:09
             * id : 15
             * isDelete : 0
             * orderPrice : 0
             * orderSn : 201905091648092638
             * orderTitle : 后台手充-vip订单
             * orderType : 2
             * payPrice : 0
             * payTime : 2019-05-09T16:48:09
             * payType : 3
             * platform : 0
             * productId : 1
             * status : 1
             * tradeSn : 201905090000000000
             * type : 0
             * updatedAt : 2019-05-09T16:48:09
             * userId : 344
             */

            private String createdAt;
            private String expiraTime;
            private int id;
            private int isDelete;
            private int orderPrice;
            private String orderSn;
            private String orderTitle;
            private int orderType;
            private int payPrice;
            private String payTime;
            private int payType;
            private int platform;
            private int productId;
            private int status;
            private String tradeSn;
            private int type;
            private String updatedAt;
            private int userId;
            private String courseId;

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getExpiraTime() {
                return expiraTime;
            }

            public void setExpiraTime(String expiraTime) {
                this.expiraTime = expiraTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public int getOrderPrice() {
                return orderPrice;
            }

            public void setOrderPrice(int orderPrice) {
                this.orderPrice = orderPrice;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getOrderTitle() {
                return orderTitle;
            }

            public void setOrderTitle(String orderTitle) {
                this.orderTitle = orderTitle;
            }

            public int getOrderType() {
                return orderType;
            }

            public void setOrderType(int orderType) {
                this.orderType = orderType;
            }

            public int getPayPrice() {
                return payPrice;
            }

            public void setPayPrice(int payPrice) {
                this.payPrice = payPrice;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public int getPayType() {
                return payType;
            }

            public void setPayType(int payType) {
                this.payType = payType;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTradeSn() {
                return tradeSn;
            }

            public void setTradeSn(String tradeSn) {
                this.tradeSn = tradeSn;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}

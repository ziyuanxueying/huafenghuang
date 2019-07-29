package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/9.
 */

public class MessageBean {


    /**
     * data : [{"content":"2222","id":36076,"isRead":false,"tempId":0,"userId":7475},{"actionId":1,"content":"1111","id":36075,"isRead":true,"tempId":0,"type":true,"userId":7475}]
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

    @Override
    public String toString() {
        return "MessageBean{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * content : 2222
         * id : 36076
         * isRead : false
         * tempId : 0
         * userId : 7475
         * actionId : 1
         * type : true
         */

        private String content;
        private int id;
        private boolean isRead;
        private int tempId;
        private int userId;
        private int actionId;
        private boolean type;
        private boolean isSelect;// 是否被选中
        private String createdAt;//显示时间

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isRead() {
            return isRead;
        }

        public void setRead(boolean read) {
            isRead = read;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public int getTempId() {
            return tempId;
        }

        public void setTempId(int tempId) {
            this.tempId = tempId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getActionId() {
            return actionId;
        }

        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "content='" + content + '\'' +
                    ", id=" + id +
                    ", isRead=" + isRead +
                    ", tempId=" + tempId +
                    ", userId=" + userId +
                    ", actionId=" + actionId +
                    ", type=" + type +
                    '}';
        }
    }
}

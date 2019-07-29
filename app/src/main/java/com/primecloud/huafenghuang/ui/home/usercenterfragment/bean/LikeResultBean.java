package com.primecloud.huafenghuang.ui.home.usercenterfragment.bean;

public class LikeResultBean {


    /**
     * data : {"courseId":8,"createdAt":"2019-05-17T14:51:15.943","id":39,"type":2,"userId":347}
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
         * courseId : 8
         * createdAt : 2019-05-17T14:51:15.943
         * id : 39
         * type : 2
         * userId : 347
         */

        private int courseId;
        private String createdAt;
        private int id;
        private int type;
        private int userId;

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
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
    }
}

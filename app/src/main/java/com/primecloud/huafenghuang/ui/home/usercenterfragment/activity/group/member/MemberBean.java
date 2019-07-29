package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/14.
 */

public class MemberBean {


    /**
     * data : {"totalPage":1,"curPage":1,"records":[{"soldCount":0,"level":1,"phone":"15131372368","teamNumber":0,"id":4,"pic":"/home/image/layout/default.png","stock":0,"userId":7490,"superiorId":1,"username":"你猜"}]}
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

    @Override
    public String toString() {
        return "MemberBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * totalPage : 1
         * curPage : 1
         * records : [{"soldCount":0,"level":1,"phone":"15131372368","teamNumber":0,"id":4,"pic":"/home/image/layout/default.png","stock":0,"userId":7490,"superiorId":1,"username":"你猜"}]
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "totalPage=" + totalPage +
                    ", curPage=" + curPage +
                    ", records=" + records +
                    '}';
        }

        public static class RecordsBean {
            /**
             * soldCount : 0
             * level : 1
             * phone : 15131372368
             * teamNumber : 0
             * id : 4
             * pic : /home/image/layout/default.png
             * stock : 0
             * userId : 7490
             * superiorId : 1
             * username : 你猜
             */

            private int soldCount;
            private int level;
            private String phone;
            private int teamNumber;
            private int id;
            private String pic;
            private int stock;
            private int userId;
            private int superiorId;
            private String username;

            public int getSoldCount() {
                return soldCount;
            }

            public void setSoldCount(int soldCount) {
                this.soldCount = soldCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getTeamNumber() {
                return teamNumber;
            }

            public void setTeamNumber(int teamNumber) {
                this.teamNumber = teamNumber;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getSuperiorId() {
                return superiorId;
            }

            public void setSuperiorId(int superiorId) {
                this.superiorId = superiorId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            @Override
            public String toString() {
                return "RecordsBean{" +
                        "soldCount=" + soldCount +
                        ", level=" + level +
                        ", phone='" + phone + '\'' +
                        ", teamNumber=" + teamNumber +
                        ", id=" + id +
                        ", pic='" + pic + '\'' +
                        ", stock=" + stock +
                        ", userId=" + userId +
                        ", superiorId=" + superiorId +
                        ", username='" + username + '\'' +
                        '}';
            }
        }

    }
}

package com.primecloud.huafenghuang.ui.home.findfragment.bean;

import java.util.List;

public class FindItemBean {


    /**
     * status : 1
     * data : {"hot":[{"coursePic":"/home/image/layout/default.png","chapterId":580,"courseView":0,"created_at":1557368727000,"chapter_title":"线下课H5链接","id":11,"courseId":2790},{"chapterId":9658,"courseView":8,"created_at":1556267355000,"chapter_title":"有限合伙魅力何在？.mp3","id":10,"courseId":2717},{"chapterId":9809,"courseView":10,"created_at":1556267313000,"chapter_title":"【Mitch】第16讲.mp3","id":9,"courseId":2734},{"coursePic":"/home/image/resource/mp3.jpg","chapterId":8837,"courseView":150,"created_at":1556256460000,"chapter_title":"子西导师课程语音介绍.mp3","id":4,"courseId":2450}],"free":[{"coursePic":"/uploads/cut/20190510181211.png","chapterId":10046,"courseView":0,"created_at":1557483146000,"chapter_title":"速度快","courseId":2790},{"coursePic":"/uploads/cut/20190510180422.png","chapterId":10045,"courseView":0,"created_at":1557482689000,"chapter_title":"111","courseId":2790},{"coursePic":"/uploads/cut/20190510175323.png","chapterId":10044,"courseView":0,"created_at":1557482015000,"chapter_title":"跨世纪的","courseId":2790},{"chapterId":9656,"courseView":0,"created_at":1529647956000,"chapter_title":"课前导学","courseId":2717}]}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<FindBean> hot;
        private List<FindBean> free;
        private List<FindBean> newSay;
        private List<FindBean> self;
        private List<FindBean> family;
        private List<FindBean> weekNew;
        private List<FindBean> work;
        private List<BannerBean> banner;//轮播图

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<FindBean> getHot() {
            return hot;
        }

        public void setHot(List<FindBean> hot) {
            this.hot = hot;
        }

        public List<FindBean> getFree() {
            return free;
        }

        public void setFree(List<FindBean> free) {
            this.free = free;
        }

        public List<FindBean> getNewSay() {
            return newSay;
        }

        public void setNewSay(List<FindBean> newSay) {
            this.newSay = newSay;
        }

        public List<FindBean> getSelf() {
            return self;
        }

        public void setSelf(List<FindBean> self) {
            this.self = self;
        }

        public List<FindBean> getFamily() {
            return family;
        }

        public void setFamily(List<FindBean> family) {
            this.family = family;
        }

        public List<FindBean> getWeekNew() {
            return weekNew;
        }

        public void setWeekNew(List<FindBean> weekNew) {
            this.weekNew = weekNew;
        }

        public List<FindBean> getWork() {
            return work;
        }

        public void setWeek(List<FindBean> week) {
            this.work = week;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "hot=" + hot +
                    ", free=" + free +
                    ", newSay=" + newSay +
                    ", self=" + self +
                    ", family=" + family +
                    ", weekNew=" + weekNew +
                    ", work=" + work +
                    ", banner=" + banner +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FindItemBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}

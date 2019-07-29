package com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class TimeTableBean {

    /**
     * data : [{"courseName":"测试数据-蜕变，成就非凡人生","courseChapterId":"8835","created_at":1557395363000,"likedId":27,"id":11,"content":"sadfasdfdass"},{"courseName":"测试数据-【阿曲】如何成为拥有洞察和区分力的大师（试读）","courseChapterId":"8838","created_at":1557394196000,"likedId":35,"id":3,"content":"111"},{"courseName":"测试数据-蜕变，成就非凡人生","courseChapterId":"8835","created_at":1320076800000,"id":4,"content":"阿塞阀塞阀"},{"courseName":"测试数据-蜕变，成就非凡人生","courseChapterId":"8835","id":5,"content":"adsfasfdasdf "},{"courseName":"测试数据-蜕变，成就非凡人生","courseChapterId":"8835","id":6,"content":"afasd"},{"courseName":"测试数据-蜕变，成就非凡人生","courseChapterId":"8835","id":7,"content":"afasd"}]
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
         * forwards : 0
         * isLike : 1
         * courseChapterId : 8835
         * created_at : 1557395363000
         * likedId : 49
         * likeCount : 2
         * avatar : http://api.hfh.issac.top/resource/home/image/layout/default.png
         * likedCount : 2
         * content : sadfasdfdass
         * courseName : 测试数据-蜕变，成就非凡人生
         * publisherId : 1
         * nickname : 管理员
         * copys : 0
         * id : 11
         * shareUrl:http:\/\/admin.huafenghuang.issac.top\/Timetable?id=1
         * coursePic:http:\/\/admin.huafenghuang.issac.top\/uploads\/cut\/20190618134757.png
         */

        private int forwards;
        private int isLike;
        private String courseChapterId;
        private long created_at;
        private int likedId;
        private int likeCount;
        private String avatar;
        private int likedCount;
        private String content;
        private String courseName;
        private int publisherId;
        private String nickname;
        private int copys;
        private int id;
        private String coverUrl;
        private String shareUrl;
        private String coursePic;
        private int courseId;

        public String getCoursePic() {
            return coursePic;
        }

        public void setCoursePic(String coursePic) {
            this.coursePic = coursePic;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getForwards() {
            return forwards;
        }

        public void setForwards(int forwards) {
            this.forwards = forwards;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public String getCourseChapterId() {
            return courseChapterId;
        }

        public void setCourseChapterId(String courseChapterId) {
            this.courseChapterId = courseChapterId;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public int getLikedId() {
            return likedId;
        }

        public void setLikedId(int likedId) {
            this.likedId = likedId;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getLikedCount() {
            return likedCount;
        }

        public void setLikedCount(int likedCount) {
            this.likedCount = likedCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getPublisherId() {
            return publisherId;
        }

        public void setPublisherId(int publisherId) {
            this.publisherId = publisherId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getCopys() {
            return copys;
        }

        public void setCopys(int copys) {
            this.copys = copys;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "forwards=" + forwards +
                    ", isLike=" + isLike +
                    ", courseChapterId='" + courseChapterId + '\'' +
                    ", created_at=" + created_at +
                    ", likedId=" + likedId +
                    ", likeCount=" + likeCount +
                    ", avatar='" + avatar + '\'' +
                    ", likedCount=" + likedCount +
                    ", content='" + content + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", publisherId=" + publisherId +
                    ", nickname='" + nickname + '\'' +
                    ", copys=" + copys +
                    ", id=" + id +
                    ", coverUrl='" + coverUrl + '\'' +
                    ", shareUrl='" + shareUrl + '\'' +
                    ", coursePic='" + coursePic + '\'' +
                    ", courseId=" + courseId +
                    '}';
        }
    }
}

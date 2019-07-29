package com.primecloud.huafenghuang.ui.course.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class OfflineCourseDetailBean implements Serializable {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean implements Serializable
    {
        private ArrayList<CourseInfoBean> courseInfoList;//开课信息
        private String courseName;//线下课程名称
        private String courseStatus;//课程状态 0-非正常 1-正常
        private String enrollNumber;//报名人数
        private String id;//课程Id
        private String teacherId;//讲师姓名
        private String courseCover;//课程封面
        private String coursePrice;//课程价格 以分为单位记录
        private String courseVideo;//宣传视频
        private String createdAt;//创建时间
        private String onlineCourseId;//线上课程id
        private String courseIntro;//课程介绍
        private String isBuy;//是否报名过


        public String getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(String isBuy) {
            this.isBuy = isBuy;
        }

        public ArrayList<CourseInfoBean> getCourseInfoList() {
            return courseInfoList;
        }

        public void setCourseInfoList(ArrayList<CourseInfoBean> courseInfoList) {
            this.courseInfoList = courseInfoList;
        }

        public String getCourseIntro() {
            return courseIntro;
        }

        public void setCourseIntro(String courseIntro) {
            this.courseIntro = courseIntro;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseStatus() {
            return courseStatus;
        }

        public void setCourseStatus(String courseStatus) {
            this.courseStatus = courseStatus;
        }

        public String getEnrollNumber() {
            return enrollNumber;
        }

        public void setEnrollNumber(String enrollNumber) {
            this.enrollNumber = enrollNumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getCourseCover() {
            return courseCover;
        }

        public void setCourseCover(String courseCover) {
            this.courseCover = courseCover;
        }

        public String getCoursePrice() {
            return coursePrice;
        }

        public void setCoursePrice(String coursePrice) {
            this.coursePrice = coursePrice;
        }

        public String getCourseVideo() {
            return courseVideo;
        }

        public void setCourseVideo(String courseVideo) {
            this.courseVideo = courseVideo;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getOnlineCourseId() {
            return onlineCourseId;
        }

        public void setOnlineCourseId(String onlineCourseId) {
            this.onlineCourseId = onlineCourseId;
        }


        public class CourseInfoBean implements Serializable
        {
            private ArrayList<String> beginTime;
            private String area;

            public ArrayList<String> getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(ArrayList<String> beginTime) {
                this.beginTime = beginTime;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }


            @Override
            public String toString() {
                return "CourseInfoBean{" +
                        "beginTime=" + beginTime +
                        ", area='" + area + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "courseInfoList=" + courseInfoList +
                    ", courseName='" + courseName + '\'' +
                    ", courseStatus='" + courseStatus + '\'' +
                    ", enrollNumber='" + enrollNumber + '\'' +
                    ", id='" + id + '\'' +
                    ", teacherId='" + teacherId + '\'' +
                    ", courseCover='" + courseCover + '\'' +
                    ", coursePrice='" + coursePrice + '\'' +
                    ", courseVideo='" + courseVideo + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", onlineCourseId='" + onlineCourseId + '\'' +
                    ", courseIntro='" + courseIntro + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "OfflineCourseDetailBean{" +
                "data=" + data +
                '}';
    }
}

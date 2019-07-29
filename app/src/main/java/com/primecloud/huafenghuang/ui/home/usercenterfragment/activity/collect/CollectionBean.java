package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect;

import java.util.List;

/**
 * Created by ${qc} on 2019/5/13.
 */

public class CollectionBean {


    /**
     * data : {" totalPage":1,"curPage":1," records":[{"commentNum":0,"count":0,"courseIntro":"中西家庭教育文化的融合，为您提供年度52堂关于家庭&青少年的线上课程。 一年的学习，帮助你开放更多视角，扩大更宽眼界，在互联网中学习、体验、实践，互动，期待您的订阅，愿孩子茁壮成长，家庭和谐美好！","courseStatus":true,"courseTitle":"测试数据-【尔雅】第5讲：家长如何面对青少年期的孩子？","courseView":0,"createdAt":"2017-12-15T00:00:00","favNum":0,"id":2533,"likeNum":0,"mainTag":1,"secTag":6,"sort":43,"teacherId":5742,"teacherName":"二红","updatedAt":"2017-12-31T14:02:20","vip_free":1}]}
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
        return "CollectionBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         *  totalPage : 1
         * curPage : 1
         *  records : [{"commentNum":0,"count":0,"courseIntro":"中西家庭教育文化的融合，为您提供年度52堂关于家庭&青少年的线上课程。 一年的学习，帮助你开放更多视角，扩大更宽眼界，在互联网中学习、体验、实践，互动，期待您的订阅，愿孩子茁壮成长，家庭和谐美好！","courseStatus":true,"courseTitle":"测试数据-【尔雅】第5讲：家长如何面对青少年期的孩子？","courseView":0,"createdAt":"2017-12-15T00:00:00","favNum":0,"id":2533,"likeNum":0,"mainTag":1,"secTag":6,"sort":43,"teacherId":5742,"teacherName":"二红","updatedAt":"2017-12-31T14:02:20","vip_free":1}]
         */

        private int totalPage;
        private int curPage;
        private List<recordsBean> records;

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

        public List<recordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<recordsBean> records) {
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

        public static class recordsBean {

            /**
             * audio_timeLen : 0
             * courseId : 2450
             * coursePic : /home/image/layout/default.png
             * courseView : 0
             * createdAt : 2017-11-10T13:18:12
             * detailTitle : <p>14年前，一个白手创业的女子正在经历创业成功后的轻狂与浮躁，也在经历企业发展的瓶颈与困惑。<br> <br> </p><p>一个偶然的机会，她接受了一个训练，从此一发不可收拾。她卖掉公司，从老板变成一家集团公司的基层员工，全情投入，完全臣服，接受最严格的专业训练，经历了太多心里的委屈和蜕变的兴奋！<br> <br> </p><p>从来没有一件事情让她如此的承诺，十余年如一日，如饥似渴，走在自我蜕变与支持蜕变的路上！<br> <br> </p><p>这个人就是我—-子西。中国蜕变式体验式训练的推动者。</p><p><br> 基于结果，这是一条行得通的路！每当看到亲人相拥而泣，爱人放下抱怨，朋友握手言和，每个人脸上露出自信的笑容，我的心就跟着幸福！<br> <br> </p><p>现在开始，我将用这个平台和更多朋友分享关于如何活在蜕变范畴，包括深入区分人生重要的一些领域，活出幸福喜悦人生！<br> <br> </p><p>如果你愿意相信，如果你真的承诺你生命的蜕变，我的分享也许是你的镜子、你的催化剂、你的发动机或是你的指南针！<br> <br> </p><p>蜕变，让你的未来无限可能！<br> <br> </p>只要你需要，我一直在这里。我的理想从未改变！<br> <br>
             * free : 0
             * id : 8836
             * sort : 1
             * status : 0
             * title : 子西导师课程语音介绍
             * videoFileid : 0c65874babdc44e28405ee668a753e34
             * video_timeLen : 0
             * vip_free : 1
             */

            private int audio_timeLen;
            private int courseId;
            private String coursePic;
            private int courseView;
            private String createdAt;
            private String detailTitle;
            private int free;
            private int id;
            private int sort;
            private int status;
            private String title;
            private String videoFileid;
            private int video_timeLen;
            private int vip_free;

            public int getAudio_timeLen() {
                return audio_timeLen;
            }

            public void setAudio_timeLen(int audio_timeLen) {
                this.audio_timeLen = audio_timeLen;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getCoursePic() {
                return coursePic;
            }

            public void setCoursePic(String coursePic) {
                this.coursePic = coursePic;
            }

            public int getCourseView() {
                return courseView;
            }

            public void setCourseView(int courseView) {
                this.courseView = courseView;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getDetailTitle() {
                return detailTitle;
            }

            public void setDetailTitle(String detailTitle) {
                this.detailTitle = detailTitle;
            }

            public int getFree() {
                return free;
            }

            public void setFree(int free) {
                this.free = free;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVideoFileid() {
                return videoFileid;
            }

            public void setVideoFileid(String videoFileid) {
                this.videoFileid = videoFileid;
            }

            public int getVideo_timeLen() {
                return video_timeLen;
            }

            public void setVideo_timeLen(int video_timeLen) {
                this.video_timeLen = video_timeLen;
            }

            public int getVip_free() {
                return vip_free;
            }

            public void setVip_free(int vip_free) {
                this.vip_free = vip_free;
            }
        }
    }
}

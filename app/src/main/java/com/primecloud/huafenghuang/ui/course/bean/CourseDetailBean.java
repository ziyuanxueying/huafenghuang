package com.primecloud.huafenghuang.ui.course.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class CourseDetailBean implements Serializable {

    private DataBean data;

    public DataBean getDataBean() {
        return data;
    }

    public void setDataBean(DataBean data) {
        this.data = data;
    }

    public class DataBean implements Serializable
    {
        private String commentNum;//点评数
        private String count;//小节数
        private String courseIntro;//课程介绍
        private String courseStatus;//课程状态：1上架 2上架 3删除
        private String courseTitle;//课程标题
        private String courseView;//课程学习数
        private String createdAt;//创建时间
        private String favNum;//收藏数
        private String id;//课程ID
        private String likeNum;//点赞数
        private String mainTag;//标签一级ID
        private double price=-1;//原价
        private double salePrice=-1;//促销价
        private String secTag;//标签二级ID
        private String sort;//排序
        private String teacherId;//教师ID
        private String teacherName;//教师姓名
        private String vip_free;//vip_free默认1
        private CatalogBean coursechapterPage;//目录分页
        private OfflineCourseBean offlineCoursePage;//线下课程
        private CurrCourseChapterBean currCourseChapter;
        private CurrUserBean currUser;
        private String courseIntro_url;//课程介绍url


        public String getCourseIntro_url() {
            return courseIntro_url;
        }

        public void setCourseIntro_url(String courseIntro_url) {
            this.courseIntro_url = courseIntro_url;
        }

        public CurrUserBean getCurrUser() {
            return currUser;
        }

        public void setCurrUser(CurrUserBean currUser) {
            this.currUser = currUser;
        }

        public CurrCourseChapterBean getCurrCourseChapter() {
            return currCourseChapter;
        }

        public void setCurrCourseChapter(CurrCourseChapterBean currCourseChapter) {
            this.currCourseChapter = currCourseChapter;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCourseIntro() {
            return courseIntro;
        }

        public void setCourseIntro(String courseIntro) {
            this.courseIntro = courseIntro;
        }

        public String getCourseStatus() {
            return courseStatus;
        }

        public void setCourseStatus(String courseStatus) {
            this.courseStatus = courseStatus;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getCourseView() {
            return courseView;
        }

        public void setCourseView(String courseView) {
            this.courseView = courseView;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getFavNum() {
            return favNum;
        }

        public void setFavNum(String favNum) {
            this.favNum = favNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(String likeNum) {
            this.likeNum = likeNum;
        }

        public String getMainTag() {
            return mainTag;
        }

        public void setMainTag(String mainTag) {
            this.mainTag = mainTag;
        }

        public String getSecTag() {
            return secTag;
        }

        public void setSecTag(String secTag) {
            this.secTag = secTag;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getVip_free() {
            return vip_free;
        }

        public void setVip_free(String vip_free) {
            this.vip_free = vip_free;
        }

        public CatalogBean getCoursechapterPage() {
            return coursechapterPage;
        }

        public void setCoursechapterPage(CatalogBean coursechapterPage) {
            this.coursechapterPage = coursechapterPage;
        }

        public OfflineCourseBean getOfflineCoursePage() {
            return offlineCoursePage;
        }

        public void setOfflineCoursePage(OfflineCourseBean offlineCoursePage) {
            this.offlineCoursePage = offlineCoursePage;
        }


        public class CurrCourseChapterBean implements Serializable
        {
            private String detailTitle;//图文
            private String audio_timeLen;
            private String audio_url;
            private String video_url;
            private String video_timeLen;
            private String free;
            private String coursePic;
            private ShareObj shareobj;
            private String detailTitle_url;//图文详情url


            public String getDetailTitle_url() {
                return detailTitle_url;
            }

            public void setDetailTitle_url(String detailTitle_url) {
                this.detailTitle_url = detailTitle_url;
            }

            public String getCoursePic() {
                return coursePic;
            }

            public void setCoursePic(String coursePic) {
                this.coursePic = coursePic;
            }

            public ShareObj getShareobj() {
                return shareobj;
            }

            public void setShareobj(ShareObj shareobj) {
                this.shareobj = shareobj;
            }

            public String getFree() {
                return free;
            }

            public void setFree(String free) {
                this.free = free;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public String getVideo_timeLen() {
                return video_timeLen;
            }

            public void setVideo_timeLen(String video_timeLen) {
                this.video_timeLen = video_timeLen;
            }

            public String getDetailTitle() {
                return detailTitle;
            }

            public void setDetailTitle(String detailTitle) {
                this.detailTitle = detailTitle;
            }

            public String getAudio_timeLen() {
                return audio_timeLen;
            }

            public void setAudio_timeLen(String audio_timeLen) {
                this.audio_timeLen = audio_timeLen;
            }

            public String getAudio_url() {
                return audio_url;
            }

            public void setAudio_url(String audio_url) {
                this.audio_url = audio_url;
            }

            @Override
            public String toString() {
                return "CurrCourseChapterBean{" +
                        "detailTitle='" + detailTitle + '\'' +
                        ", audio_timeLen='" + audio_timeLen + '\'' +
                        ", audio_url='" + audio_url + '\'' +
                        ", video_url='" + video_url + '\'' +
                        ", video_timeLen='" + video_timeLen + '\'' +
                        ", free='" + free + '\'' +
                        ", coursePic='" + coursePic + '\'' +
                        ", shareobj=" + shareobj +
                        '}';
            }

            public class ShareObj implements  Serializable
            {
                private String url;
                private String title;
                private String content;
                private String pic;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }


                @Override
                public String toString() {
                    return "ShareObj{" +
                            "url='" + url + '\'' +
                            ", title='" + title + '\'' +
                            ", content='" + content + '\'' +
                            ", pic='" + pic + '\'' +
                            '}';
                }
            }

        }


        public class CatalogBean implements Serializable
        {
            private String totalPage;//总页数
            private String curPage;//当前页数
            private ArrayList<CatalogRecordsBean> records;//小节记录

            public String getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(String totalPage) {
                this.totalPage = totalPage;
            }

            public String getCurPage() {
                return curPage;
            }

            public void setCurPage(String curPage) {
                this.curPage = curPage;
            }

            public ArrayList<CatalogRecordsBean> getRecords() {
                return records;
            }

            public void setRecords(ArrayList<CatalogRecordsBean> records) {
                this.records = records;
            }

            public class CatalogRecordsBean implements Serializable
            {
                private String courseId;//所属课程ID
                private String courseView;//播放数
                private String createdAt;//创建时间
                private String id;//小节ID
                private String sort;//排序字段
                private String status;//章节状态  0上架 1下架 2删除
                private String title;//小节标题
                private int vip_free=-1;//1为vip免费
                private String audioFileid;//FileId
                private String audio_timeLen;//音频时长
                private String audio_url;//音频地址
                private String coursePic;//课程封面
                private String detailTitle;//图文介绍
                private String free;//是否是免费，1为免费
                private String videoFileid;//视频FileId
                private String video_timeLen;//视频时长
                private String video_url;//视频地址


                public String getAudioFileid() {
                    return audioFileid;
                }

                public void setAudioFileid(String audioFileid) {
                    this.audioFileid = audioFileid;
                }

                public String getAudio_timeLen() {
                    return audio_timeLen;
                }

                public void setAudio_timeLen(String audio_timeLen) {
                    this.audio_timeLen = audio_timeLen;
                }

                public String getAudio_url() {
                    return audio_url;
                }

                public void setAudio_url(String audio_url) {
                    this.audio_url = audio_url;
                }

                public String getCoursePic() {
                    return coursePic;
                }

                public void setCoursePic(String coursePic) {
                    this.coursePic = coursePic;
                }

                public String getDetailTitle() {
                    return detailTitle;
                }

                public void setDetailTitle(String detailTitle) {
                    this.detailTitle = detailTitle;
                }

                public String getFree() {
                    return free;
                }

                public void setFree(String free) {
                    this.free = free;
                }

                public String getVideoFileid() {
                    return videoFileid;
                }

                public void setVideoFileid(String videoFileid) {
                    this.videoFileid = videoFileid;
                }

                public String getVideo_timeLen() {
                    return video_timeLen;
                }

                public void setVideo_timeLen(String video_timeLen) {
                    this.video_timeLen = video_timeLen;
                }

                public String getVideo_url() {
                    return video_url;
                }

                public void setVideo_url(String video_url) {
                    this.video_url = video_url;
                }

                public int getVip_free() {
                    return vip_free;
                }

                public void setVip_free(int vip_free) {
                    this.vip_free = vip_free;
                }

                public String getCourseId() {
                    return courseId;
                }

                public void setCourseId(String courseId) {
                    this.courseId = courseId;
                }

                public String getCourseView() {
                    return courseView;
                }

                public void setCourseView(String courseView) {
                    this.courseView = courseView;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSort() {
                    return sort;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                @Override
                public String toString() {
                    return "CatalogRecordsBean{" +
                            "courseId='" + courseId + '\'' +
                            ", courseView='" + courseView + '\'' +
                            ", createdAt='" + createdAt + '\'' +
                            ", id='" + id + '\'' +
                            ", sort='" + sort + '\'' +
                            ", status='" + status + '\'' +
                            ", title='" + title + '\'' +
                            ", vip_free=" + vip_free +
                            ", audioFileid='" + audioFileid + '\'' +
                            ", audio_timeLen='" + audio_timeLen + '\'' +
                            ", audio_url='" + audio_url + '\'' +
                            ", coursePic='" + coursePic + '\'' +
                            ", detailTitle='" + detailTitle + '\'' +
                            ", free='" + free + '\'' +
                            ", videoFileid='" + videoFileid + '\'' +
                            ", video_timeLen='" + video_timeLen + '\'' +
                            ", video_url='" + video_url + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "CatalogBean{" +
                        "totalPage='" + totalPage + '\'' +
                        ", curPage='" + curPage + '\'' +
                        ", records=" + records +
                        '}';
            }
        }

        public class OfflineCourseBean implements Serializable
        {
            private String totalPage;//总页数
            private String curPage;//当前页数
            private ArrayList<OfflineRecordsBean> records;//小节记录

            public String getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(String totalPage) {
                this.totalPage = totalPage;
            }

            public String getCurPage() {
                return curPage;
            }

            public void setCurPage(String curPage) {
                this.curPage = curPage;
            }

            public ArrayList<OfflineRecordsBean> getRecords() {
                return records;
            }

            public void setRecords(ArrayList<OfflineRecordsBean> records) {
                this.records = records;
            }

            public class OfflineRecordsBean implements Serializable
            {
                private String courseName;//线下课程名称
                private String teacherId;//讲师姓名
                private String enrollNumber;//报名人数
                private String courseStatus;//课程状态 0-非正常，1-正常
                private String id;//线下课程ID
                private ArrayList<String> courseInfo;//开课信息
                private String courseCover;//封面图
                private String coursePrice;//课程


                public String getCourseCover() {
                    return courseCover;
                }

                public void setCourseCover(String courseCover) {
                    this.courseCover = courseCover;
                }

                public String getCourseName() {
                    return courseName;
                }

                public void setCourseName(String courseName) {
                    this.courseName = courseName;
                }

                public String getTeacherId() {
                    return teacherId;
                }

                public void setTeacherId(String teacherId) {
                    this.teacherId = teacherId;
                }

                public String getEnrollNumber() {
                    return enrollNumber;
                }

                public void setEnrollNumber(String enrollNumber) {
                    this.enrollNumber = enrollNumber;
                }

                public String getCourseStatus() {
                    return courseStatus;
                }

                public void setCourseStatus(String courseStatus) {
                    this.courseStatus = courseStatus;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public ArrayList<String> getCourseInfo() {
                    return courseInfo;
                }

                public void setCourseInfo(ArrayList<String> courseInfo) {
                    this.courseInfo = courseInfo;
                }

                public String getCoursePrice() {
                    return coursePrice;
                }

                public void setCoursePrice(String coursePrice) {
                    this.coursePrice = coursePrice;
                }
            }

            @Override
            public String toString() {
                return "OfflineCourseBean{" +
                        "totalPage='" + totalPage + '\'' +
                        ", curPage='" + curPage + '\'' +
                        ", records=" + records +
                        '}';
            }
        }


        public class CurrUserBean implements Serializable
        {
            private String isVip;//	isVip: 当前用户是否为vip 1 vip
            private String isBuy;//当前用户是否购买这个课程： 1 购买
            private String isLike;//当前用户是否点赞 1 点赞
            private String isFav;//当前用户是否收藏 1 收藏


            public String getIsVip() {
                return isVip;
            }

            public void setIsVip(String isVip) {
                this.isVip = isVip;
            }

            public String getIsBuy() {
                return isBuy;
            }

            public void setIsBuy(String isBuy) {
                this.isBuy = isBuy;
            }

            public String getIsLike() {
                return isLike;
            }

            public void setIsLike(String isLike) {
                this.isLike = isLike;
            }

            public String getIsFav() {
                return isFav;
            }

            public void setIsFav(String isFav) {
                this.isFav = isFav;
            }


            @Override
            public String toString() {
                return "CurrUserBean{" +
                        "isVip='" + isVip + '\'' +
                        ", isBuy='" + isBuy + '\'' +
                        ", isLike='" + isLike + '\'' +
                        ", isFav='" + isFav + '\'' +
                        '}';
            }
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "commentNum='" + commentNum + '\'' +
                    ", count='" + count + '\'' +
                    ", courseIntro='" + courseIntro + '\'' +
                    ", courseStatus='" + courseStatus + '\'' +
                    ", courseTitle='" + courseTitle + '\'' +
                    ", courseView='" + courseView + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", favNum='" + favNum + '\'' +
                    ", id='" + id + '\'' +
                    ", likeNum='" + likeNum + '\'' +
                    ", mainTag='" + mainTag + '\'' +
                    ", price=" + price +
                    ", salePrice=" + salePrice +
                    ", secTag='" + secTag + '\'' +
                    ", sort='" + sort + '\'' +
                    ", teacherId='" + teacherId + '\'' +
                    ", teacherName='" + teacherName + '\'' +
                    ", vip_free='" + vip_free + '\'' +
                    ", coursechapterPage=" + coursechapterPage +
                    ", offlineCoursePage=" + offlineCoursePage +
                    ", currCourseChapter=" + currCourseChapter +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CourseDetailBean{" +
                "dataBean=" + data +
                '}';
    }
}

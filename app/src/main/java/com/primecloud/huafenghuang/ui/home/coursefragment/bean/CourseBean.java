package com.primecloud.huafenghuang.ui.home.coursefragment.bean;

import java.util.List;

public class CourseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean
    {
        private List<SecTagsBean> secTags;//二级分类
        private int totalPage;
        private int curPage;
        private List<CourseListBean> courses;//课程列表

        public List<SecTagsBean> getSecTags() {
            return secTags;
        }

        public void setSecTags(List<SecTagsBean> secTags) {
            this.secTags = secTags;
        }

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

        public List<CourseListBean> getCourses() {
            return courses;
        }

        public void setCourses(List<CourseListBean> courses) {
            this.courses = courses;
        }



        public class SecTagsBean
        {
            private String createdAt;//创建时间
            private String id;//id
            private String level;//标签级别，1.1级，2.2级
            private String name;//标签名称
            private String parentId;//父标签ID
            private String sort;//排序
            private String state;//状态，1.上架，2.下架
            private String updatedAt;//修改时间


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

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            @Override
            public String toString() {
                return "SecTagsBean{" +
                        "createdAt='" + createdAt + '\'' +
                        ", id='" + id + '\'' +
                        ", level='" + level + '\'' +
                        ", name='" + name + '\'' +
                        ", parentId='" + parentId + '\'' +
                        ", sort='" + sort + '\'' +
                        ", state='" + state + '\'' +
                        ", updatedAt='" + updatedAt + '\'' +
                        '}';
            }
        }

        public class CourseListBean
        {

            private String status;//章节状态 0上架 1下架 2 删除
            private String courseId;//课程ID
            private String id;//小节ID
            private String courseView ;//课程播放数
            private String courseStatus;//课程状态 1 上架 2下架 3删除
            private String secTag;//二级分类ID
            private String coursePic;//封面图
            private String vip_free;//1 vip免费
            private String title;//章节名称  章和节递归循环
            private String created_at;//创建时间
            private String video_fileID;//视频fileID（同resource表fileID）
            private String free;//免费标志，区分与vip免费：1免费
            private String chapterId;//小节ID
            private String chapter_title;//小节标题


            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapter_title() {
                return chapter_title;
            }

            public void setChapter_title(String chapter_title) {
                this.chapter_title = chapter_title;
            }

            public String getFree() {
                return free;
            }

            public void setFree(String free) {
                this.free = free;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCourseView() {
                return courseView;
            }

            public void setCourseView(String courseView) {
                this.courseView = courseView;
            }

            public String getCourseStatus() {
                return courseStatus;
            }

            public void setCourseStatus(String courseStatus) {
                this.courseStatus = courseStatus;
            }

            public String getSecTag() {
                return secTag;
            }

            public void setSecTag(String secTag) {
                this.secTag = secTag;
            }

            public String getCoursePic() {
                return coursePic;
            }

            public void setCoursePic(String coursePic) {
                this.coursePic = coursePic;
            }

            public String getVip_free() {
                return vip_free;
            }

            public void setVip_free(String vip_free) {
                this.vip_free = vip_free;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getVideo_fileID() {
                return video_fileID;
            }

            public void setVideo_fileID(String video_fileID) {
                this.video_fileID = video_fileID;
            }


            @Override
            public String toString() {
                return "CourseListBean{" +
                        "status='" + status + '\'' +
                        ", courseId='" + courseId + '\'' +
                        ", id='" + id + '\'' +
                        ", courseView='" + courseView + '\'' +
                        ", courseStatus='" + courseStatus + '\'' +
                        ", secTag='" + secTag + '\'' +
                        ", coursePic='" + coursePic + '\'' +
                        ", vip_free='" + vip_free + '\'' +
                        ", title='" + title + '\'' +
                        ", created_at='" + created_at + '\'' +
                        ", video_fileID='" + video_fileID + '\'' +
                        ", free='" + free + '\'' +
                        ", chapterId='" + chapterId + '\'' +
                        ", chapter_title='" + chapter_title + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "secTags=" + secTags +
                    ", totalPage='" + totalPage + '\'' +
                    ", curPage='" + curPage + '\'' +
                    ", courses=" + courses +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "data=" + data +
                '}';
    }
}


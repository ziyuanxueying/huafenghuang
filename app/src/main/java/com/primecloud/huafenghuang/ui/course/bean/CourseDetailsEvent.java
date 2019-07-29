package com.primecloud.huafenghuang.ui.course.bean;

public class CourseDetailsEvent {

    private int type = -1; // 0 评论，1 分享,2 评论，3目录点击 刷新详情页
    private int courseId = -1;//课程Id
    private int chapterId = -1;//小节Id


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}

package com.primecloud.huafenghuang.ui.home.findfragment.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;

public class FindBean extends CourseBean implements MultiItemEntity {

    public static final int ITEM_HEAD = 1;//头部布局（轮播，分类）
    public static final int ITEM_TYPE = 2;//类型（热门推荐等）
    public static final int ITEM_LIST = 3;//类型（热门推荐等）
    public static final int ITEM_COURSE = 0;//课程item
    private int itemType = 0;// 默认是课程

    private String typeName ;// 类型名称 热门推荐，免费



    private FindItemBean findItemBean;

    public FindItemBean getFindItemBean() {
        return findItemBean;
    }

    public void setFindItemBean(FindItemBean findItemBean) {
        this.findItemBean = findItemBean;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * coursePic : /home/image/layout/default.png
     * chapterId : 580
     * courseView : 0
     * created_at : 1557368727000
     * chapter_title : 线下课H5链接
     * id : 11
     * courseId : 2790
     */

    private String coursePic;
    private int chapterId;
    private int courseView;
    private long created_at;
    private String chapter_title;
    private int id;
    private int courseId;
    private int free = 1;
    private String summary;//一句话概括

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getCourseView() {
        return courseView;
    }

    public void setCourseView(int courseView) {
        this.courseView = courseView;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "FindBean{" +
                "itemType=" + itemType +
                ", typeName='" + typeName + '\'' +
                ", findItemBean=" + findItemBean +
                ", coursePic='" + coursePic + '\'' +
                ", chapterId=" + chapterId +
                ", courseView=" + courseView +
                ", created_at=" + created_at +
                ", chapter_title='" + chapter_title + '\'' +
                ", id=" + id +
                ", courseId=" + courseId +
                ", vip_free=" + free +
                ", summary=" + summary +
                '}';
    }
}

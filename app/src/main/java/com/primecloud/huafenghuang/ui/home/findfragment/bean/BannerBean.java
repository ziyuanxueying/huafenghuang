package com.primecloud.huafenghuang.ui.home.findfragment.bean;

public class BannerBean {


    /**
     * createdAt : 2019-04-24T17:06:29
     * id : 1
     * path : /uploads/cut/20190424170628.png
     * sort : 1
     * status : 0
     * title : 同城旅游
     * type : 1
     * url : http://www.baidu.com
     *
     */

    private String createdAt;
    private int id;
    private String path;
    private int sort;
    private int status;
    private String title;
    private int type;	//点击跳转目标： 1 -> H5 2 -> 线上课程 3 -> 线下课程
    private String url;
    private int courseId;
    private int courseChapterId;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseChapterId() {
        return courseChapterId;
    }

    public void setCourseChapterId(int courseChapterId) {
        this.courseChapterId = courseChapterId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

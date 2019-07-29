package com.primecloud.huafenghuang.ui.home.coursefragment.bean;

import java.util.ArrayList;

public class MainCourseBean {

    private ArrayList<CateGoryBean> data;

    public ArrayList<CateGoryBean> getData() {
        return data;
    }

    public void setData(ArrayList<CateGoryBean> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "MainCourseBean{" +
                "data=" + data +
                '}';
    }

    public class CateGoryBean
    {
        private String createdAt;//创建日期
        private String id;//id
        private String level;//标签级别，1.1级，2.2级
        private String name;//标签名称
        private String parentId;//父标签ID
        private String sort;//排序
        private String state;//状态，1.上架，2.下架
        private String updatedAt;//更新时间

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
            return "CateGoryBean{" +
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
}

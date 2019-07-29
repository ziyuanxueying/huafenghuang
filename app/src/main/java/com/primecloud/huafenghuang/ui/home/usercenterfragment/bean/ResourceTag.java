package com.primecloud.huafenghuang.ui.home.usercenterfragment.bean;

import java.util.List;

public class ResourceTag {

    /**
     * data : [{"id":0,"name":"全部图文","tagType":1},{"id":1,"name":"萌新","tagType":1},{"id":2,"name":"老司机","tagType":1}]
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
         * id : 0
         * name : 全部图文
         * tagType : 1
         */

        private int id;
        private String name;
        private int tagType;
        private boolean isSelect;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTagType() {
            return tagType;
        }

        public void setTagType(int tagType) {
            this.tagType = tagType;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", tagType=" + tagType +
                    ", isSelect=" + isSelect +
                    '}';
        }
    }
}

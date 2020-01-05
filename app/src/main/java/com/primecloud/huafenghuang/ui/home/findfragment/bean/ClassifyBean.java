package com.primecloud.huafenghuang.ui.home.findfragment.bean;

import com.primecloud.huafenghuang.R;

import java.util.ArrayList;
import java.util.List;

public class ClassifyBean {

    public static List<ClassifyBean> classifyList = new ArrayList<>();
    static {
        classifyList.add(new ClassifyBean(R.mipmap.xinyingshuo, "心颖说", 5));

        classifyList.add(new ClassifyBean(R.mipmap.chengzhang, "个人成长",  1));

        classifyList.add(new ClassifyBean(R.mipmap.jiating, "家庭", 3));

        classifyList.add(new ClassifyBean(R.mipmap.renwen, "事业", 2));

        classifyList.add(new ClassifyBean(R.mipmap.more, "其他", 4));
    }
    private int resourceId;
    private String name;
    private int parentId;

    public ClassifyBean(int resourceId, String name, int parentId) {
        this.resourceId = resourceId;
        this.name = name;
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassifyBean{" +
                "resourceId=" + resourceId +
                ", name='" + name + '\'' +
                '}';
    }
}

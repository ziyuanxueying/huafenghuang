package com.primecloud.huafenghuang.ui.share;

import android.text.TextUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class ShareBean {


    private String platName;// 分享平台名称

    //分享类型
    private int sType;// 1仅图片分享
    //分享类型微信使用
    private int shareType = -1;
    // text是分享文本，所有平台都需要这个字段
    private String text;
    // title标题，微信、QQ和QQ空间等平台使用
    private String title;
    // titleUrl QQ和QQ空间跳转链接
    private String titleUrl;
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    private String imagePath;

    private String imageData;

    // url在微信、微博，Facebook等平台中使用
    private String url;
    // comment是我对这条分享的评论，仅在人人网使用
    private String comment;
    // site是分享此内容的网站名称，仅在QQ空间使用
    private String site;
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    private String siteUrl;

    // 分享结果回调事件
    private PlatformActionListener listener;

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getText() {
        if(TextUtils.isEmpty(text))
            text = "";
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        if(TextUtils.isEmpty(title))
            title = "";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        if(TextUtils.isEmpty(titleUrl))
            titleUrl = "";
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        if(TextUtils.isEmpty(imagePath))
            imagePath = "";
        this.imagePath = imagePath;
    }
    public String getUrl() {
        if(TextUtils.isEmpty(url))
            url = "";
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        if(TextUtils.isEmpty(comment))
            comment = "";
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSite() {
        if(TextUtils.isEmpty(site))
            site = "";
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteUrl() {
        if(TextUtils.isEmpty(siteUrl))
            siteUrl = "";
        return siteUrl;
    }

    public int getShareType() {
        if(shareType == -1){
            shareType=Platform.SHARE_IMAGE;
        }
        return shareType;
    }

    public int getsType() {
        return sType;
    }

    public void setsType(int sType) {
        this.sType = sType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public PlatformActionListener getListener() {
        return listener;
    }

    public void setListener(PlatformActionListener listener) {
        this.listener = listener;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "platName='" + platName + '\'' +
                ", sType=" + sType +
                ", shareType=" + shareType +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", titleUrl='" + titleUrl + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageData='" + imageData + '\'' +
                ", url='" + url + '\'' +
                ", comment='" + comment + '\'' +
                ", site='" + site + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", listener=" + listener +
                '}';
    }
}

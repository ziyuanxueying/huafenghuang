package com.primecloud.huafenghuang.ui.update;

/**
 * Created by ${qc} on 2018/12/20.
 */

public class VersionUpdateModel {
    private String updateLog;// 更新日志
    private String downloadUrl;// 下载地址
    private String release;// 当前版本
    private int isUpdate;// 是否强制更新

    public VersionUpdateModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "VersionUpdateModel{" +
                "updateLog='" + updateLog + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", release='" + release + '\'' +
                ", isUpdate=" + isUpdate +
                '}';
    }
}

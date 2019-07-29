package com.primecloud.huafenghuang.ui.home.usercenterfragment.bean;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MaterialBean {


    /**
     * data : [{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557825918000,"id":19,"tagName":"aaa","content":"阿塞阀森森","resourceType":1},{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557825571000,"id":18,"tagName":"aaa","content":"asdfasfsf","resourceType":1},{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557825511000,"id":17,"tagName":"aaa","content":"asdfdfsf","resourceType":1},{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\",\"\\/uploads\\/sourceMaterial\\/5ce369434602d_5ce3694346069.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557825476000,"id":16,"tagName":"aaa","content":"asdfsdf","resourceType":1},{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\",\"\\/uploads\\/sourceMaterial\\/5ce3692c713eb_5ce3692c71427.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557825474000,"id":15,"tagName":"aaa","content":"asdfsdf","resourceType":1},{"forwards":1,"resourceUrl":"[\"\\/uploads\\/sourceMaterial\\/5ce27d8187657_5ce27d8187694.png\",\"\\/uploads\\/sourceMaterial\\/5ce36923b9c97_5ce36923b9cd3.png\"]","isLike":0,"tagId":1,"downloads":1,"copys":1,"created_at":1557824240000,"id":14,"tagName":"aaa","content":"lalalalala","resourceType":1}]
     * message : 请求处理完成
     */

    private static Gson gson = new Gson();
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
         * forwards : 1
         * resourceUrl : ["\/uploads\/sourceMaterial\/5ce27d8187657_5ce27d8187694.png"]
         * isLike : 0
         * tagId : 1
         * downloads : 1
         * copys : 1
         * created_at : 1557825918000
         * id : 19
         * tagName : aaa
         * content : 阿塞阀森森
         * resourceType : 1
         * likeCount;
         */

        private int forwards;//转发量
        private String resourceUrl;
        private int isLike;//点赞标志：1.点赞 0 未点赞
        private int tagId;
        private int downloads;
        private int copys;
        private long created_at;
        private int id;
        private String tagName;
        private String content;
        private int resourceType;//	资源类型 1 图文 2视频
        private int likedId;//点赞ID
        private int likeCount;// 点赞数量
        private String avatar;// 头像
        private String nickname;
        private String coverUrl;
        private String downloadUrl;
        private String shareUrl;

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getResourceUrl() {
            return resourceUrl;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getLikedId() {
            return likedId;
        }

        public void setLikedId(int likedId) {
            this.likedId = likedId;
        }

        public int getForwards() {
            return forwards;
        }

        public void setForwards(int forwards) {
            this.forwards = forwards;
        }

        public List<String> getResourcePicUrl(){
            List<String> arrayList = null;

            if (!TextUtils.isEmpty(resourceUrl)) {
                arrayList = gson.fromJson(resourceUrl, ArrayList.class);
            }

            if(arrayList == null){
                arrayList = new ArrayList<String>();
            }
            return arrayList ;
        }

        public String getResourceVideoUrl() {

            return resourceUrl ;
        }

        public void setResourceUrl(String resourceUrl) {
            this.resourceUrl = resourceUrl;
        }

        public int getIsLike() {
            return isLike;
        }

        public void setIsLike(int isLike) {
            this.isLike = isLike;
        }

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public int getDownloads() {
            return downloads;
        }

        public void setDownloads(int downloads) {
            this.downloads = downloads;
        }

        public int getCopys() {
            return copys;
        }

        public void setCopys(int copys) {
            this.copys = copys;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getResourceType() {
            return resourceType;
        }

        public void setResourceType(int resourceType) {
            this.resourceType = resourceType;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "forwards=" + forwards +
                    ", resourceUrl='" + resourceUrl + '\'' +
                    ", isLike=" + isLike +
                    ", tagId=" + tagId +
                    ", downloads=" + downloads +
                    ", copys=" + copys +
                    ", created_at=" + created_at +
                    ", id=" + id +
                    ", tagName='" + tagName + '\'' +
                    ", content='" + content + '\'' +
                    ", resourceType=" + resourceType +
                    ", likedId=" + likedId +
                    '}';
        }
    }


}

package com.primecloud.huafenghuang.ui.course.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentBean implements Serializable {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean implements Serializable {
        private String totalPage;
        private String curPage;
        private ArrayList<RecordsBean> records;


        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public String getCurPage() {
            return curPage;
        }

        public void setCurPage(String curPage) {
            this.curPage = curPage;
        }

        public ArrayList<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<RecordsBean> records) {
            this.records = records;
        }

        public class RecordsBean implements Serializable {
            private String content;//	课程留言及回复
            private int courseId;//	课程ID
            private int id;//留言ID
            private int isTop;//是否置顶 默认为0 置顶则为1
            private String msgTime;//留言时间
            private int parentId;//	父ID
            private String publicStr;//	0公开1私密
            private int timeLen;//	语言留言对应时间
            private String type;//1纯文本 2语音
            private int userId;//用户id
            private int replyCount;//回复数
            private String userName;//用户名称
            private String userPic;//用户图片
            private FirstReplyBean firstReply;//第一条回复内容


            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPic() {
                return userPic;
            }

            public void setUserPic(String userPic) {
                this.userPic = userPic;
            }

            public FirstReplyBean getFirstReply() {
                return firstReply;
            }

            public void setFirstReply(FirstReplyBean firstReply) {
                this.firstReply = firstReply;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsTop() {
                return isTop;
            }

            public void setIsTop(int isTop) {
                this.isTop = isTop;
            }

            public String getMsgTime() {
                return msgTime;
            }

            public void setMsgTime(String msgTime) {
                this.msgTime = msgTime;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getPublicStr() {
                return publicStr;
            }

            public void setPublicStr(String publicStr) {
                this.publicStr = publicStr;
            }

            public int getTimeLen() {
                return timeLen;
            }

            public void setTimeLen(int timeLen) {
                this.timeLen = timeLen;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }


            @Override
            public String toString() {
                return "RecordsBean{" +
                        "content='" + content + '\'' +
                        ", courseId=" + courseId +
                        ", id=" + id +
                        ", isTop=" + isTop +
                        ", msgTime='" + msgTime + '\'' +
                        ", parentId=" + parentId +
                        ", publicStr='" + publicStr + '\'' +
                        ", timeLen=" + timeLen +
                        ", type='" + type + '\'' +
                        ", userId=" + userId +
                        ", replyCount=" + replyCount +
                        ", userName='" + userName + '\'' +
                        ", userPic='" + userPic + '\'' +
                        ", firstReply='" + firstReply + '\'' +
                        '}';
            }



            public class FirstReplyBean implements Serializable
            {
                private String content;//	课程留言及回复
                private int courseId;//	课程ID
                private int id;//留言ID
                private int isTop;//是否置顶 默认为0 置顶则为1
                private String msgTime;//留言时间
                private int parentId;//	父ID
                private String publicStr;//	0公开1私密
                private int timeLen;//	语言留言对应时间
                private String type;//1纯文本 2语音
                private int userId;//用户id
                private int replyCount;//回复数
                private String userName;//用户名称
                private String userPic;//用户图片

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getCourseId() {
                    return courseId;
                }

                public void setCourseId(int courseId) {
                    this.courseId = courseId;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getIsTop() {
                    return isTop;
                }

                public void setIsTop(int isTop) {
                    this.isTop = isTop;
                }

                public String getMsgTime() {
                    return msgTime;
                }

                public void setMsgTime(String msgTime) {
                    this.msgTime = msgTime;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public String getPublicStr() {
                    return publicStr;
                }

                public void setPublicStr(String publicStr) {
                    this.publicStr = publicStr;
                }

                public int getTimeLen() {
                    return timeLen;
                }

                public void setTimeLen(int timeLen) {
                    this.timeLen = timeLen;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getUserId() {
                    return userId;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public int getReplyCount() {
                    return replyCount;
                }

                public void setReplyCount(int replyCount) {
                    this.replyCount = replyCount;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getUserPic() {
                    return userPic;
                }

                public void setUserPic(String userPic) {
                    this.userPic = userPic;
                }


                @Override
                public String toString() {
                    return "FirstReplyBean{" +
                            "content='" + content + '\'' +
                            ", courseId=" + courseId +
                            ", id=" + id +
                            ", isTop=" + isTop +
                            ", msgTime='" + msgTime + '\'' +
                            ", parentId=" + parentId +
                            ", publicStr='" + publicStr + '\'' +
                            ", timeLen=" + timeLen +
                            ", type='" + type + '\'' +
                            ", userId=" + userId +
                            ", replyCount=" + replyCount +
                            ", userName='" + userName + '\'' +
                            ", userPic='" + userPic + '\'' +
                            '}';
                }
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "totalPage='" + totalPage + '\'' +
                    ", curPage='" + curPage + '\'' +
                    ", records=" + records +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "data=" + data +
                '}';
    }
}

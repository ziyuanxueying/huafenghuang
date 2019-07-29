package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean;

import java.util.List;

public class CatchoutChannelBean {

    /**
     * data : {"BankcardList":[{"bankId":1002,"bankName":"工商银行","cardName":"刘德华","cardNumber":"123456","id":4,"isDefault":true,"userId":7475}],"WeChat":{"realName":"Test","openId":"abcdefghijk"}}
     * message : 请求处理完成
     */

    private DataBean data;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * BankcardList : [{"bankId":1002,"bankName":"工商银行","cardName":"刘德华","cardNumber":"123456","id":4,"isDefault":true,"userId":7475}]
         * WeChat : {"realName":"Test","openId":"abcdefghijk"}
         */

        private WeChatBean WeChat;
        private List<BankCardBean> BankcardList;

        public WeChatBean getWeChat() {
            return WeChat;
        }

        public void setWeChat(WeChatBean WeChat) {
            this.WeChat = WeChat;
        }

        public List<BankCardBean> getBankcardList() {
            return BankcardList;
        }

        public void setBankcardList(List<BankCardBean> BankcardList) {
            this.BankcardList = BankcardList;
        }

        public static class WeChatBean {
            /**
             * realName : Test
             * openId : abcdefghijk
             */

            private String realName;
            private String openId;

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }
        }


    }
}

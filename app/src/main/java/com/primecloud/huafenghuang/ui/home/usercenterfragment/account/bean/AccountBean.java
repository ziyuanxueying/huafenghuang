package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean;

public class AccountBean {


    /**
     * data : {"balance":0,"isBindingRealName":true,"isBindingWechat":false,"isEnableCatchout":false,"totalIncome":0}
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
         * balance : 0
         * isBindingRealName : true
         * isBindingWechat : false
         * isEnableCatchout : false
         * totalIncome : 0
         * iDCard: "111222333444",
         * realName: "Test"
         *
         */

        private int balance;
        private boolean isBindingRealName;
        private boolean isBindingWechat;
        private boolean isEnableCatchout;
        private boolean isBindingBankCard;
        private int totalIncome;
        private String iDCard;
        private String realName;


        public boolean isBindingBankCard() {
            return isBindingBankCard;
        }

        public void setBindingBankCard(boolean bindingBankCard) {
            isBindingBankCard = bindingBankCard;
        }

        public String getiDCard() {
            return iDCard;
        }

        public void setiDCard(String iDCard) {
            this.iDCard = iDCard;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public boolean isIsBindingRealName() {
            return isBindingRealName;
        }

        public void setIsBindingRealName(boolean isBindingRealName) {
            this.isBindingRealName = isBindingRealName;
        }

        public boolean isIsBindingWechat() {
            return isBindingWechat;
        }

        public void setIsBindingWechat(boolean isBindingWechat) {
            this.isBindingWechat = isBindingWechat;
        }

        public boolean isIsEnableCatchout() {
            return isEnableCatchout;
        }

        public void setIsEnableCatchout(boolean isEnableCatchout) {
            this.isEnableCatchout = isEnableCatchout;
        }

        public int getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(int totalIncome) {
            this.totalIncome = totalIncome;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "balance=" + balance +
                    ", isBindingRealName=" + isBindingRealName +
                    ", isBindingWechat=" + isBindingWechat +
                    ", isEnableCatchout=" + isEnableCatchout +
                    ", isBindingBankCard=" + isBindingBankCard +
                    ", totalIncome=" + totalIncome +
                    ", iDCard='" + iDCard + '\'' +
                    ", realName='" + realName + '\'' +
                    '}';
        }
    }
}

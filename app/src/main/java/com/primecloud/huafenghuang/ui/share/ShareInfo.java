package com.primecloud.huafenghuang.ui.share;

/**
 * Created by ${qc} on 2019/5/16.
 */

public class ShareInfo {

    private UserBean User;
    private int State; //-1表示本次登录成功但尚未绑定手机号，0表示已关联手机号
    private String token;

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class UserBean {
        /**
         * checks : 0
         * code : 1997862
         * id : 7494
         * salt : 874328bb97cd4c7fb31a7afb5322b83d
         * type : 2
         */

        private int checks;
        private String code;
        private int id;
        private String salt;
        private int type;

        public int getChecks() {
            return checks;
        }

        public void setChecks(int checks) {
            this.checks = checks;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "checks=" + checks +
                    ", code='" + code + '\'' +
                    ", id=" + id +
                    ", salt='" + salt + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "User=" + User +
                ", State=" + State +
                '}';
    }
}

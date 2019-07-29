package com.primecloud.huafenghuang.ui.user;

/**
 * Created by ${qc} on 2019/5/13.
 */

public class UserInfo {
    /**
     * checks : 0
     * cityId : 411200
     * code : 64805
     * createdAt : 2019-05-09T15:51:34
     * id : 7483
     * lastLogin : 2019-05-09T15:51:34
     * password : 190aa6f7df7f525003343751cd813ff1
     * phone : 15131372222
     * pic : /home/image/layout/default.png
     * provinceId : 410000
     * salt : ef616ef15c96a5940de4dada8e7673fd
     * sex : 0
     * type : 2
     * updatedAt : 2019-05-09T15:51:34
     * username : 你弟
     */
    private int checks;
    private int cityId;
    private String code;
    private String createdAt;
    private String id;
    private String lastLogin;
    private String password;
    private String phone;
    private String pic;
    private int provinceId;
    private String salt;
    private int sex;
    private int type;
    private String updatedAt;
    private String username;
    private String  birthday;
    private String balance;//余额
    private String expireTime;//VIP到期时间
    private int isVip;//VIP标识 vip标志：1· vip 0 非vip 2 vip 过期
    private String recommenderName;//邀请人
    private int  level; //分销用户等级： 分销用户等级： 1 -> 注册用户、 2 -> 代言人、 3->合伙人 4->分公司
    private boolean unreadedMessageExist;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo() {
    }

    public UserInfo(int cityId, String code, String id, String phone, String pic, int provinceId, String username, String birthday, String balance, String expireTime, int isVip, String recommenderName, int level,boolean unreadedMessageExist) {
        this.cityId = cityId;
        this.code = code;
        this.id = id;
        this.phone = phone;
        this.pic = pic;
        this.provinceId = provinceId;
        this.username = username;
        this.birthday = birthday;
        this.balance = balance;
        this.expireTime = expireTime;
        this.isVip = isVip;
        this.recommenderName = recommenderName;
        this.level = level;
        this.unreadedMessageExist = unreadedMessageExist;
    }

    public boolean isUnreadedMessageExist() {
        return unreadedMessageExist;
    }

    public void setUnreadedMessageExist(boolean unreadedMessageExist) {
        this.unreadedMessageExist = unreadedMessageExist;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getChecks() {
        return checks;
    }

    public void setChecks(int checks) {
        this.checks = checks;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
//        if(TextUtils.isEmpty(id)){
//            return "7475";
//        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "checks=" + checks +
                ", cityId=" + cityId +
                ", code='" + code + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id='" + id + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", pic='" + pic + '\'' +
                ", provinceId=" + provinceId +
                ", salt='" + salt + '\'' +
                ", sex=" + sex +
                ", type=" + type +
                ", updatedAt='" + updatedAt + '\'' +
                ", username='" + username + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}

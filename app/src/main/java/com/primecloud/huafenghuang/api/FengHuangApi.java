package com.primecloud.huafenghuang.api;


import com.lidroid.xutils.http.RequestParams;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.http.OkHttpManager;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.tencent.qq.QQ;
import okhttp3.RequestBody;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public class FengHuangApi {

    public static OkHttpManager okHttpManager = null;

    static {
        okHttpManager = OkHttpManager.getOkHttpManager(NetWorks.baseUrl);
    }

    /**
     * 发送验证码 -- 注册
     *
     * @param phone
     * @param callback
     */
    public static void sendMsg(String phone, HttpCallBack<BizResult> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        okHttpManager.post("api/sms/userRegister", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callback);
    }

    /**
     * 注册
     * 1 	phone	用户手机号	是	[string]	13100000000
     * 2	password	MD5加密的密码字符串，小写	是	[string]	02aa00d0d0d0d0d0d0d0d0d0d0d0d0d0d
     * 3	smscode	短信验证码	是	[string]	546803
     * 4	fromcode	邀请人的7位邀请码，选填
     */
    public static void register(String phone, String password, String smscode, String fromcode, HttpCallBack<BizResult> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("smscode", smscode);
        if (StringUtils.notBlank(fromcode)) {
            map.put("fromcode", fromcode);
        }
        okHttpManager.post("api/users/userRegister", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callback);
    }

    /**
     * 发送忘记密码短信验证码接口
     *
     * @param phone
     * @param callback
     */
    public static void userForgetPassword(String phone, HttpCallBack<BizResult> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        okHttpManager.post("api/sms/userForgetPassword", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callback);
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param smscode
     * @param callback
     */
    public static void forgetPassword(String phone, String password, String smscode, HttpCallBack<BizResult> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("smscode", smscode);
        okHttpManager.putAsy("api/users/userForgetPassword", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callback);
    }


    /**
     * 建议反馈
     *
     * @param userId
     * @param content
     * @param callback
     */
    public static void feedback(String userId, String content, HttpCallBack<BizResult> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("content", content);
        okHttpManager.post("api/about/feedback", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callback);
    }

    /**
     * 关于我们
     *
     * @param callBack
     */
    public static void aboutUs(HttpCallBack<BizResult> callBack) {
        okHttpManager.getAsny("api/about/aboutUs", callBack);
    }

    /**
     * 我的团队-统计信息
     *
     * @param userId
     * @param callBack
     */
    public static void statistics(String userId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        okHttpManager.post("api/myteam/statistics", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 收藏/取消收藏
     *
     * @param courseId 课程ID
     * @param userId   用户ID
     * @param flag     1收藏，2取消收藏
     */
    public static void collectCourse(String courseId, String userId, String flag, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("userId", userId);
        map.put("flag", flag);
        okHttpManager.post("api/course/collectCourse", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 点赞/取消点赞
     *
     * @param courseId 课程ID
     * @param userId   用户ID
     * @param flag     1收藏，2取消收藏
     */
    public static void courseLikes(String courseId, String userId, String flag, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("userId", userId);
        map.put("flag", flag);
        okHttpManager.post("api/course/courseLikes", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @param callBack
     */
    public static void getUserInformation(String userId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        okHttpManager.getAsny("api/users/getUserInformation", map, callBack);
    }

    /**
     * 第三方登录用户发送绑定手机号验证码
     *
     * @param phone
     * @param callBack
     */
    public static void userThirdPartyBindingPhone(String phone, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        okHttpManager.post("api/sms/userThirdPartyBindingPhone", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 第三方登录接口
     */
    public static void userThirdPartyLogin(String idType, String idValue, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("idType", idType);
        map.put("idValue", idValue);
        okHttpManager.post("api/users/userThirdPartyLogin", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 第三方登录用户绑定手机号接口
     *
     * @param idType
     * @param idValue
     * @param phone
     * @param smscode
     * @param fromcode
     * @param callBack
     */
    public static void userThirdPartyBindingPhone(String idType, String idValue, String phone, String smscode, String fromcode,String password,HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("idType", idType);
        map.put("idValue", idValue);
        map.put("phone", phone);
        map.put("smscode", smscode);
        map.put("password",password);
        if (StringUtils.notBlank(fromcode)) {
            map.put("fromcode", fromcode);
        }
        okHttpManager.putAsy("api/users/userThirdPartyBindingPhone", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 获取用户绑定的第三方登录信息
     *
     * @param userId
     * @param callBack
     */
    public static void getUserExtendInformation(String userId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        okHttpManager.getAsny("api/users/getUserExtendInformation", map, callBack);
    }

    /**
     * 解除第三方绑定
     *
     * @param userId
     * @param idType
     * @param callBack
     */
    public static void userUnbindingOthers(String userId, String idType, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("idType", idType);
        okHttpManager.putAsy("api/users/userUnbindingOthers", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 第三方绑定
     *
     * @param userId
     * @param idType
     * @param idValue
     * @param callBack
     */
    public static void userBindingOthers(String userId, String idType, String idValue, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("idType", idType);
        map.put("idValue", idValue);
        okHttpManager.putAsy("api/users/userBindingOthers", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 修改用户昵称
     *
     * @param userId
     * @param userName
     * @param callBack
     */
    public static void updateUserName(String userId, String userName, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("userName", userName);
        okHttpManager.putAsy("api/users/updateUserName", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 用户已读
     *
     * @param userId
     * @param callBack
     */
    public static void updateUserMessageAsReaded(String userId, String messageId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("messageId", messageId);
        okHttpManager.putAsy("api/usermessage/updateUserMessageAsReaded", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 删除消息
     */
    public static void deleteUserMessage(String userId, String ids, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("messageId", ids);
        okHttpManager.deleteAsy("api/usermessage/deleteUserMessage", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 评论发送
     *
     * @param courseId 课程ID
     * @param userId   用户ID
     * @param content  课程留言及回复
     */
    public static void postComment(String courseId, String userId, String content, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("userId", userId);
        map.put("content", content);
        okHttpManager.post("api/course/postComment", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 回复
     *
     * @param commentId 课程ID
     * @param userId    用户ID
     * @param content   课程留言及回复
     */
    public static void replyMsg(String commentId, String userId, String content, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("commentId", commentId);
        map.put("userId", userId);
        map.put("content", content);
        okHttpManager.post("api/course/replyMsg", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 支付-获取用户账户信息
     *
     * @param userId 用户ID
     */
    public static void getUserAccount(String userId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        okHttpManager.getAsny("api/disGeneral/getUserAccount", map, callBack);
    }


    /**
     * 获取邀请图片接口
     *
     * @param userId
     * @param callBack
     */
    public static void getInvitePage(String userId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        okHttpManager.getAsny("api/users/getInvitePage", map, callBack);
    }


    /**
     * 修改用户地址接口
     */
    public static void updateUserAddress(String userId, String cityId, String provinceId, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("provinceId", provinceId);
        map.put("userId", userId);
        map.put("cityId", cityId);
        okHttpManager.putAsy("api/users/updateUserAddress", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 修改用户生日
     */
    public static void updateUserBirthday(String userId, String birthday, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("birthday", birthday);
        okHttpManager.putAsy("api/users/updateUserBirthday", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 发送解绑手机验证码短信
     */
    public static void sendUnbindingPhoneSMS(String phone, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        okHttpManager.post("api/sms/SendUnbindingPhoneSMS", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 发送绑定手机验证码短信
     */
    public static void sendBindingNewPhoneSMS(String phone, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        okHttpManager.post("api/sms/SendBindingNewPhoneSMS", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 用户解绑手机号接口
     */
    public static void userUnbindingPhone(String userId, String phone, String smscode, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("phone", phone);
        map.put("smscode", smscode);
        okHttpManager.post("api/users/userUnbindingPhone", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }

    /**
     * 用户绑定新手机号接口
     */
    public static void userBindingNewPhone(String userId, String phone, String smscode, HttpCallBack<BizResult> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("phone", phone);
        map.put("smscode", smscode);
        okHttpManager.post("api/users/userBindingNewPhone", RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON, okHttpManager.requestGetBySyn(map)), callBack);
    }


    /**
     * 课程学习数
     */
    public static void countCourseView(String userId,String courseId,String chapterId,HttpCallBack<BizResult> callBack)
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("courseId",courseId);
        map.put("chapterId",chapterId);
        okHttpManager.post("api/course/CountcourseView",RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON,okHttpManager.requestGetBySyn(map)),callBack);
    }

    /**
     * 版本更新
     * @param currentRelease
     * @param callBack
     */
    public static void getNewRelease(String currentRelease,HttpCallBack<BizResult> callBack)
    {
        HashMap<String,String> map = new HashMap<>();
        map.put("currentRelease","v"+currentRelease);
        map.put("type","0");
        okHttpManager.post("api/release/getNewRelease",RequestBody.create(OkHttpManager.MEDIA_TYPE_JSON,okHttpManager.requestGetBySyn(map)),callBack);
    }

}

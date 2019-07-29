package com.primecloud.huafenghuang.api;


import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindItemBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BankListBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.BingBankCardList;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.CatchoutChannelBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo.ExpenditureInfoBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo.IncomeInfoBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable.TimeTableBean;
import com.primecloud.huafenghuang.ui.search.bean.AllDataBean;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    //发现-免费+热门课程
    @GET("api/course/listIndexCourses")
    Observable<Response<FindItemBean>> listIndexCourses();


    /**
     * 发现—获取所有分类
     */
    @GET("api/course/listAllCategory/")
    Observable<Response<MainCourseBean>> listAllCategory();

    /**
     * 账户
     */
    @GET("api/users/getMyAccountFirstPage")
    Observable<Response<AccountBean>> getMyAccountFirstPage(@Query("userId") String userId);


    /**
     * 根据分类查询课程列表
     *
     * @param pageNum    当前页数
     * @param categoryId 类别ID，由发现-分类接口返回
     */
    @FormUrlEncoded
    @POST("api/course/listByCategory")
    Observable<Response<CourseBean>> listByCateGory(@Field("pageNum") int pageNum, @Field("categoryId") int categoryId);

    /**
     * 搜索
     *
     * @param searchKey
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("api/course/searchCourses")
    Observable<Response<AllDataBean>> searchCourses(@Field("searchKey") String searchKey, @Field("pageNum") int pageNum);


    /**
     * 免费课程查询/热门课程查询
     *
     * @param pageNum   当前页数
     * @param queryType 课程类别：1免费 2热门
     */
    @FormUrlEncoded
    @POST("api/course/listCoursesByType")
    Observable<Response<CourseBean>> listCourseByType(@Field("pageNum") int pageNum,@Field("queryType") int queryType);

    /**
     * 课程详情
     *
     * @param courseId 课程ID
     * @param pageNum  当前页数
     * @param pageType 1目录分页 2线下课程
     */
    @FormUrlEncoded
    @POST("api/course/listByCourseId")
    Observable<Response<CourseDetailBean>> courseDetail(@Field("courseId") int courseId,@Field("pageNum") String pageNum,@Field("pageType") String pageType,@Field("chapterId") int chapterId,@Field("userId") String userId);

    /**
     * 发送绑定银行卡短信验证码
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("api/sms/userBindingBankcard")
    Observable<Response<Object>> sendBankSmsCode(@Field("phone") String phone);

    /**
     * 用户解绑银行卡接口
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/disBankcard/userBindingBankcard")
    Observable<Response<Object>> userBindingBankcard(@Field("userId") String userId, @Field("cardNumber") String cardNumber, @Field("cardName") String cardName, @Field("bankId") String bankId, @Field("smscode") String smscodes, @Field("phone") String phone);


    /**
     * 获取银行列表接口
     */
    @GET("api/disBankcard/getBankList")
    Observable<Response<BankListBean>> getBankList();

    /**
     * 获取用户绑定的所有银行卡
     */
    @GET("api/disBankcard/getUserBankcardList")
    Observable<Response<BingBankCardList>> getUserBankcardList(@Query("userId") String userId);


    /**
     * 用户解绑银行卡接口
     */
    @DELETE("api/disBankcard/userUnbindingBankcard")
    Observable<Response<Object>> userUnbindingBankcard(@Query("userId") String userId, @Query("cardId") String cardId);


    /**
     * 获取图文资源接口
     */
    @GET("api/sourcematerial/getResourceImageDocument")
    Observable<Response<MaterialBean>> getResourceImageDocument(@Query("userId") String userId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("tagId") int tagId);

    /**
     * 获取视频资源接口
     */
    @GET("api/sourcematerial/getResourceVideo")
    Observable<Response<MaterialBean>> getResourceVideo(@Query("userId") String userId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("tagId") int tagId);

    /**
     * 资源取消点赞接口
     */
    @DELETE("api/sourcematerial/resourceDislike")
    Observable<Response<Object>> resourceDislike(@Query("userId") String userId, @Query("likedId") int likedId);

    /**
     * 资源点赞接口
     */

    @FormUrlEncoded
    @POST("api/sourcematerial/resourceLike")
    Observable<Response<LikeResultBean>> resourceLike(@Field("userId") String userId, @Field("resoutcdId") int resoutcdId);


    /**
     * 课程-评论列表/回复列表查询
     *
     * @param queryId   课程ID/评论ID
     * @param pageNum   当前页数
     * @param queryType 1.评论列表 2.回复列表
     */
    @FormUrlEncoded
    @POST("api/course/listCommentByQueryId")
    Observable<Response<CommentBean>> listCommentByQueryId(@Field("queryId") String queryId, @Field("pageNum") String pageNum, @Field("queryType") String queryType);


    /**
     * 线下课程详情
     *
     * @param courseId 课程ID
     */
    @FormUrlEncoded
    @POST("api/course/listOfflineCourseDetail")
    Observable<Response<OfflineCourseDetailBean>> listOfflineCourseDetail(@Field("courseId") String courseId,@Field("userId") String userId);

    /**
     * 获取用户收益明细
     *
     * @param userId
     * @param pageNumber
     * @param pageSize
     */
    @GET("api/disGeneral/getIncomeInfo")
    Observable<Response<IncomeInfoBean>> listIncomeInfo(@Query("userId") String userId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    /**
     * 获取用户支出明细
     *
     * @param userId
     * @param pageNumber
     * @param pageSize
     */
    @GET("api/disGeneral/getExpenditureInfo")
    Observable<Response<ExpenditureInfoBean>> listExpenditureInfo(@Query("userId") String userId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    /**
     * 获取精品课程接口
     *
     * @param userId
     * @param pageNumber
     * @param pageSize
     */
    @GET("api/timetable/getTimeTable")
    Observable<Response<TimeTableBean>> listTimeTable(@Query("userId") String userId, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);


    /**
     * 精品课程点赞接口
     *
     * @param courseId
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST("api/timetable/timeTableLike")
    Observable<Response<LikeResultBean>> timeTableLike(@Field("userId") String userId, @Field("courseId") String courseId);

    /**
     * 精品课程取消点赞接口
     *
     * @param userId
     * @param cardId
     * @return
     */
    @DELETE("api/timetable/timeTableDislike")
    Observable<Response<Object>> timeTableDislike(@Query("userId") String userId, @Query("likedId") String cardId);


    /**
     * 每日素材-下载量、转发量、复制量+1 或者-1
     *
     * @param userId
     * @param resourceId
     * @param flag        1. 数量+1 2.数量-1
     * @param operateType 1. 下载 2 复制 3.转发
     * @return
     */
    @FormUrlEncoded
    @POST("api/sourcematerial/handleAmount")
    Observable<Response<Object>> handleAmount(@Field("userId") String userId, @Field("resourceId") String resourceId, @Field("flag") int flag, @Field("operateType") int operateType);

    /**
     * 用户实名认证接口
     *
     * @param userId
     */
    @FormUrlEncoded
    @POST("api/users/userBindingNameAndIDCard")
    Observable<Response<Object>> userBindingNameAndIDCard(@Field("userId") String userId, @Field("realName") String realName, @Field("iDCard") String iDCard);

    /**
     * 用户绑定提现微信接口
     * @param userId
     */
    @FormUrlEncoded
    @POST("api/users/userBindingWechat")
    Observable<Response<Object>> userBindingWechat(@Field("userId") String userId, @Field("openId") String openId);

    /**
     * 获取用户所有已绑定的提现方式
     * @param userId
     */
    @GET("api/disGeneral/getCatchoutChannel")
    Observable<Response<CatchoutChannelBean>> getCatchoutChannel(@Query("userId") String userId);

    /**
     * 获取每日素材标分类列表
     * @param type
     * @return 获取的资源类型，1=图文，2=视频
     */
    @GET("api/sourcematerial/getResourceTags")
    Observable<Response<ResourceTag>> getResourceTags(@Query("type") int type);

    /**
     * 用户解绑提现微信接口
     *
     * @param userId
     * @return
     */
    @DELETE("api/users/userUnbindingWechat")
    Observable<Response<Object>> userUnbindingWechat(@Query("userId") String userId);



    /**
     * 申请提现接口
     * @param userId
     * @param type 提现类型，1 -> 微信提现、 2 -》银行卡
     * @param amount
     * @param bankcardId
     * @return
     */
    @FormUrlEncoded
    @POST("api/disGeneral/catchout")
    Observable<Response<Object>> catchout(@Field("userId") String userId, @Field("type") int type, @Field("amount") int amount, @Field("bankcardId") int bankcardId);

    /**
     * 设置用户默认银行卡
     * @param userId

     * @return
     */
    @Multipart
    @PUT("api/disBankcard/setDefaultBankcard")
    Observable<Response<Object>> setDefaultBankcard(@Part("userId") RequestBody userId, @Part("cardId") RequestBody cardId);



    /**
     *  精品课程-转发量、复制量+1 或者-1
     * @param userId
     * @param courseId
     * @param flag 1. 数量+1 2.数量-1
     * @param operateType 2 复制 3.转发
     * @return
     */
    @FormUrlEncoded
    @POST("api/timetable/handleAmount")
    Observable<Response<Object>> handleAmountCourse(@Field("userId") String userId, @Field("courseId") String courseId, @Field("flag") int flag, @Field("operateType") int operateType);
}

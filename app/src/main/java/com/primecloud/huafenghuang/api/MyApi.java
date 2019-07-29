package com.primecloud.huafenghuang.api;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect.CollectionBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member.MemberBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.UserHead;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message.MessageBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.ui.user.login.LoginBean;
import com.primecloud.library.baselibrary.entity.BaseEntity;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/14.
 */

public interface MyApi {

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("api/users/userLogin")
    Observable<Response<LoginBean>> toLogin(@Field("phone") String phone, @Field("password") String password);


    /**
     * 团队成员列表-按类型查
     * @param userId
     * @param memberType
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("api/myteam/listMembers")
    Observable<Response<MemberBean>> getMembers(@Field("userId") String userId, @Field("memberType") int memberType , @Field("pageNum") int pageNum);


    /**
     * 用户订单
     * @param userId
     * @param pageNum
     * @return
     */
    @FormUrlEncoded
    @POST("api/orders/list")
    Observable<Response<OrderBean>> getOrders(@Field("userId") String userId, @Field("pageNum") int pageNum);

    /**
     * 消息
     * @param userId
     * @param pageNum
     * @return
     */
    @GET("api/usermessage/getUserMessage")
    Observable<Response<MessageBean>> getMessages(@Query("userId") String userId, @Query("pageNumber") int pageNum);

    /**
     * 修改用户头像
     * @param map
     * @return
     */
    @Multipart
    @POST("api/users/updateUserPic")
    Observable<Response<UserHead>> enrollInfo(@PartMap Map<String, RequestBody> map);

    /**
     * 收藏
     */
    @GET("api/coursefav/getCourseFav")
    Observable<Response<CollectionBean>> getCollections(@Query("userId") String userId,@Query("pageNum") int pageNum);



}

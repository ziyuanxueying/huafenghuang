package com.primecloud.huafenghuang.utils.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.ApplyResultActivity;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.primecloudpaysdk.version1_0.CreateOrderBean;
import com.primecloud.primecloudpaysdk.version2_0.ConfigParameter;
import com.primecloud.primecloudpaysdk.version2_0.PaySDK;

import rx.functions.Action1;

public class PayUtils {

    private final PaySDK paySDK;
    private Context mContext;
    private String productType;

    private Action1 action1 = new Action1<Integer>() {
        @Override
        public void call(Integer integer) {
            if(integer == PayCallBackResult.PAY_SUCCESS){
                Intent intent = new Intent(mContext,ApplyResultActivity.class);
                intent.putExtra("productType",productType);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        }
    };


    public PayUtils(Context mContext) {
        this.mContext = mContext;

        //获取支付SDK
        paySDK = PaySDK.getPaySDKInstance(((Activity)mContext));
        ConfigParameter configParameter = new ConfigParameter(NetWorks.baseUrl+"epay/createorder");
        configParameter.setCallBack(new PayCallBackResult(action1,mContext));
        paySDK.registerRequestCofig(configParameter);
    }



    /**
     *支付
     * @param payType 支付类型  0 支付宝 1 微信 4余额支付  5 微信h5
     * @param productType 商品类型 0线上课程支付，1线下课程支付，2vip会员支付
     * @param productId productId 课程Id（包括线上，线下）
     * @param userId userId 用户账号
     * @param userName userName 线下课报名-用户名
     * @param tel 线下课报名-手机号
     * @param job 线下课报名-工作
     * @param area 	线下课报名-地区
     * @param courseTime 线下课报名-开课时间
     * @param platform platform 0 -> Android、1 -> iOS、2 -> H5
     * @param orderTitle openId;//openId 公众号 openID
     * @param payMethod 订单标题
     * */
    public void gotoPay(String payType,String productType,String productId,String userId,
                         String userName,String tel,String job,String area,String courseTime,
                         String platform,String orderTitle,PaySDK.PayMethod payMethod){
        this.productType = productType;
        MyApplication.getInstance().setBuyConetxt(mContext);
        MyApplication.getInstance().setProductType(productType);

        PayBean payBean = new PayBean();
        if (StringUtils.notBlank(payType))
        {
            payBean.setPayType(payType);
        }
        if (StringUtils.notBlank(productType))
        {
            payBean.setProductType(productType);
        }
        if (StringUtils.notBlank(productId))
        {
            payBean.setProductId(productId);
        }
        if (StringUtils.notBlank(userId))
        {
            payBean.setUserId(userId);
        }
        if (StringUtils.notBlank(userName))
        {
            payBean.setUserName(userName);
        }
        if (StringUtils.notBlank(tel))
        {
            payBean.setTel(tel);
        }
        if (StringUtils.notBlank(job))
        {
            payBean.setJob(job);
        }
        if (StringUtils.notBlank(area))
        {
            payBean.setArea(area);
        }
        if (StringUtils.notBlank(courseTime))
        {
            payBean.setCourseTime(courseTime);
        }
        if (StringUtils.notBlank(platform))
        {
            payBean.setPlatform(platform);
        }
        if (StringUtils.notBlank(orderTitle))
        {
            payBean.setOrderTitle(orderTitle);
        }
        paySDK.getOrderInfo(payBean, payMethod);
    }
}

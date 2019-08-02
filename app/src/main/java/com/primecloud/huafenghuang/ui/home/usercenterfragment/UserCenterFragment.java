package com.primecloud.huafenghuang.ui.home.usercenterfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.helper.UIHelper;
import com.primecloud.huafenghuang.ui.course.DredgeVIPActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.AccountActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.UserInfoActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect.CollectionActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.TeamActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.InfoIntract;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message.MessageActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SettingActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.material.ClassicalMaterialActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable.TimeTableActivity;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.widget.CircleImageView;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zy on 2018/12/20.
 * 我的
 */

public class UserCenterFragment extends BasePresenterFragment implements InfoIntract {
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.circle_view)
    CircleImageView circleView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.text_vip)
    TextView textVip;
    @BindView(R.id.yaoqing_num)
    TextView yaoqingNum;
    @BindView(R.id.re_my)
    RelativeLayout reMy;
    @BindView(R.id.tv_tuandui)
    TextView tvTuandui;
    @BindView(R.id.tv_zhanghu)
    TextView tvZhanghu;
    @BindView(R.id.tv_yaoqing)
    TextView tvYaoqing;
    @BindView(R.id.line_vip)
    LinearLayout lineVip;
    @BindView(R.id.line_yaoqingma)
    LinearLayout lineYaoqing;
    @BindView(R.id.img_vip)
    ImageView imgVip;
    private int type;//1vip 开通会员 2vip续费
    private boolean isVis = false;
    private boolean isPrepared;
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usercenter, null, false);
        return view;
    }

    @Override
    public void initData() {
        isPrepared = true;
        setUserInfo();
    }

    @Override
    public void initListener() {
    }

    @OnClick({R.id.img_message, R.id.img_setting, R.id.circle_view,
            R.id.tv_tuandui, R.id.tv_zhanghu, R.id.tv_yaoqing, R.id.line_vip,
            R.id.sb_sucai, R.id.sb_kecheng, R.id.sb_dingdan, R.id.sb_shoucang, R.id.sb_fankui, R.id.line_info_change,R.id.re_my})
    public void onClickViw(View view) {
        switch (view.getId()) {
            case R.id.img_message://消息
                isLogin(0);
                break;
            case R.id.img_setting://设置
                isLogin(1);
                break;
            case R.id.circle_view://头像
            case R.id.line_info_change:
                isLogin(2);
                break;
            case R.id.tv_tuandui://团队
                isLogin(3);
                break;
            case R.id.tv_zhanghu://账户
                isLogin(4);
                break;
            case R.id.tv_yaoqing://邀请
                isLogin(5);
                break;
            case R.id.line_vip://vip购买
                isLogin(6);
                break;
            case R.id.sb_sucai://素材
                isLogin(7);
                break;
            case R.id.sb_kecheng://课程
                isLogin(8);
                break;
            case R.id.sb_dingdan://订单
                isLogin(9);
                break;
            case R.id.sb_shoucang://收藏
                isLogin(10);
                break;
            case R.id.sb_fankui://反馈
                isLogin(11);
                break;
            case R.id.re_my:
                isLogin(12);
                break;
            default:
                break;
        }

    }


    public void setUserInfo() {
        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            UserInfo userInfo = MyApplication.getInstance().getUserInfo();
            if (StringUtils.notBlank(userInfo.getPic())) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.mipmap.touxiang)
                        .dontAnimate();//图片加载失败后，显示的图片

                Glide.with(this)
                        .load(userInfo.getPic()) //图片地址
                        .apply(options)
                        .into(circleView);
            }
            if(userInfo.isUnreadedMessageExist()==true){
                imgMessage.setImageResource(R.mipmap.ic_mes);
            }else{
                imgMessage.setImageResource(R.mipmap.ic_new);
            }

            if (StringUtils.notBlank(userInfo.getUsername())) {
                name.setText(userInfo.getUsername());
            }
            if (StringUtils.notBlank(userInfo.getCode())) {
                ViewUtils.setVisible(lineYaoqing);
                yaoqingNum.setText(userInfo.getCode());
            } else {
                ViewUtils.setGone(lineYaoqing);
            }
            if (userInfo.getIsVip() == 1) {//1· vip 0 非vip 2 vip 过期
                ViewUtils.setVisible(imgVip);
                type = 2;
            } else {
                ViewUtils.setGone(imgVip);
                type = 1;
            }
            if (userInfo.getLevel() == 2) {//分销用户等级： 1 -> 注册用户、 2 -> 代言人、 3 -> 合伙人、 4 -> 分公司、 5 -> 合作导师（等同于分公司）、
                ViewUtils.setVisible(company);
                company.setText(getResources().getString(R.string.text_senfen_daiyanren));
            } else if (userInfo.getLevel() == 3) {
                ViewUtils.setVisible(company);
                company.setText(getResources().getString(R.string.text_senfen_hehuoren));
            } else if (userInfo.getLevel() == 4) {
                ViewUtils.setVisible(company);
                company.setText(getResources().getString(R.string.text_senfen_fengongsi));
            } else if (userInfo.getLevel() == 1) {
                ViewUtils.setVisible(company);
                company.setText(getResources().getString(R.string.text_senfen_zhuce));
            } else if(userInfo.getLevel() == 5){
                ViewUtils.setVisible(company);
                company.setText(getResources().getString(R.string.text_senfen_daosi));
            }

            if (StringUtils.notBlank(userInfo.getExpireTime())) {
                ViewUtils.setVisible(textVip);
                textVip.setText("VIP到期时间:" + userInfo.getExpireTime());
            } else {
                ViewUtils.setVisible(textVip);
                textVip.setText("暂未开通VIP");
            }

        }
    }

    @Override
    public void changeInfo(String flag) {
        if (flag.equals("2")) {
            if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                FengHuangApi.getUserInformation(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                    @Override
                    public void onSuccess(String data, BizResult body) {
                        UserInfo Information = JSON.parseObject(body.getData(), UserInfo.class);
                        MyApplication.doLogin(getActivity(), Information);
                        getUserInfo();
                    }

                    @Override
                    public void onFailure(String data, String errorMsg) {

                    }
                });
            }
        }else if(flag.equals("1")){
            setUserInfoIsLogin();
        }
    }


    /*
     * 是否登录
     *
     */
    public void isLogin(int tag) {
        if (null != MyApplication.getInstance().getUserInfo()
                && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            switch (tag) {
                case 0://消息
                    startActivity(new Intent(getContext(), MessageActivity.class));
                    break;
                case 1://设置
                    startActivity(new Intent(getContext(), SettingActivity.class));
                    break;
                case 2://头像
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                    break;
                case 3://团队
                    startActivity(new Intent(getContext(), TeamActivity.class));
                    break;
                case 4://账户
                    startActivity(new Intent(getContext(), AccountActivity.class));
                    break;
                case 5://邀请
                    UIHelper.showSimpleBack(getContext(), SimpleBackPage.Change_Invitave);
                    break;
                case 6://vip购买
                    Intent intent = new Intent(getContext(), DredgeVIPActivity.class);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    break;
                case 7://素材
                    startActivity(new Intent(getContext(), ClassicalMaterialActivity.class));
                    break;
                case 8://课程
                    startActivity(new Intent(getContext(), TimeTableActivity.class));
                    break;
                case 9://订单
                    startActivity(new Intent(getContext(), OrderActivity.class));
                    break;
                case 10://收藏
                    startActivity(new Intent(getContext(), CollectionActivity.class));
                    break;
                case 11://反馈
                    UIHelper.showSimpleBack(getContext(), SimpleBackPage.Change_Suggest);
                    break;
            }
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.putExtra("isLogin", 1);
            startActivity(intent);
        }
    }

    public void setUserInfoIsLogin(){
        Glide.with(this)
                .load(R.mipmap.touxiang) //图片地址
                .into(circleView);
        imgMessage.setImageResource(R.mipmap.ic_new);
        name.setText("未登录");
        ViewUtils.setGone(lineYaoqing);
        ViewUtils.setGone(imgVip);
        ViewUtils.setGone(company);
        ViewUtils.setGone(textVip);
    }

    public void getUserInfo(){
        if(null!=MyApplication.getInstance().getUserInfo()&& StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())){
            FengHuangApi.getUserAccount(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    UserInfo userInfo = JSON.parseObject(body.getData(), UserInfo.class);
                    UserInfo Information = MyApplication.getInstance().getUserInfo();
                    UserInfo info = new UserInfo(Information.getCityId(), Information.getCode(), Information.getId(), Information.getPhone(), Information.getPic(), Information.getProvinceId(), Information.getUsername(), Information.getBirthday(), userInfo.getBalance(), userInfo.getExpireTime(), userInfo.getIsVip(), userInfo.getRecommenderName(), userInfo.getLevel(),userInfo.isUnreadedMessageExist());
                    info.setToken(Information.getToken());
                    MyApplication.doLogin(getActivity(), info);
                    if(getActivity() != null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setUserInfo();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepared == true) {
            getUserInfo();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }
}
         
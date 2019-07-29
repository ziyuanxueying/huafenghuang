/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.share.ShareUtils;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.GlideCacheUtil;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;
import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.sharesdk.framework.Platform;

/**
 * 邀请好友
 */
public class InvitationFragment extends BaseFragmentV4 implements OnBannerListener, View.OnClickListener, ViewPager.OnPageChangeListener {


    TextView toolbarBack;
    TextView toolbarTitle;
    TextView toolbarConfirm;
    Banner banner;
    public List<String> ImageUrl;
    public int currentPagePos;//记录当前的页面下标 用于分享
    ShareUtils shareUtils;

//    private String[] stringItem = {"https://gd2.alicdn.com/imgextra/i1/2259324182/TB2sdjGm0BopuFjSZPcXXc9EpXa_!!2259324182.jpg",
//            "http://img4.tbcdn.cn/tfscom/i1/2259324182/TB2ISF_hKtTMeFjSZFOXXaTiVXa_!!2259324182.jpg",
//            "http://img2.tbcdn.cn/tfscom/i1/2259324182/TB2NAMmm00opuFjSZFxXXaDNVXa_!!2259324182.jpg"};

    private String imageUrl;
    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invite, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        toolbarConfirm = currentView.findViewById(R.id.toolbar_confirm);
        Drawable drawable = getResources().getDrawable(R.mipmap.img_share);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        toolbarConfirm.setCompoundDrawables(null, null, drawable, null);

        banner = currentView.findViewById(R.id.in_banner);
        banner.setOnBannerListener(this);
        banner.setOnPageChangeListener(this);
        toolbarConfirm.setOnClickListener(this);
        ImageUrl = new ArrayList<>();
        getInvitePage();
//        for (int i = 0; i < stringItem.length; i++) {
//            ImageUrl.add(stringItem[i]);
//        }


    }

    private void initBanner() {
//        设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//          设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.CENTER);
//          设置图片加载器
        banner.setImageLoader(new GlideCacheUtil());
//          设置图片集合
        banner.setImages(ImageUrl);
//          设置banner动画效果
//                banner.setBannerAnimation(Transformer.DepthPage);
//          设置标题集合（当banner样式有显示title时）
//                banner.setBannerTitles(titles);
//          设置自动轮播，默认为true
        banner.isAutoPlay(false);
//          设置轮播时间
//         banner.setDelayTime(5000);
        banner.start();

    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_invitate_title));
        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void OnBannerClick(int position) {//图片的点击事件
        setDialog(position);
    }

    Dialog dialog;

    public void setDialog(final int position) {
        dialog = DialogUtils.saveDialog(getContext(), getResources().getString(R.string.long_save), getResources().getString(R.string.choose_photo_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.loadImage(ImageUrl.get(position), getActivity());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        shareUtils = new ShareUtils(getActivity());
        //分享
        shareUtils.share("", null, "", imageUrl, "", Platform.SHARE_IMAGE ,0);
//        shareUtils.shareWX("", null, "", imageUrl, "", Platform.SHARE_IMAGE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPagePos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void getInvitePage(){
        if(NetUtils.isConnected(getActivity())){
            FengHuangApi.getInvitePage(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    JSONObject object = JSONObject.parseObject(body.getData());
                    imageUrl = object.getString("ImageUrl");
                    ImageUrl.add(imageUrl);
                    initBanner();
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }else {
            ToastUtils.showToast(getActivity(),getActivity().getResources().getString(R.string.detection_network));
        }
    }
}


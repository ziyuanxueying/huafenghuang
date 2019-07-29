package com.primecloud.huafenghuang.video;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.library.baselibrary.log.XLog;


import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserActionStd;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import cn.jzvd.JzvdStd;

public class VideoUtils implements View.OnClickListener {
    public Context mContext;
    public JzvdStd mJzvdStd;
    Jzvd.JZAutoFullscreenListener mSensorEventListener;
    SensorManager mSensorManager;
    private String title;
    public static LinearLayout linearLayout;
    private boolean isregister;
    private long currentPosition;
    private int currentState = -1;// 当前播放状态
    public String playUrl;
    private String playTitle;
    private int size;
    private View.OnClickListener clickListener;
    public static VideoUtils videoUtils = null;
    private static View view = null;
    public boolean buyToast = false;
    private String picUrl;
    private View view_image = null;
    private ImageView imageView = null;


    public static VideoUtils getVideoUtils(Context mContext, String title, LinearLayout linearLayout) {

        if (videoUtils == null) {
            videoUtils = new VideoUtils(mContext, title, linearLayout);
        } else {

            addView(linearLayout);
        }
        return videoUtils;
    }


    public VideoUtils(Context mContext, String title, LinearLayout linearLayout) {
        this.mContext = mContext;
        this.title = title;
        this.linearLayout = linearLayout;
        initView();
    }


    public VideoUtils(Context mContext, String title, LinearLayout linearLayout, View.OnClickListener clickListener) {
        this.mContext = mContext;
        this.title = title;
        this.linearLayout = linearLayout;
        this.clickListener = clickListener;
        initView();
    }

    public View getView() {
        return view;
    }

    public void initView() {

        initReceiver();

        mJzvdStd.WIFI_TIP_DIALOG_SHOWED = false;

        view = LayoutInflater.from(mContext).inflate(R.layout.video_play, null);
        mJzvdStd = view.findViewById(R.id.jz_video);
        ViewGroup.LayoutParams layoutParams = mJzvdStd.getLayoutParams();
        layoutParams.height = (int) (((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth() / 1.78f);
//        Jzvd.setMediaInterface(new JZMediaIjkplayer());  //  ijkMediaPlayer
//        Jzvd.setMediaInterface(new JZExoPlayer());  //exo
//        Jzvd.setMediaInterface(new CustomMediaPlayerAssertFolder());  //exo

        linearLayout.addView(view);


        mJzvdStd.setClickListener(this);


        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensorEventListener = new Jzvd.JZAutoFullscreenListener();

        mJzvdStd.myStart.setVisibility(View.VISIBLE);
        if (clickListener != null) {
            mJzvdStd.myStart.setOnClickListener(clickListener);
        }

        mJzvdStd.backButton.setOnClickListener(this);
        mJzvdStd.myStart.setOnClickListener(this);
        mJzvdStd.myShare.setOnClickListener(this);
        mJzvdStd.cut_voice.setOnClickListener(this);

        mJzvdStd.jzDataSource = new JZDataSource("");
//        initPlay();

    }


    /*
     * 设置封面图
     * */
    public void setImage(String url) {
        picUrl = url;
        if (mJzvdStd != null) {
            Glide.with(mContext)
                    .load(url)
                    .into(mJzvdStd.thumbImageView);
        }

    }


    /**
     * 播放
     *
     * @param url
     * @param title
     */
    public void setVideoPlayUrl(String url, String title, int size) {
        buyToast = false;
        playUrl = url;
        playTitle = title;

        if (size > 0) {
            if (size % (1024 * 1024) > 0) {
                this.size = size / 1024 / 1024 + 1;
            } else {
                this.size = size / 1024 / 1024;
            }
        }

        mJzvdStd.setSize(this.size);

        if (!JZUtils.isWifiConnected(mContext) && !mJzvdStd.WIFI_TIP_DIALOG_SHOWED) {
            mJzvdStd.showWifi();

            return;
        }
//        if (!mJzvdStd.isCurrentPlay()) {
        if (mJzvdStd.isMusic) {
            audio(linearLayout);
        }
        mJzvdStd.setUp(url, title
                , JzvdStd.SCREEN_WINDOW_NORMAL);
//        mJzvdStd.startVideo();
        mJzvdStd.setVisibility(View.VISIBLE);
        mJzvdStd.myStart.setVisibility(View.GONE);


    }


    public void onResume() {

        if (!mJzvdStd.isMusic) {
            Sensor accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            if (currentState == Jzvd.CURRENT_STATE_PLAYING) {
                //home back
                Jzvd.goOnPlayOnResume();
            }
        }

    }


    public void onPause() {

        if (!mJzvdStd.isMusic) {
            mSensorManager.unregisterListener(mSensorEventListener);
            Jzvd.clearSavedProgress(mContext, null);


            Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
            if (currentJzvd != null) {
                currentState = currentJzvd.currentState;
                XLog.i("currentState:" + currentState);
            }
            //home back
            Jzvd.goOnPlayOnPause();
        }

    }

    public void onDestroy() {
        buyToast = false;

        if (!mJzvdStd.isMusic || mJzvdStd.isMusic && mJzvdStd.currentState != mJzvdStd.CURRENT_STATE_PLAYING) {
            Jzvd.releaseAllVideos();
            mJzvdStd.clearFloatScreen();
//            mJzvdStd.clearFloatScreen();
//            videoUtils = null;
//            view = null;
            mJzvdStd.isMusic = false;
        }


        if (netReceiver != null && isregister) {
            isregister = false;
            mContext.unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }

    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iswifi_continue:
                mJzvdStd.WIFI_TIP_DIALOG_SHOWED = true;
                if (mJzvdStd.jzDataSource == null || mJzvdStd.jzDataSource != null && !StringUtils.notBlank(mJzvdStd.jzDataSource.getCurrentUrl().toString())) {
                    setVideoPlayUrl(playUrl, playTitle, size);
                } else {
                    mJzvdStd.onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG);
                    mJzvdStd.startVideo();
                }
//                Intent intent = new Intent(mContext, UserSettingActivity.class);
//                mContext.startActivity(intent);
                if (mJzvdStd.dialog != null && mJzvdStd.dialog.isShowing()) {
                    mJzvdStd.dialog.dismiss();
                }
                break;
            case R.id.iswifi_pause:
                mJzvdStd.clearFloatScreen();
                if (mJzvdStd.dialog != null && mJzvdStd.dialog.isShowing()) {
                    mJzvdStd.dialog.dismiss();
                }
                break;
            case R.id.back:
                ((Activity) mContext).finish();
                break;
            case R.id.jz_cut_voice://切换音视频


                if (mJzvdStd.isMusic) {
                    video(linearLayout);
                } else {
                    audio(linearLayout);
                }
                break;
            case R.id.jz_share://分享
                CourseDetailsEvent courseDetailsEvent = new CourseDetailsEvent();
                courseDetailsEvent.setType(1);
                EventBus.getDefault().post(courseDetailsEvent);
                break;
            case R.id.mystart:
                if (buyToast) {
                    if (Utils.isLogin(((Activity) mContext))) {
                        ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.video_open_buy_toast));
                    }

                } else if (!StringUtils.notBlank(playUrl)) {
                    ToastUtils.showToast(mContext, mContext.getResources().getString(R.string.video_open_toast));
                } else {
                    if (mJzvdStd.jzDataSource == null || mJzvdStd.jzDataSource != null && !StringUtils.notBlank(mJzvdStd.jzDataSource.getCurrentUrl().toString())) {
                        setVideoPlayUrl(playUrl, playTitle, size);
                    } else {
                        mJzvdStd.onEvent(JZUserActionStd.ON_CLICK_START_WIFIDIALOG);
                        mJzvdStd.startVideo();
                    }
                }


                break;
        }


    }


    /**
     * 注册网络监听的广播
     */
    public void initReceiver() {
        if (!isregister) {
            isregister = true;
            IntentFilter timeFilter = new IntentFilter();
            timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
            timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
            timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            timeFilter.addAction("android.net.wifi.STATE_CHANGE");
            timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            mContext.registerReceiver(netReceiver, timeFilter);
        }


    }


    BroadcastReceiver netReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type2 = networkInfo.getType();
                    switch (type2) {
                        case 0://移动 网络

                            if (!mJzvdStd.WIFI_TIP_DIALOG_SHOWED) {
                                if (mJzvdStd != null) {
                                    if (mJzvdStd.myStart != null && mJzvdStd.myStart.getVisibility() == View.GONE) {
                                        mJzvdStd.showWifi();
                                    }

                                }

                            }

                            break;
                        case 1: //wifi网络
                            break;

                        case 9:  //网线连接

                            break;
                    }
                } else {// 无网络
                }
            }
        }

    };


    /**
     * 添加音频封面
     */
    public void addPic(Context context) {
        if (view_image == null) {
            view_image = LayoutInflater.from(context).inflate(R.layout.view_image, null);
        }
        if (imageView == null) {
            imageView = (ImageView) view_image.findViewById(R.id.image);
        }

        Glide.with(context)
                .load(picUrl)
                .into(imageView);
        if (mJzvdStd.textureViewContainer != null) {
            ViewGroup parent = (ViewGroup) view_image.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            mJzvdStd.textureViewContainer.addView(view_image);
        }
    }


    /**
     * 音频
     */
    public void audio(LinearLayout linear) {

        addView(linear);

        mJzvdStd.cut_voice.setText(mContext.getResources().getString(R.string.cut_video));
        mJzvdStd.fullscreenButton.setVisibility(View.INVISIBLE);
        if (mJzvdStd.textureViewContainer != null) {
            mJzvdStd.textureViewContainer.removeAllViews();
            mJzvdStd.textureViewContainer.setVisibility(View.VISIBLE);
        }
        addPic(mContext);
        mJzvdStd.isMusic = true;
    }


    /**
     * 视频
     */
    public void video(LinearLayout linear) {

        addView(linear);
        mJzvdStd.cut_voice.setText(mContext.getResources().getString(R.string.cut_voice));
        mJzvdStd.fullscreenButton.setVisibility(View.VISIBLE);
        if (mJzvdStd.textureViewContainer != null && JZMediaManager.textureView != null) {
            mJzvdStd.textureViewContainer.removeAllViews();
            mJzvdStd.textureViewContainer.setVisibility(View.VISIBLE);
            ViewGroup parent = (ViewGroup) JZMediaManager.textureView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            mJzvdStd.textureViewContainer.addView(JZMediaManager.textureView);
        }

        mJzvdStd.isMusic = false;
    }





    /**
     * 是否显示分享，切换按钮
     */
    public void isShowCutShare(boolean isShowShare, boolean isShowCut) {
        if (mJzvdStd != null) {
            mJzvdStd.isShowShare = isShowShare;
            mJzvdStd.isShowCut = isShowCut;
        }
    }


    /*
     * 音频播放，添加画面
     * **/
    public static void addView(LinearLayout linear) {
        if (linearLayout != null && linear != null && linear != linearLayout) {
            linearLayout = linear;
            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }

                linear.addView(view);
            }

        }

    }


    public void clear() {
        linearLayout = null;
        view = null;
        view_image = null;
        imageView = null;
        mContext = null;


    }

}

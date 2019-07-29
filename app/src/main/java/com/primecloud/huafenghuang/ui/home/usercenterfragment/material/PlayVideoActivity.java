package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.primecloud.huafenghuang.R;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import butterknife.BindView;
import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

public class PlayVideoActivity extends CommonBaseActivity {


    @BindView(R.id.act_playvideo)
    JzvdStd actPlayvideo;
    private String videUrl = "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_paly_video);
    }

    @Override
    protected void initData() {


        ImmersionBar
                .with(this)
                .reset()
                .statusBarColor(R.color.black)
                .fitsSystemWindows(true)
                .init();

        videUrl = getIntent().getStringExtra("videoPath");

        JZDataSource jzDataSource = new JZDataSource(videUrl);
        actPlayvideo.setUp(jzDataSource, JzvdStd.SCREEN_WINDOW_NORMAL);
        actPlayvideo.startVideo();

        actPlayvideo.myShare.setVisibility(View.GONE);
        actPlayvideo.cut_voice.setVisibility(View.GONE);
        actPlayvideo.backButton.setImageResource(R.mipmap.close);
        actPlayvideo.fullscreenButton.setVisibility(View.GONE);
        actPlayvideo.startButton.setVisibility(View.GONE);
        actPlayvideo.myStart.setVisibility(View.GONE);
        actPlayvideo.cut_voice.setVisibility(View.GONE);

    }

    @Override
    protected void initEvent() {
        actPlayvideo.backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        actPlayvideo.releaseAllVideos();
    }


}

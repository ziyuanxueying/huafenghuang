package com.primecloud.huafenghuang.ui.user;

import android.content.Intent;
import android.os.Bundle;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.HomeActivity;
import com.primecloud.huafenghuang.utils.HandlerUtils;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

public class SplashActivity extends CommonBaseActivity {

    @Override
    protected void initContentView(Bundle bundle) {

        setContentView(R.layout.act_splash);
    }

    @Override
    protected void initData() {

        ImmersionBar.with(this).reset().hideBar(BarHide.FLAG_HIDE_BAR).init();
        HandlerUtils.mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

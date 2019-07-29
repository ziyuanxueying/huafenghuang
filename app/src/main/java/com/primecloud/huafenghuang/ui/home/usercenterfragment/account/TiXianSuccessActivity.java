package com.primecloud.huafenghuang.ui.home.usercenterfragment.account;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TiXianSuccessActivity extends CommonBaseActivity {

    @BindView(R.id.img_error_layout)
    ImageView imgErrorLayout;
    @BindView(R.id.tv_error_layout)
    TextView tvErrorLayout;
    @BindView(R.id.animProgress)
    ProgressBar animProgress;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_tixian_success);
    }

    @Override
    protected void initData() {
        mToolbar.setToolbarTitleContent("提现成功");
        animProgress.setVisibility(View.GONE);
        imgErrorLayout.setVisibility(View.VISIBLE);
        tvErrorLayout.setText("提现成功");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.primecloud.huafenghuang.ui.home.findfragment;

import android.os.Bundle;
import android.webkit.WebView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.utils.WebViewUtils;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import butterknife.BindView;

public class WebViewActivity extends CommonBaseActivity {
    @BindView(R.id.act_webview)
    WebView actWebview;



    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_webveiw);
    }

    @Override
    protected void initData() {

        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        mToolbar.setToolbarTitleContent(title);
        WebViewUtils.WebViewLoad(actWebview, url);

    }

    @Override
    protected void initEvent() {

    }


}

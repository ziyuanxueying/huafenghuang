/**
 *
 */
package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.fragment;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.WebViewUtils;
import com.primecloud.huafenghuang.widget.MaxLengthEditText;
import com.primecloud.huafenghuang.widget.SettingBar;
import com.primecloud.library.baselibrary.base.BaseFragmentV4;
import com.primecloud.library.baselibrary.log.XLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户协议
 */
public class XieYiFragment extends BaseFragmentV4 {


    TextView toolbarBack;
    TextView toolbarTitle;
    WebView webView;
//    TextView tv;
    String xieyi;

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_xieyi, container, false);
    }

    @Override
    public void initView(View currentView) {
        toolbarBack = currentView.findViewById(R.id.toolbar_back);
        toolbarTitle = currentView.findViewById(R.id.toolbar_title);
        webView = currentView.findViewById(R.id.web_view);
//        tv = currentView.findViewById(R.id.xieyi_view);
        getAboutInfo();
    }

    @Override
    public void initData() {
        toolbarTitle.setText(getResources().getString(R.string.text_xieyi_title));
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

    private String getHTMLStr(String htmlStr) {

        //先将换行符保留，然后过滤标签
        Pattern p_enter = Pattern.compile("<br/>", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(htmlStr);
        htmlStr = m_enter.replaceAll("\n");

        //过滤html标签
        Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);

        //替换回车符
//         Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
//         Matcher m = CRLF.matcher(htmlStr);
//         if (m.find()) {
//         urlStr = m.replaceAll("<br/>");
//         }


        return m_html.replaceAll("");
    }

    public void getAboutInfo() {
        FengHuangApi.aboutUs(new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                JSONObject object = JSONObject.parseObject(body.getData());
                xieyi = object.getString("agreement");
                String a = getHTMLStr(xieyi);
                WebViewUtils.WebViewLoad(webView,a);

//                tv.setText(Html.fromHtml(a));
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }

}


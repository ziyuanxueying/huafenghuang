package com.primecloud.huafenghuang.ui.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.WebViewUtils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import butterknife.BindView;

public class ImageTextFragment extends BasePresenterFragment {


    @BindView(R.id.image_text_webView)
    WebView webView;
    private CourseDetailBean bean;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_imgage_text,null);
    }

    @Override
    public void initData() {
    }


    public void setBean(CourseDetailBean bean) {
        this.bean = bean;
        if (bean!=null&&bean.getDataBean()!=null&&bean.getDataBean().getCurrCourseChapter()!=null&&StringUtils.notBlank(bean.getDataBean().getCurrCourseChapter().getDetailTitle_url()))
        {
            WebViewUtils.WebViewLoad(webView,bean.getDataBean().getCurrCourseChapter().getDetailTitle_url());
        }
    }

    @Override
    public void initListener() {

    }
}

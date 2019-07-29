package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.video.VideoUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OfflineCourseDetailsActivity extends BasePresenterActivity<CoursePresenter, CourseModel> implements CourseContract.View, View.OnClickListener {


    @BindView(R.id.offline_course_details_video)
    LinearLayout video;
    @BindView(R.id.offline_course_details_webView)
    WebView webView;
    @BindView(R.id.apply)
    Button apply;
    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.head_center_text)
    TextView center_text;
    private String courseId;
    private VideoUtils videoUtils;
    private OfflineCourseDetailBean.DataBean dataBean;
    private boolean isPastDue;
    private String userId;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_offline_course_details);
    }

    @Override
    protected void initData() {

        courseId = getIntent().getStringExtra("courseId");
        isPastDue = getIntent().getBooleanExtra("isPastDue", false);


        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            userId = MyApplication.getInstance().getUserInfo().getId();
        } else {
            userId = null;
        }

        mPresenter.offlineDetailsPresenter(courseId, userId);


        center_text.setText(getResources().getString(R.string.course));
        videoUtils = VideoUtils.getVideoUtils(this, "", video);
        videoUtils.mJzvdStd.myShare.setVisibility(View.GONE);
        videoUtils.mJzvdStd.cut_voice.setVisibility(View.GONE);
        videoUtils.mJzvdStd.backButton.setImageDrawable(null);
        videoUtils.mJzvdStd.backButton.setOnClickListener(null);


        if (isPastDue) {
            apply.setVisibility(View.GONE);
        }

    }


    @Override
    protected void initEvent() {

        apply.setOnClickListener(this);
    }


    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

        if (courseDetailBean != null && courseDetailBean.getData() != null) {
            dataBean = courseDetailBean.getData();
            if (StringUtils.notBlank(courseDetailBean.getData().getCourseCover())) {
                videoUtils.setImage(courseDetailBean.getData().getCourseCover());
            }

            if (StringUtils.notBlank(courseDetailBean.getData().getCourseIntro())) {
                webView.loadDataWithBaseURL(null, Utils.getNewContent(courseDetailBean.getData().getCourseIntro()), "text/html", "utf-8", null);
            }
            if (StringUtils.notBlank(courseDetailBean.getData().getCourseVideo())) {
                videoUtils.isShowCutShare(false,false);
                videoUtils.setVideoPlayUrl(courseDetailBean.getData().getCourseVideo(), "", 0);
                videoUtils.initReceiver();
            }
            if (Boolean.valueOf(dataBean.getIsBuy()))
            {
                apply.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }

    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {

    }


    @Override
    public void commentView(CommentBean commentBean) {

    }

    @Override
    @OnClick(R.id.head_left_image)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply:
                if (Utils.isLogin(OfflineCourseDetailsActivity.this)) {
                    Intent intent = new Intent(OfflineCourseDetailsActivity.this, ApplyActivity.class);
                    intent.putExtra("courseId", courseId);
                    intent.putExtra("area_time", dataBean);
                    if (dataBean != null) {
                        intent.putExtra("coursePrice", dataBean.getCoursePrice());
                    }
                    startActivity(intent);
                }

                break;
            case R.id.head_left_image:
                finish();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (videoUtils != null) {
            videoUtils.onPause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (videoUtils != null) {
            videoUtils.onResume();
        }
    }



    @Override
    public void finish() {
        super.finish();
        if (videoUtils != null) {
            videoUtils.onDestroy();
            videoUtils = null;
        }
    }



}

package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.share.ShareUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.ViewUtils;
import com.primecloud.huafenghuang.video.VideoUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.sharesdk.framework.Platform;

public class CourseDetailsActivity extends BasePresenterActivity<CoursePresenter, CourseModel> implements CourseContract.View {


    @BindView(R.id.course_details_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.course_details_tab_layout)
    TabLayout mTab;
    @BindView(R.id.course_details_video)
    LinearLayout mVideo;
    @BindView(R.id.comment_view)
    LinearLayout mView;

    private IntroduceFragment introduceFragment;
    private CatalogFragment catalogFragment;
    private ImageTextFragment imageTextFragment;
    private OfflineCourseFragment offlineCourseFragment;
    private CourseDetailsPagerAdapter pagerAdapter;
    private CommentView commentView;
    private VideoUtils videoUtils;
    private ShareUtils shareUtils;
    private int chapterId;
    private CourseDetailBean courseDetailBean;
    private Boolean music;
    private int courseId;
    private String shareTitle;
    private String shareContent;
    private String sharePic;
    private String shareUrl;
    private boolean isRefresh = true;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_course_details);
    }

    @Override
    protected void initData() {

        commentView = CommentView.getComment(this);
        EventBus.getDefault().register(this);
        chapterId = getIntent().getIntExtra("chapterId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        music = getIntent().getBooleanExtra("isMusic", false);


        if (chapterId == -1) {
            chapterId = MyApplication.getInstance().getChapterId();
        }

        if (courseId == -1) {
            courseId = MyApplication.getInstance().getCourseId();
        }
        if (courseId == MyApplication.getInstance().getCourseId() && chapterId == MyApplication.getInstance().getChapterId() && !music) {
            music = true;
        }


        mTab.setupWithViewPager(mViewPager);


        pagerAdapter = new CourseDetailsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);


        ViewGroup.LayoutParams layoutParams = mVideo.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = (int) (layoutParams.width * 0.56);
        Utils.reflex(mTab, (int) (getResources().getDisplayMetrics().widthPixels * 0.08), (int) (getResources().getDisplayMetrics().widthPixels * 0.08));


        mViewPager.setCurrentItem(1);
    }

    @Override
    protected void initEvent() {

        if (!music) {
            VideoUtils.videoUtils = null;
        }

        videoUtils = VideoUtils.getVideoUtils(this, "", mVideo);
        videoUtils.mJzvdStd.isMusic = music;
        videoUtils.mContext = this;
    }


    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {

        this.courseDetailBean = courseDetailBean;

        if (introduceFragment != null) {
            introduceFragment.setBean(courseDetailBean);
        }

        if (imageTextFragment != null) {
            imageTextFragment.setBean(courseDetailBean);
        }


//        if (videoUtils == null)
//        {
//            VideoUtils.isMusic = music;
//            videoUtils = VideoUtils.getVideoUtils(this, "", mVideo);
//            videoUtils.mContext = this;
//
//        }

        if (!videoUtils.mJzvdStd.isMusic) {

//            videoUtils.video(mVideo);

            if (courseDetailBean != null && courseDetailBean.getDataBean() != null && courseDetailBean.getDataBean().getCurrCourseChapter() != null) {
                if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getCoursePic())) {
                    videoUtils.setImage(courseDetailBean.getDataBean().getCurrCourseChapter().getCoursePic());
                }
                if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getFree()) && courseDetailBean.getDataBean().getCurrCourseChapter().getFree().equals("1")) {
                    if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getVideo_url())) {
                        videoUtils.mJzvdStd.isMusic = false;
                        videoUtils.isShowCutShare(true, true);
                        playVideo(courseDetailBean.getDataBean().getCurrCourseChapter().getVideo_url());
                    } else if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getAudio_url())) {
                        videoUtils.mJzvdStd.isMusic = true;
                        videoUtils.isShowCutShare(true, false);
                        playVideo(courseDetailBean.getDataBean().getCurrCourseChapter().getAudio_url());
                    } else {
                        videoUtils.buyToast = false;
                    }
                } else if (courseDetailBean.getDataBean().getCurrUser() != null) {
                    if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrUser().getIsBuy()) && courseDetailBean.getDataBean().getCurrUser().getIsBuy().equals("1") || StringUtils.notBlank(courseDetailBean.getDataBean().getCurrUser().getIsVip()) && courseDetailBean.getDataBean().getCurrUser().getIsVip().equals("1")) {
                        if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getVideo_url())) {
                            videoUtils.mJzvdStd.isMusic = false;
                            videoUtils.isShowCutShare(true, true);
                            playVideo(courseDetailBean.getDataBean().getCurrCourseChapter().getVideo_url());
                        } else if (StringUtils.notBlank(courseDetailBean.getDataBean().getCurrCourseChapter().getAudio_url())) {
                            videoUtils.mJzvdStd.isMusic = true;
                            videoUtils.isShowCutShare(true, false);
                            playVideo(courseDetailBean.getDataBean().getCurrCourseChapter().getAudio_url());
                        } else {
                            videoUtils.buyToast = false;
                        }
                    } else {
                        videoUtils.buyToast = true;
                    }

                } else {
                    videoUtils.buyToast = true;
                }
            }

        } else {

            videoUtils.audio(mVideo);

        }


    }


    @Override
    public void commentView(CommentBean commentBean) {
        commentView.setRecyclerData(commentBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CourseDetailsEvent courseDetailsEvent) {
        switch (courseDetailsEvent.getType()) {
            case 0://评论
                if (mView != null && mView.getChildCount() == 0) {
                    commentView.setData(courseId + "", introduceFragment);
                    View view = commentView.getmView();
                    if (view == null) {
                        commentView.initView();
                        view = commentView.getmView();
                    }
                    mView.addView(view);
                    commentView.setParent(mView);
                }
                commentView.visibility(true);
                break;
            case 1://分享

                if (shareUtils == null) {
                    //分享
                    shareUtils = new ShareUtils(CourseDetailsActivity.this);
                }

                if (courseDetailBean != null && courseDetailBean.getDataBean() != null && courseDetailBean.getDataBean().getCurrCourseChapter() != null && courseDetailBean.getDataBean().getCurrCourseChapter().getShareobj() != null) {
                    CourseDetailBean.DataBean.CurrCourseChapterBean.ShareObj shareObj = courseDetailBean.getDataBean().getCurrCourseChapter().getShareobj();

                    if (StringUtils.notBlank(shareObj.getTitle())) {
                        shareTitle = shareObj.getTitle();
                    } else {
                        shareTitle = "";
                    }

                    if (StringUtils.notBlank(shareObj.getContent())) {
                        shareContent = shareObj.getContent();
                    } else {
                        shareContent = "";
                    }

                    if (StringUtils.notBlank(shareObj.getPic())) {
                        sharePic = shareObj.getPic();
                    } else {
//                        sharePic = "";
                        sharePic = courseDetailBean.getDataBean().getCurrCourseChapter().getCoursePic();
                    }

                    if (StringUtils.notBlank(shareObj.getUrl())) {
                        shareUrl = shareObj.getUrl();
                    } else {
                        shareUrl = "";
                    }
                }

                if (StringUtils.notBlank(shareUrl)) {
                    shareUtils.share(shareTitle, shareUrl, shareContent, sharePic, shareUrl, Platform.SHARE_WEBPAGE);
                }

                break;
            case 2://评论列表
                mPresenter.commentPresenter(courseId + "", commentView.currentPage + "", "1");
                break;
            case 3://刷新详情
                if (videoUtils != null) {

                    videoUtils.mJzvdStd.isMusic = false;

                    if (videoUtils.mJzvdStd.jzDataSource != null && StringUtils.notBlank(videoUtils.mJzvdStd.jzDataSource.getCurrentUrl().toString())) {
                        videoUtils.onDestroy();
                    }

                }


                courseId = courseDetailsEvent.getCourseId();
                chapterId = courseDetailsEvent.getChapterId();
                isRefresh = true;
                refresh();
                break;
            case 4://清除视频数据
                if (videoUtils != null) {
                    videoUtils.mJzvdStd.isMusic = false;
                    videoUtils.onDestroy();

                }
                isRefresh = true;
                break;


        }


    }


    /*
     * 刷新
     * */
    public void refresh() {
        if (chapterId != -1 && courseId != -1) {
            if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                mPresenter.courseDetailPresenter(courseId, "1", "1", chapterId, MyApplication.getInstance().getUserInfo().getId());
            } else {
                mPresenter.courseDetailPresenter(courseId, "1", "1", chapterId, null);
            }

        }
    }


    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }


    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

    }

    public class CourseDetailsPagerAdapter extends FragmentStatePagerAdapter {

        public CourseDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (introduceFragment == null) {
                        introduceFragment = new IntroduceFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("courseId", courseId + "");
                        introduceFragment.setArguments(bundle);
                    }

                    return introduceFragment;
                case 1:
                    if (catalogFragment == null) {
                        catalogFragment = new CatalogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("chapterId", chapterId);
                        bundle.putInt("courseId", courseId);
                        catalogFragment.setArguments(bundle);
                    }

                    return catalogFragment;
                case 2:
                    if (imageTextFragment == null) {
                        imageTextFragment = new ImageTextFragment();
                    }
                    return imageTextFragment;
                case 3:
                    if (offlineCourseFragment == null) {
                        offlineCourseFragment = new OfflineCourseFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("chapterId", chapterId);
                        bundle.putInt("courseId", courseId);
                        offlineCourseFragment.setArguments(bundle);
                    }

                    return offlineCourseFragment;
            }
            return new Fragment();
        }


        @Override
        public int getCount() {
            return 4;
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return getResources().getString(R.string.introduce);
            } else if (position == 1) {
                return getResources().getString(R.string.catalog);
            } else if (position == 2) {
                return getResources().getString(R.string.image_text);
            } else {
                return getResources().getString(R.string.offline_course);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refresh();


        if (videoUtils != null) {
            if (mVideo != null) {
                videoUtils.addView(mVideo);
            }

            videoUtils.onResume();
        }


        if (commentView != null && mView != null && mView.getVisibility() == View.VISIBLE) {
            commentView.swipeRefresh.setRefreshing(true);
            commentView.onRefresh();
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
    protected void onDestroy() {
        EventBus.getDefault().post(3);
        super.onDestroy();


        EventBus.getDefault().unregister(this);
        commentView.destory();
        commentView = null;


        if (videoUtils != null) {

            if (videoUtils.mJzvdStd.isMusic && videoUtils.mJzvdStd.currentState == videoUtils.mJzvdStd.CURRENT_STATE_PLAYING) {
                MyApplication.getInstance().setChapterId(chapterId);
                MyApplication.getInstance().setCourseId(courseId);

            } else {
                MyApplication.getInstance().setChapterId(-1);
                MyApplication.getInstance().setCourseId(-1);
                videoUtils.onDestroy();
                videoUtils.clear();
            }
            videoUtils = null;
        }
    }


    /**
     * 播放音频
     */
    public void playVideo(String url) {

        if (isRefresh) {
            isRefresh = false;
            videoUtils.setVideoPlayUrl(url, "", 0);
//                    videoUtils.setVideoPlayUrl("http://214.sgld.org:13289/Resource/OnlinePlayUrl?id=a469661fdcf54aaab08d7b2401a5b53b","",0);
            videoUtils.initReceiver();
            if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                addCourse(MyApplication.getInstance().getUserInfo().getId(), courseId + "", chapterId + "");
            }
        }


    }


    /**
     * 增加播放次数
     */
    public void addCourse(String userId, String courseId, String chapterId) {
        FengHuangApi.countCourseView(userId, courseId, chapterId, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {

            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }
}

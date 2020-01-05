package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.CatalogAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.huafenghuang.utils.WebViewUtils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageTextFragment extends BasePresenterFragment<CoursePresenter, CourseModel> implements CourseContract.View,View.OnClickListener  {


    @BindView(R.id.image_text_webView)
    WebView webView;
    private CourseDetailBean bean;

    @BindView(R.id.catalog_buy)
    TextView buy;
    @BindView(R.id.catalog_vip)
    Button vip;
    private CourseDetailBean.DataBean dataBean;

    @BindView(R.id.introduce_comment_collect)
    TextView collect;//收藏
    @BindView(R.id.introduce_comment_message)
    TextView text_message;//消息
    @BindView(R.id.introduce_comment_parse)
    TextView parse;//点赞
    @BindView(R.id.introduce_comment_edit)
    LinearLayout input;
    private String myflag = "2";
    private String isParse = "2";
    private Drawable dr;
    private int collectCount = 0;
    private int parseCount = 0;
    private int messageCount = 0;
    private int courseId;
    private int chapterId;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_imgage_text,null);
    }

    @Override
    public void initData() {
        chapterId = getArguments().getInt("chapterId", -1);
        courseId = getArguments().getInt("courseId", -1);
    }


    public void setBean(CourseDetailBean bean) {
        this.bean = bean;
        if (bean!=null&&bean.getDataBean()!=null&&bean.getDataBean().getCurrCourseChapter()!=null&&StringUtils.notBlank(bean.getDataBean().getCurrCourseChapter().getDetailTitle_url()))
        {
            WebViewUtils.WebViewLoad(webView,bean.getDataBean().getCurrCourseChapter().getDetailTitle_url());
        }
        setData();
    }

    @Override
    public void initListener() {

    }


    /**
     * 设置数据
     */
    public void setData() {

        if (bean != null && bean.getDataBean() != null) {

            if (StringUtils.notBlank(bean.getDataBean().getCommentNum())) {
                messageCount = Integer.parseInt(bean.getDataBean().getCommentNum());
            }

            if (StringUtils.notBlank(bean.getDataBean().getLikeNum())) {
                parseCount = Integer.parseInt(bean.getDataBean().getLikeNum());
            }

            if (StringUtils.notBlank(bean.getDataBean().getFavNum())) {
                collectCount = Integer.parseInt(bean.getDataBean().getFavNum());
            }


            if (bean.getDataBean().getCurrUser() != null) {
                if (StringUtils.notBlank(bean.getDataBean().getCurrUser().getIsFav())) {
                    myflag = bean.getDataBean().getCurrUser().getIsFav();
                }

                if (StringUtils.notBlank(bean.getDataBean().getCurrUser().getIsLike())) {
                    isParse = bean.getDataBean().getCurrUser().getIsLike();
                }
            }

        }


        text_message.setText(messageCount + "");
        CommentView.getComment(getActivity()).message.setText(messageCount + "");
        parse.setText(parseCount + "");
        CommentView.getComment(getActivity()).parse.setText(parseCount + "");
        collect.setText(collectCount + "");
        CommentView.getComment(getActivity()).collect.setText(collectCount + "");

        setCollect(false);
        setParse(false);

        if (chapterId != -1 && courseId != -1) {
            if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                mPresenter.courseDetailPresenter(courseId, "1", "1", chapterId, MyApplication.getInstance().getUserInfo().getId());
            } else {
                mPresenter.courseDetailPresenter(courseId, "1", "1", chapterId, null);
            }

        }

    }


    @Override
    @OnClick({R.id.catalog_buy, R.id.catalog_vip,R.id.introduce_comment_message, R.id.introduce_comment_collect, R.id.introduce_comment_parse, R.id.introduce_comment_edit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catalog_buy:
                if (Utils.isLogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), BuyCourseActivity.class);
                    intent.putExtra("courseId", courseId + "");
                    if (dataBean != null) {
                        intent.putExtra("courseName", dataBean.getCourseTitle());
                        if (dataBean.getSalePrice() != -1) {
                            intent.putExtra("coursePrice", dataBean.getSalePrice());
                        } else {
                            intent.putExtra("coursePrice", dataBean.getPrice());
                        }

                    }
                    getActivity().startActivity(intent);
                }


                break;
            case R.id.catalog_vip:
                buyVip();
                break;
            case R.id.introduce_comment_edit:
            case R.id.introduce_comment_message:
                CourseDetailsEvent courseDetailsEvent = new CourseDetailsEvent();
                courseDetailsEvent.setType(0);
                EventBus.getDefault().post(courseDetailsEvent);
                break;
            case R.id.introduce_comment_collect:
                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                    collect(courseId+"", MyApplication.getInstance().getUserInfo().getId());
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.introduce_comment_parse:
                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                    parse(courseId+"", MyApplication.getInstance().getUserInfo().getId());
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }


    /**
     * 发送评论
     */
    public void postComment(String chapterId, String userId, String content) {
        FengHuangApi.postComment(chapterId, userId, content, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("message");
                    if (msg.equals("请求处理完成")) {
                        CommentView.getComment(getActivity()).edit.setText("");
                        messageCount++;

                        if (text_message != null) {
                            text_message.setText(messageCount + "");
                        }
                        if (CommentView.getComment(getActivity()).message != null) {
                            CommentView.getComment(getActivity()).message.setText(messageCount + "");
                        }


                    }
                    ToastUtils.showToast(getActivity(), msg);
                    CommentView.getComment(getActivity()).issend = false;
                    CommentView.getComment(getActivity()).swipeRefresh.setRefreshing(true);
                    CommentView.getComment(getActivity()).onRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String data, String errorMsg) {
                CommentView.getComment(getActivity()).issend = false;
            }
        });
    }


    /*
     * 收藏/取消收藏
     * */
    public void collect(String chapterId, String userId) {
        FengHuangApi.collectCourse(chapterId, userId, myflag, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {

                setCollect(true);
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }


    /*
     * 点赞/取消点赞
     * */
    public void parse(String chapterId, String userId) {
        FengHuangApi.courseLikes(chapterId, userId, isParse, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {

                setParse(true);
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }
        });
    }


    public void setCollect(boolean isShowCount) {
        if (myflag.equals("1")) {

            dr = getResources().getDrawable(R.mipmap.shoucang_select);
            myflag = "2";
            if (isShowCount) {
                collectCount++;
            }


        } else {
            myflag = "1";
            dr = getResources().getDrawable(R.mipmap.shoucang);
            if (isShowCount) {
                collectCount--;
            }

        }

        dr.setBounds(0, 0, dr.getMinimumWidth(), dr.getMinimumHeight());
        collect.setCompoundDrawables(null, dr, null, null);
        CommentView.getComment(getActivity()).collect.setCompoundDrawables(null, dr, null, null);
        collect.setText(collectCount + "");
        CommentView.getComment(getActivity()).collect.setText(collectCount + "");
    }


    public void setParse(boolean isShowCount) {
        if (isParse.equals("1")) {

            dr = getResources().getDrawable(R.mipmap.dianzan_select);
            isParse = "2";
            if (isShowCount) {
                parseCount++;
            }


        } else {

            dr = getResources().getDrawable(R.mipmap.dianzan_normali);
            isParse = "1";
            if (isShowCount) {
                parseCount--;
            }

        }

        dr.setBounds(0, 0, dr.getMinimumWidth(), dr.getMinimumHeight());
        parse.setCompoundDrawables(null, dr, null, null);
        CommentView.getComment(getActivity()).parse.setCompoundDrawables(null, dr, null, null);
        parse.setText(parseCount + "");
        CommentView.getComment(getActivity()).parse.setText(parseCount + "");
    }


    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }

    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {
        if (courseDetailBean != null && courseDetailBean.getDataBean() != null) {
            dataBean = courseDetailBean.getDataBean();
            if (dataBean != null) {
                if (dataBean.getCurrCourseChapter() != null && StringUtils.notBlank(dataBean.getCurrCourseChapter().getFree()) && dataBean.getCurrCourseChapter().getFree().equals("1")) {
                    vip.setVisibility(View.GONE);
                } else {
                    if (dataBean.getCurrUser() != null&& dataBean.getCurrUser().getIsVip().equals("1")) {
                        vip.setVisibility(View.GONE);
                    } else {
                        vip.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

    }


    /**
     * 购买Vip
     */
    public void buyVip() {
        if (Utils.isLogin(getActivity())) {
            Intent intentvip = new Intent(getActivity(), DredgeVIPActivity.class);
            intentvip.putExtra("type", 1);
            getActivity().startActivity(intentvip);
        }
    }


    @Override
    public void commentView(CommentBean commentBean) {

    }

    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

    }

}

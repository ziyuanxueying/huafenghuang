package com.primecloud.huafenghuang.ui.course;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.CommentAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.AnimationUtil;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CommentView extends View implements View.OnClickListener, CommentAdapter.OnItemClickListener, CommentAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static CommentView commentView;
    private Context mContext;
    private ImageView close;
    public SwipeRefreshLayout swipeRefresh;
    private RecyclerView recycler;
    private List<CommentBean.DataBean.RecordsBean> list = null;
    private CommentAdapter commentAdapter;
    public int currentPage;//当前页数
    private int totalPage;//总页数
    public View mView;
    private LinearLayout parent;
    private IntroduceFragment introduceFragment = null;
    private String courseId = null;
    public TextView parse;
    public TextView collect;
    public TextView message;
    public EditText edit;
    private Button send;
    private LinearLayout zong;
    private OnClickListener click = null;
    public boolean issend;


    public static CommentView getComment(Context context) {
        if (commentView == null) {
            commentView = new CommentView(context);
        }

        return commentView;
    }


    public void setData(String courseId, IntroduceFragment introduceFragment) {
        this.courseId = courseId;
        this.introduceFragment = introduceFragment;
    }


    public void setRecyclerData(CommentBean commentBean) {
        if (commentBean != null && commentBean.getData() != null) {
            currentPage = Integer.parseInt(commentBean.getData().getCurPage());
            totalPage = Integer.parseInt(commentBean.getData().getTotalPage());

            if (commentBean.getData().getRecords() != null && commentBean.getData().getRecords().size() > 0) {
                if (swipeRefresh.isRefreshing()) {
                    list.clear();
                }

                list.addAll(commentBean.getData().getRecords());

            }
        }

        initAdaapter();
    }


    public CommentView(Context mContext) {
        super(mContext);
        this.mContext = mContext;

        list = new ArrayList<>();

        initView();
        initAdaapter();
    }

    public void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.course_comment, null);
        close = (ImageView) mView.findViewById(R.id.comment_close);
        swipeRefresh = (SwipeRefreshLayout) mView.findViewById(R.id.swipeRefresh);
        recycler = (RecyclerView) mView.findViewById(R.id.comment_recyvlerView);
        parse = (TextView) mView.findViewById(R.id.comment_parse);
        collect = (TextView) mView.findViewById(R.id.comment_collect);
        message = (TextView) mView.findViewById(R.id.comment_message);
        edit = (EditText) mView.findViewById(R.id.comment_edit);
        send = (Button) mView.findViewById(R.id.comment_send_button);
        zong = (LinearLayout) mView.findViewById(R.id.comment_zong);

        collect.setOnClickListener(this);
        close.setOnClickListener(this);
        parse.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);


        mView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {


                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > 0)) {

                    //软键盘显示
                    zong.setVisibility(GONE);
                    send.setVisibility(VISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > 0)) {

                    //软键盘隐藏
                    zong.setVisibility(VISIBLE);
                    send.setVisibility(GONE);

                }

            }

        });


        send.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!issend) {

                            if (StringUtils.notBlank(edit.getText().toString())) {
                                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                                    issend = true;
                                    introduceFragment.postComment(courseId, MyApplication.getInstance().getUserInfo().getId(), edit.getText().toString());
                                }
                                else
                                {
                                    mContext.startActivity(new Intent(mContext,LoginActivity.class));
                                }

                            } else {
                                issend = false;
                                ToastUtils.showToast(mContext, "请输入评论");
                            }
                        }
                        break;
                }

                return true;
            }
        });

    }


    public View getmView() {
        return mView;
    }

    public void initAdaapter() {
        if (commentAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(linearLayoutManager);
            commentAdapter = new CommentAdapter(R.layout.comment_recycler_item, list);
            recycler.setAdapter(commentAdapter);
            commentAdapter.setOnItemClickListener(this);
            commentAdapter.setOnLoadMoreListener(this, recycler);
            commentAdapter.disableLoadMoreIfNotFullPage();

        } else {
            commentAdapter.notifyDataSetChanged();
        }

        stopRefreshLoading();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter.getData()!=null&&adapter.getData().size()>0&&position<adapter.getData().size())
        {
            CommentBean.DataBean.RecordsBean recordsBean = (CommentBean.DataBean.RecordsBean)adapter.getData().get(position);
            if (recordsBean!=null)
            {
                Intent intent = new Intent(mContext,ReplyActivity.class);
                intent.putExtra("replyId",recordsBean.getId()+"");
                intent.putExtra("comment",recordsBean);
                intent.putExtra("replyCount",recordsBean.getReplyCount());
                mContext.startActivity(intent);
            }

        }
    }


    @Override
    public void onLoadMoreRequested() {

        if (swipeRefresh.isRefreshing())
            return;
        if (currentPage + 1 <= totalPage) {
            refresh((currentPage + 1));
        }
    }


    @Override
    public void onRefresh() {
        refresh(1);
    }


    public void refresh(int pageNumber) {
        currentPage = pageNumber;
        CourseDetailsEvent courseDetailsEvent = new CourseDetailsEvent();
        courseDetailsEvent.setType(2);
        EventBus.getDefault().post(courseDetailsEvent);

    }


    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            commentAdapter.setEnableLoadMore(true);
        }

        if (currentPage >= totalPage) {
            commentAdapter.loadMoreEnd();
        } else {
            commentAdapter.loadMoreComplete();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.comment_close:
                visibility(false);
                break;
            case R.id.comment_parse:
                if (introduceFragment != null) {
                    if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                        introduceFragment.parse(courseId, MyApplication.getInstance().getUserInfo().getId());
                    }
                    else
                    {
                        mContext.startActivity(new Intent(mContext,LoginActivity.class));
                    }
                }
                break;
            case R.id.comment_collect:
                if (introduceFragment != null) {
                    if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                        introduceFragment.collect(courseId, MyApplication.getInstance().getUserInfo().getId());
                    }
                    else
                    {
                        mContext.startActivity(new Intent(mContext,LoginActivity.class));
                    }
                }
                break;

        }
    }


    public void setParent(LinearLayout parent) {
        this.parent = parent;
    }


    /**
     * 显示/隐藏
     * */
    public void visibility(boolean isvisibility) {

        if (parent!=null)
        {
            if (isvisibility && parent.getVisibility() == GONE) {
                parent.setVisibility(VISIBLE);
                parent.setClickable(true);
                parent.setFocusable(true);
                parent.setAnimation(AnimationUtil.moveToViewLocation());
                swipeRefresh.setRefreshing(true);
                onRefresh();
            } else if (!isvisibility && parent.getVisibility() == VISIBLE) {
                parent.setVisibility(GONE);
                parent.setClickable(false);
                parent.setFocusable(false);
                parent.setAnimation(AnimationUtil.moveToViewBottom());
                edit.setText("");
                stopRefreshLoading();
            }
        }

    }


    /**
     * 销毁
     * */
    public void destory()
    {
        visibility(false);
       commentView = null;
       mView = null;
       commentAdapter = null;
       list = null;
        parent = null;

    }


}

package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.CatalogAdapter;
import com.primecloud.huafenghuang.ui.course.adapter.ReplyAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.utils.DatesUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.library.baselibrary.utils.glideutils.GlideImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ReplyActivity extends BasePresenterActivity<CoursePresenter,CourseModel> implements CourseContract.View,SwipeRefreshLayout.OnRefreshListener,ReplyAdapter.RequestLoadMoreListener,View.OnClickListener {


    @BindView(R.id.head_center_text)
    TextView text_center;
    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.reply_round_image)
    RoundedImageView round_image;
    @BindView(R.id.reply_comment_content)
    TextView comment_content;
    @BindView(R.id.reply_comment_name)
    TextView comment_name;
    @BindView(R.id.reply_comment_time)
    TextView comment_time;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.reply_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.comment_edit)
    EditText comment_edit;
    @BindView(R.id.comment_send)
    TextView comment_send;


    private ArrayList<CommentBean.DataBean.RecordsBean> list = null;
    private int currentPage;//当前页数
    private int totalPage;//总页数
    private String replyId;
    private ReplyAdapter replyAdapter = null;
    private CommentBean.DataBean.RecordsBean comment;
    private int replyCount;
    private boolean isSend = false;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_reply);
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();
        replyId = getIntent().getStringExtra("replyId");
        comment = (CommentBean.DataBean.RecordsBean)getIntent().getSerializableExtra("comment");
        replyCount = getIntent().getIntExtra("replyCount",0);

        text_center.setText(replyCount+getResources().getString(R.string.reply_count));

        setData();
        iniData();
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }


    /**
     * 设置留言数据
     * */
    public void setData()
    {
        if (comment!=null)
        {
            if (StringUtils.notBlank(comment.getUserPic()))
            {
                GlideImageLoader.getInstance().displayImage(this,comment.getUserPic(),round_image);
            }

            if (StringUtils.notBlank(comment.getContent()))
            {
                comment_content.setText(comment.getContent());
            }

            if (StringUtils.notBlank(comment.getUserName()))
            {
                comment_name.setText(comment.getUserName());
            }

            if (StringUtils.notBlank(comment.getMsgTime()))
            {
                comment_time.setText(DatesUtils.friendly_time(comment.getMsgTime()+":00"));
            }

        }
    }


    /**
     * 初始化适配器
     * */
    public void iniData()
    {
        if (replyAdapter == null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);
            replyAdapter = new ReplyAdapter(R.layout.item_reply,list);
            recyclerView.setAdapter(replyAdapter);
            replyAdapter.setOnLoadMoreListener(this,recyclerView);
            replyAdapter.disableLoadMoreIfNotFullPage();
        }
        else
        {
            replyAdapter.notifyDataSetChanged();
        }

        stopRefreshLoading();
    }

    @Override
    protected void initEvent() {

        swipeRefresh.setOnRefreshListener(this);

        comment_send.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        if (!isSend)
                        {

                            if (StringUtils.notBlank(comment_edit.getText().toString()))
                            {
                                if (MyApplication.getInstance().getUserInfo()!=null&&StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId()))
                                {
                                    isSend = true;
                                    reply(comment.getId()+"",MyApplication.getInstance().getUserInfo().getId(),comment_edit.getText().toString());
                                }
                                else
                                {
                                    startActivity(new Intent(ReplyActivity.this,LoginActivity.class));
                                }

                            }
                            else
                            {
                                isSend = false;
                                ToastUtils.showToast(ReplyActivity.this,"请输入评论");
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public void commentView(CommentBean commentBean) {
        if (commentBean!=null&&commentBean.getData()!=null)
        {
            CommentBean.DataBean dataBean = commentBean.getData();
            currentPage = Integer.parseInt(dataBean.getCurPage());
            totalPage = Integer.parseInt(dataBean.getTotalPage());

            if (dataBean.getRecords()!=null&&dataBean.getRecords().size()>0)
            {
                if (swipeRefresh.isRefreshing())
                {
                    list.clear();
                }

                list.addAll(dataBean.getRecords());
            }
        }

        iniData();
    }

    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }

    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {

    }


    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

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
        if (StringUtils.notBlank(replyId))
        {
           mPresenter.commentPresenter(replyId,pageNumber+"","2");
        }

    }


    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            replyAdapter.setEnableLoadMore(true);
        }

        if (currentPage >= totalPage) {
            replyAdapter.loadMoreEnd();
        } else {
            replyAdapter.loadMoreComplete();
        }

    }


    @Override
    @OnClick({R.id.head_left_image})
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.head_left_image:
                finish();
                break;
        }
    }


    /**
     * 回复
     * */
    public void reply(String commentId,String userId,String content)
    {
        FengHuangApi.replyMsg(commentId, userId, content, new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, BizResult body) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String msg = jsonObject.getString("message");
                    if (msg.equals("请求处理完成"))
                    {
                        replyCount++;
                        comment_edit.setText("");
                    }
                    text_center.setText(replyCount+getResources().getString(R.string.reply_count));
                    swipeRefresh.setRefreshing(true);
                    onRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                isSend = false;
            }

            @Override
            public void onFailure(String data, String errorMsg) {
                isSend = false;
            }
        });
    }
}

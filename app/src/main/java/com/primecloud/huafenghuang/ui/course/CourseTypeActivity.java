package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.adapter.CourseAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CourseTypeActivity extends BasePresenterActivity<CoursePresenter,CourseModel> implements CourseContract.View,CourseAdapter.OnItemClickListener,CourseAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {


    @BindView(R.id.fragment_course_type_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.head_left_image)
    ImageView imageReturn;
    @BindView(R.id.head_center_text)
    TextView centerText;

    private CourseAdapter adapter;

    private int currentPage;//当前页数
    private int totalPage;//总页数
    private String secTagId;
    private List<CourseBean.DataBean.CourseListBean> list = null;
    private int queryType;//课程类别：1免费，2热门


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_course_type);
    }

    @Override
    public void initData() {

        list = new ArrayList<>();

        queryType = getIntent().getIntExtra("queryType",1);

        if (queryType == 1)
        {
            centerText.setText(getResources().getString(R.string.course_free));
        }
        else if (queryType == 2)
        {
            centerText.setText(getResources().getString(R.string.course_hot));
        }


        initAdapter();
        swipeRefresh.setRefreshing(true);
        onRefresh();


    }



    @Override
    protected void initEvent() {
        swipeRefresh.setOnRefreshListener(this);
    }

    public void initAdapter()
    {
        if (adapter == null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new CourseAdapter(R.layout.course_list_item,list);
            adapter.setQueryType(queryType);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnLoadMoreListener(this,mRecyclerView);
            adapter.disableLoadMoreIfNotFullPage();
        }
        else
        {
            adapter.notifyDataSetChanged();
        }

        stopRefreshLoading();

    }


    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

        if (dataBean!=null&&dataBean.getData()!=null)
        {

            CourseBean.DataBean  data = dataBean.getData();
            if (data!=null)
            {
                totalPage = data.getTotalPage();
                currentPage = data.getCurPage();
                if (data.getCourses()!=null&&data.getCourses().size()>0)
                {

                    if (swipeRefresh.isRefreshing())
                    {
                        list.clear();
                    }

                    list.addAll(data.getCourses());
                }
            }

        }

        initAdapter();
    }


    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        initAdapter();
    }

    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {

    }


    @Override
    public void commentView(CommentBean commentBean) {

    }

    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter.getData()!=null&&adapter.getData().size()>0&&position<adapter.getData().size())
        {
            CourseBean.DataBean.CourseListBean courseListBean = (CourseBean.DataBean.CourseListBean)adapter.getData().get(position);
            if (courseListBean!=null)
            {
                Intent intent = new Intent(this,CourseDetailsActivity.class);
                intent.putExtra("chapterId",Integer.parseInt(courseListBean.getChapterId()));
                intent.putExtra("courseId",Integer.parseInt(courseListBean.getCourseId()));
                startActivity(intent);
            }


        }

    }

    @Override
    @OnClick(R.id.head_left_image)
    public void onClick(View v) {
          switch (v.getId())
          {
              case R.id.head_left_image:
                  finish();
                  break;
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
        mPresenter.listCourseByTypePresenter(pageNumber,queryType);
    }


    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            adapter.setEnableLoadMore(true);
        }

        if (currentPage >= totalPage) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        list = null;
        adapter = null;
    }
}

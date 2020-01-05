package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.adapter.CourseAdapter;
import com.primecloud.huafenghuang.ui.course.adapter.CourseTypeAdapter;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseModel;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCoursePresenter;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 课程二级页面跳转的列表页
 */
public class CourseTypeList2Activity extends BasePresenterActivity<MainCoursePresenter, MainCourseModel> implements MainCourseContract.View, View.OnClickListener,
        CourseAdapter.OnItemClickListener, CourseAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.head_center_text)
    TextView title;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_course_type_recycler)
    RecyclerView mRecyclerView;

    private CourseAdapter adapter;
    private int secTagId;
    private String name;

    private int currentPage;//当前页数
    private int totalPage;//总页数
    private List<CourseBean.DataBean.CourseListBean> list = new ArrayList<>();

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_course_type);
    }

    @Override
    protected void initData() {
        secTagId = Integer.parseInt(getIntent().getStringExtra("secTagId"));
        name = getIntent().getStringExtra("name");


        if (StringUtils.notBlank(name)) {
            title.setText(name);
        }


        initAdapter();
        swipeRefresh.setRefreshing(true);
        onRefresh();
    }


    @Override
    protected void initEvent() {
        swipeRefresh.setOnRefreshListener(this);
    }

    public void initAdapter() {
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new CourseAdapter(R.layout.course_list_item, list);
            adapter.setQueryType(secTagId);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnLoadMoreListener(this, mRecyclerView);
            adapter.disableLoadMoreIfNotFullPage();
        } else {
            adapter.notifyDataSetChanged();
        }
        stopRefreshLoading();

    }

    @Override
    public void listByCateGoryView(CourseBean dataBean) {
        if (dataBean != null && dataBean.getData() != null) {
            CourseBean.DataBean data = dataBean.getData();
            if (data != null) {
                totalPage = data.getTotalPage();
                currentPage = data.getCurPage();
                if (data.getCourses().size() > 0) {

                    if (swipeRefresh.isRefreshing()) {
                        list.clear();
                    }

                    list.addAll(data.getCourses());
                }
            }

        }

        initAdapter();
    }

    @Override
    public void mainCourseView(MainCourseBean mainCourseBean) {

    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        initAdapter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter.getData().size() > 0 && position < adapter.getData().size()) {
            CourseBean.DataBean.CourseListBean courseListBean = (CourseBean.DataBean.CourseListBean) adapter.getData().get(position);
            if (courseListBean != null) {
                Intent intent = new Intent(this, CourseDetailsActivity.class);
                intent.putExtra("chapterId", Integer.parseInt(courseListBean.getId()));
                intent.putExtra("courseId", Integer.parseInt(courseListBean.getCourseId()));
                startActivity(intent);
            }


        }

    }

    @Override
    @OnClick(R.id.head_left_image)
    public void onClick(View v) {
        switch (v.getId()) {
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
        mPresenter.listByCateGoryPresenter(pageNumber, secTagId);
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

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
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.CatalogAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 目录
 */
public class CatalogActivity extends BasePresenterActivity<CoursePresenter, CourseModel> implements CourseContract.View, SwipeRefreshLayout.OnRefreshListener, CatalogAdapter.OnItemClickListener, CatalogAdapter.RequestLoadMoreListener, View.OnClickListener {


    private ArrayList<CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean> list = null;
    private CatalogAdapter catalogAdapter;
    private int currentPage;//当前页数
    private int totalPage;//总页数
    private int chapterId;
    private CourseDetailBean.DataBean dataBean;
    private int courseId;
    private boolean islock = true;


    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.head_center_text)
    TextView title;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_course_type_recycler)
    RecyclerView recyclerView;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_course_type);
    }

    @Override
    protected void initData() {
        title.setText("视频目录");
        list = new ArrayList<>();

        chapterId = getIntent().getIntExtra("chapterId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        initAdapter();
        swipeRefresh.setRefreshing(true);
        onRefresh();

    }


    @Override
    protected void initEvent() {
        swipeRefresh.setOnRefreshListener(this);
    }

    public void initAdapter() {

        if (catalogAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            catalogAdapter = new CatalogAdapter(R.layout.catalog_recycler_item, list, chapterId+"");
            catalogAdapter.setIslock(islock);
            recyclerView.setAdapter(catalogAdapter);
            catalogAdapter.setOnItemClickListener(this);
            catalogAdapter.setOnLoadMoreListener(this, recyclerView);
            catalogAdapter.disableLoadMoreIfNotFullPage();
        } else {
            catalogAdapter.setIslock(islock);
            catalogAdapter.notifyDataSetChanged();
        }
        stopRefreshLoading();

    }



    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        initAdapter();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        Log.i("FJ",list.get(position).getId());
        intent.putExtra("chapterId", Integer.parseInt(list.get(position).getId()) );
        intent.putExtra("courseId", courseId);
        startActivity(intent);
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
        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            mPresenter.courseDetailPresenter(courseId, pageNumber + "", "1", chapterId, MyApplication.getInstance().getUserInfo().getId());
        } else {
            mPresenter.courseDetailPresenter(courseId, pageNumber + "", "1", chapterId, null);
        }

    }


    // 停止刷新或者加载
    private void stopRefreshLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
            catalogAdapter.setEnableLoadMore(true);
        }

        if (currentPage >= totalPage) {
            catalogAdapter.loadMoreEnd();
        } else {
            catalogAdapter.loadMoreComplete();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        list = null;
        catalogAdapter = null;
    }

    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }

    @Override
    public void courseDetailView(CourseDetailBean courseDetailBean) {
        dataBean = courseDetailBean.getDataBean();
        if (dataBean != null && dataBean.getCoursechapterPage() != null) {
            currentPage = Integer.parseInt(dataBean.getCoursechapterPage().getCurPage());
            totalPage = Integer.parseInt(dataBean.getCoursechapterPage().getTotalPage());

            if (dataBean.getCoursechapterPage().getRecords() != null && dataBean.getCoursechapterPage().getRecords().size() > 0) {
                if (swipeRefresh.isRefreshing()) {
                    list.clear();
                }
                list.addAll(dataBean.getCoursechapterPage().getRecords());
            }
        }
        initAdapter();

    }

    @Override
    public void commentView(CommentBean commentBean) {

    }

    @Override
    public void offlineDetailsView(OfflineCourseDetailBean courseDetailBean) {

    }
}

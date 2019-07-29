package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.adapter.CourseAdapter;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseModel;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCoursePresenter;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseFragment extends BasePresenterFragment<MainCoursePresenter,MainCourseModel> implements MainCourseContract.View, CourseAdapter.RequestLoadMoreListener,CourseAdapter.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fragment_course_list_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private CourseAdapter adapter;

    private int currentPage;//当前页数
    private int totalPage;//总页数
    private String secTagId;
    private List<CourseBean.DataBean.CourseListBean> list = null;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_course_list,null);
    }

    @Override
    public void initData() {

        list = new ArrayList<>();

        secTagId = getArguments().getString("secTagId");


        initAdapter();
        swipeRefresh.setRefreshing(true);
        onRefresh();


    }


    @Override
    public void initListener() {
        swipeRefresh.setOnRefreshListener(this);
    }


    public void initAdapter()
    {
        if (adapter == null)
        {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new CourseAdapter(R.layout.course_list_item,list);
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
    public void listByCateGoryView(CourseBean courseBean) {
        if (courseBean!=null&&courseBean.getData()!=null)
        {
            CourseBean.DataBean dataBean = courseBean.getData();
            totalPage = dataBean.getTotalPage();
            currentPage = dataBean.getCurPage();
            if (dataBean.getCourses()!=null&&dataBean.getCourses().size()>0)
            {

                if (swipeRefresh.isRefreshing())
                {
                    list.clear();
                }

                list.addAll(dataBean.getCourses());

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
    public void mainCourseView(MainCourseBean mainCourseBean) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            if (adapter.getData()!=null&&adapter.getData().size()>0&&position<adapter.getData().size())
            {
                CourseBean.DataBean.CourseListBean courseListBean = (CourseBean.DataBean.CourseListBean)adapter.getData().get(position);
                if (courseListBean != null)
                {
                    Intent intent = new Intent(getActivity(),CourseDetailsActivity.class);
                    intent.putExtra("chapterId",Integer.parseInt(courseListBean.getId()));
                    intent.putExtra("courseId",Integer.parseInt(courseListBean.getCourseId()));
                    getActivity().startActivity(intent);
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
      mPresenter.listByCateGoryPresenter(pageNumber,Integer.parseInt(secTagId));
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

package com.primecloud.huafenghuang.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.search.adapter.SearchAdapter;
import com.primecloud.huafenghuang.ui.search.bean.AllDataBean;
import com.primecloud.huafenghuang.ui.search.bean.SearchContract;
import com.primecloud.huafenghuang.ui.search.bean.SearchModel;
import com.primecloud.huafenghuang.ui.search.bean.SearchPresenter;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 搜索
 * */
public class SearchActivity extends BasePresenterActivity<SearchPresenter, SearchModel> implements SearchContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_cancel)
    TextView searchCancel;
    @BindView(R.id.search_recyclerView)
    RecyclerView searchRecyclerView;
    @BindView(R.id.search_iv)
    ImageView searchIv;


    private SearchAdapter adapter;

    private List<AllDataBean.DataBean.CoursesBean> list;

    private int currentPage = 1;
    private int totalPage;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();

        Utils.setEmojiFilter(searchEdit);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(linearLayoutManager);

        initAdapter();

    }


    @Override
    protected void initEvent() {


        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //判断是否是“搜索”键
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }

                    if (StringUtils.notBlank(searchEdit.getText().toString())) {
                        currentPage = 1;
                        totalPage = 1;
                        refresh();
                    }


                    return true;
                }

                return false;
            }
        });




    }


    /*
     *
     * 初始化
     * */
    public void initAdapter() {
        if (adapter == null) {
            adapter = new SearchAdapter(R.layout.course_list_item, list);
            searchRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.disableLoadMoreIfNotFullPage(searchRecyclerView);
            adapter.setOnLoadMoreListener(this, searchRecyclerView);
        } else {
            adapter.notifyDataSetChanged();

        }


    }


    @OnClick({R.id.search_iv, R.id.search_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_cancel:
                finish();
                break;
            case R.id.search_iv:
                if (StringUtils.notBlank(searchEdit.getText().toString())) {
                    currentPage = 1;
                    totalPage = 1;
                    refresh();
                }
                break;
        }
    }


    @Override
    public void searchView(AllDataBean dataBean) {
        AllDataBean.DataBean data = dataBean.getData();

        if (data != null) {
            if (data.getCourses() == null || data.getCourses().size() == 0) {
                View view = LayoutInflater.from(this).inflate(R.layout.view_error_layout, null, false);
                TextView tv_msg = view.findViewById(R.id.tv_error_layout);
                tv_msg.setText("暂无相关课程");
                view.findViewById(R.id.animProgress).setVisibility(View.GONE);
                adapter.setEmptyView(view);
            }

            currentPage = data.getCurPage();
            totalPage = data.getTotalPage();


            List<AllDataBean.DataBean.CoursesBean> tempList = data.getCourses();
            if (tempList != null) {
                if (adapter.isLoading()) {
                    adapter.addData(tempList);
                } else {
                    adapter.setNewData(tempList);
                }

            }else{
                adapter.setNewData(new ArrayList<AllDataBean.DataBean.CoursesBean>());
            }


            stopLoadMore();


            initAdapter();
        }


    }

    private void stopLoadMore() {
        if (currentPage >= totalPage) {
            adapter.loadMoreEnd(false);
        } else {
            adapter.loadMoreComplete();
        }


    }

    public void refresh() {
        mPresenter.searchPresenter(searchEdit.getText().toString(), currentPage);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();


    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        Intent intent = new Intent(this, CourseDetailsActivity.class);
        List<AllDataBean.DataBean.CoursesBean> data = adapter.getData();
        intent.putExtra("courseId", data.get(position).getCourseId());
        intent.putExtra("chapterId", data.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onLoadMoreRequested() {

        if (currentPage < totalPage) {
            ++currentPage;
            refresh();
        } else {
            stopLoadMore();
        }
    }


}

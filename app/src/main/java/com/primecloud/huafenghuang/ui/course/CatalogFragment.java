package com.primecloud.huafenghuang.ui.course;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.adapter.CatalogAdapter;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailsEvent;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CatalogFragment extends BasePresenterFragment<CoursePresenter, CourseModel> implements CourseContract.View, SwipeRefreshLayout.OnRefreshListener, CatalogAdapter.OnItemClickListener, CatalogAdapter.RequestLoadMoreListener, View.OnClickListener {


    @BindView(R.id.catalog_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.catalog_buy)
    TextView buy;
    @BindView(R.id.catalog_vip)
    Button vip;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private ArrayList<CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean> list = null;
    private CatalogAdapter catalogAdapter;
    private int currentPage;//当前页数
    private int totalPage;//总页数
    private int chapterId;
    private CourseDetailBean.DataBean dataBean;
    private int courseId;
    private boolean islock = true;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, null);
    }

    @Override
    public void initData() {

        list = new ArrayList<>();

        chapterId = getArguments().getInt("chapterId", -1);
        courseId = getArguments().getInt("courseId", -1);

        iniData();
//        swipeRefresh.setRefreshing(true);
//        onRefresh();

    }


    @Override
    public void initListener() {
        swipeRefresh.setOnRefreshListener(this);
    }


    public void iniData() {
        if (catalogAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            catalogAdapter = new CatalogAdapter(R.layout.catalog_recycler_item, list, chapterId + "");
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
    public void courseDetailView(CourseDetailBean courseDetailBean) {
        if (courseDetailBean != null && courseDetailBean.getDataBean() != null) {
            dataBean = courseDetailBean.getDataBean();

            if (dataBean != null) {
                if (dataBean.getCurrCourseChapter() != null && StringUtils.notBlank(dataBean.getCurrCourseChapter().getFree()) && dataBean.getCurrCourseChapter().getFree().equals("1")) {
                    buy.setVisibility(View.GONE);
                    vip.setVisibility(View.GONE);
                } else {
                    if (dataBean.getCurrUser() != null) {


                        if (StringUtils.notBlank(dataBean.getCurrUser().getIsVip()) && dataBean.getCurrUser().getIsVip().equals("1")) {
                            islock = false;
                            buy.setVisibility(View.GONE);
                            vip.setVisibility(View.GONE);
                        } else {
                            if (StringUtils.notBlank(dataBean.getCurrUser().getIsBuy()) && dataBean.getCurrUser().getIsBuy().equals("1")) {
                                islock = false;
                                buy.setVisibility(View.GONE);
                            } else {
                                islock = true;
                                vip.setVisibility(View.VISIBLE);

                                if (dataBean.getPrice() != 0 || dataBean.getSalePrice() != 0) {
                                    buy.setVisibility(View.VISIBLE);
                                    if (dataBean.getSalePrice() != -1 && dataBean.getSalePrice() != 0) {
                                        buy.setText("¥" + dataBean.getSalePrice() / 100 + getResources().getString(R.string.buy) + "\t" + "¥" + dataBean.getPrice() / 100);

                                    } else {
                                        buy.setText("¥" + dataBean.getPrice() / 100 + getResources().getString(R.string.buy));
                                    }

                                    setSpan(buy);
                                } else {
                                    buy.setVisibility(View.GONE);
                                }

                            }
                        }

                    } else {
                        vip.setVisibility(View.VISIBLE);

                        if (dataBean.getPrice() != 0 || dataBean.getSalePrice() != 0) {
                            buy.setVisibility(View.VISIBLE);
                            if (dataBean.getSalePrice() != -1 && dataBean.getSalePrice() != 0) {
                                buy.setText("¥" + dataBean.getSalePrice() / 100 + getResources().getString(R.string.buy) + "\t" + "¥" + dataBean.getPrice() / 100);

                            } else {
                                buy.setText("¥" + dataBean.getPrice() / 100 + getResources().getString(R.string.buy));
                            }

                            setSpan(buy);
                        }
                    }


                }


            }


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
        }

        iniData();
    }


    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        iniData();
    }

    @Override
    public void listCourseByTypeView(CourseBean dataBean) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        if (adapter.getData() != null && position < adapter.getData().size()) {
            CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean data = (CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean) adapter.getData().get(position);

            if (data != null) {
                if (chapterId == Integer.parseInt(data.getId())) {
                    return;
                }
                if (dataBean != null) {
                    if (StringUtils.notBlank(data.getFree()) && data.getFree().equals("1")) {
                        courseId = Integer.parseInt(data.getCourseId());
                        chapterId = Integer.parseInt(data.getId());
                        catalogAdapter = null;
                        CourseDetailsEvent courseDetailsEvent = new CourseDetailsEvent();
                        courseDetailsEvent.setType(3);
                        courseDetailsEvent.setCourseId(Integer.parseInt(data.getCourseId()));
                        courseDetailsEvent.setChapterId(Integer.parseInt(data.getId()));
                        EventBus.getDefault().post(courseDetailsEvent);
                        swipeRefresh.setRefreshing(true);
                        onRefresh();
                    } else if (dataBean.getCurrUser() != null) {
                        if (StringUtils.notBlank(dataBean.getCurrUser().getIsVip()) && dataBean.getCurrUser().getIsVip().equals("1") || StringUtils.notBlank(dataBean.getCurrUser().getIsBuy()) && dataBean.getCurrUser().getIsBuy().equals("1")) {
                            courseId = Integer.parseInt(data.getCourseId());
                            chapterId = Integer.parseInt(data.getId());
                            catalogAdapter = null;
                            CourseDetailsEvent courseDetailsEvent = new CourseDetailsEvent();
                            courseDetailsEvent.setType(3);
                            courseDetailsEvent.setCourseId(Integer.parseInt(data.getCourseId()));
                            courseDetailsEvent.setChapterId(Integer.parseInt(data.getId()));
                            EventBus.getDefault().post(courseDetailsEvent);
                            swipeRefresh.setRefreshing(true);
                            onRefresh();
                        }
                        else
                        {
                            if(dataBean.getPrice() != 0 || dataBean.getSalePrice() != 0)
                            {
                                buy(data);
                            }
                            else
                            {
                               buyVip();
                            }

                        }
                    } else {

                        if(dataBean.getPrice() != 0 || dataBean.getSalePrice() != 0)
                        {
                            buy(data);
                        }
                        else
                        {
                            buyVip();
                        }
                    }
                }

            }


        }
    }

    /*
    * 购买课程*/
    public void buy(CourseDetailBean.DataBean.CatalogBean.CatalogRecordsBean data)
    {
        if (Utils.isLogin(getActivity())) {
            Intent intent = new Intent(getActivity(), BuyCourseActivity.class);
            intent.putExtra("courseId", data.getCourseId());
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

    }


    /**
     * 购买Vip
     * */
    public void buyVip()
    {
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
        if (chapterId != -1 && courseId != -1) {
            if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                mPresenter.courseDetailPresenter(courseId, pageNumber + "", "1", chapterId, MyApplication.getInstance().getUserInfo().getId());
            } else {
                mPresenter.courseDetailPresenter(courseId, pageNumber + "", "1", chapterId, null);
            }

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
    @OnClick({R.id.catalog_buy, R.id.catalog_vip})
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
        }
    }


    public void setSpan(TextView textView) {
        String content = textView.getText().toString();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);

        int redIndex = content.indexOf("购");
        if (redIndex != -1) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#FC363C")), 0, redIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        int index = content.indexOf("¥", 2);
        if (index != -1) {
            spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), index, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStringBuilder.setSpan(new StrikethroughSpan(), index, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(30), index, content.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        textView.setText(spannableStringBuilder);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && swipeRefresh != null) {
            swipeRefresh.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()&&swipeRefresh != null) {
            swipeRefresh.setRefreshing(true);
            onRefresh();
        }
    }




}

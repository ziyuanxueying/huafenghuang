package com.primecloud.huafenghuang.ui.home.findfragment;

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

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.course.CatalogActivity;
import com.primecloud.huafenghuang.ui.course.CourseDetailsActivity;
import com.primecloud.huafenghuang.ui.course.CourseTypeActivity;
import com.primecloud.huafenghuang.ui.course.CourseTypeList2Activity;
import com.primecloud.huafenghuang.ui.course.CourseTypeListActivity;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.findfragment.adapter.FindAdapter;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindBean;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindItemBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message.MessageActivity;
import com.primecloud.huafenghuang.ui.search.SearchActivity;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.GridSpacingItemDecoration;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zy on 2018/12/20.
 */

public class FindFragment extends BasePresenterFragment<FindPresenter, FindModel> implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, FindContract.View, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.home_recycler)
    RecyclerView homeRecycler;
    @BindView(R.id.home_refresh)
    SwipeRefreshLayout homeRefresh;
    private FindAdapter adapter;
    private List<FindBean> list;
    private LinearLayoutManager layoutManager = null;

    private boolean isPrepared;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null, false);
        return view;
    }

    @Override
    public void initData() {

        Utils.setSwipeRefreshLayout(homeRefresh);
//        layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        layoutManager = new LinearLayoutManager(getActivity());
        ;
        homeRecycler.setLayoutManager(layoutManager);
        int spanCount = 1;//跟布局里面的spanCount属性是一致的
        int spacing = 28;//每一个矩形的间距
        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距
        //设置每个item间距
        homeRecycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        list = new ArrayList<>();
        setFindAdapter();

        homeRefresh.setRefreshing(true);
        onRefresh();

        isPrepared = true;
    }

    private void setFindAdapter() {
        if (adapter == null) {
            adapter = new FindAdapter(list);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemChildClickListener(this);
            homeRecycler.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        stopRefresh();
    }

    private void stopRefresh() {
        if (homeRefresh.isRefreshing()) {
            homeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void initListener() {
        homeRefresh.setOnRefreshListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        int itemViewType = adapter.getItemViewType(position);
        List<FindBean> data = adapter.getData();
        Intent intent = null;
        if (data.get(position).getChapterId() == 0) {
            intent = new Intent(getContext(), CourseTypeList2Activity.class);
            intent.putExtra("secTagId", data.get(position).getCourseId() + "");
            intent.putExtra("name", data.get(position).getChapter_title());
            getContext().startActivity(intent);
        } else if (data.get(position).getChapter_title().equals("女性健康") ||
                data.get(position).getChapter_title().equals("新心学")||
                data.get(position).getChapter_title().equals("思维升级")) {
            intent = new Intent(getContext(), CatalogActivity.class);
            intent.putExtra("chapterId", data.get(position).getChapterId());
            intent.putExtra("courseId", data.get(position).getCourseId());
            getContext().startActivity(intent);
        } else if (itemViewType == FindBean.ITEM_COURSE) {// 课程
            Log.i("FJ", data.get(position).getChapter_title());
            intent = new Intent(getContext(), CourseDetailsActivity.class);
            intent.putExtra("chapterId", data.get(position).getChapterId());
            intent.putExtra("courseId", data.get(position).getCourseId());
            getContext().startActivity(intent);
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.item_head_search:// 搜索
            {
                intent = new Intent(getContext(), SearchActivity.class);
            }
            break;
            case R.id.item_type_more:// 查看更多
            {
                List<FindBean> data = adapter.getData();
                String typeName = data.get(position).getTypeName();
                if (data.get(position).getId() == 0) {
                    intent = new Intent(getContext(), CourseTypeActivity.class);
                    if ("热门推荐".equals(typeName)) {
                        intent.putExtra("queryType", 2);
                    } else if ("免费课程".equals(typeName)) {
                        intent.putExtra("queryType", 1);
                    }

                } else {
//                    intent = new Intent(getContext(), CourseTypeListActivity.class);
//                    intent.putExtra("parentId", data.get(position).getId());
//                    intent.putExtra("name", data.get(position).getTypeName());
                    if (data.get(position).getId() == 5 || data.get(position).getId() == 37) {
                        intent = new Intent(getContext(), CourseTypeList2Activity.class);
                    } else {
                        intent = new Intent(getContext(), CourseTypeListActivity.class);
                    }
                    intent.putExtra("parentId", data.get(position).getId());
                    intent.putExtra("secTagId", data.get(position).getId() + "");
                    Log.i("FJ", "dataBean---" + data.get(position).getId());
                    intent.putExtra("name", data.get(position).getTypeName());
//                    startActivity(intent);
                }
            }
            break;
            case R.id.item_head_yinpin:// 音频
            {
                if (MyApplication.getInstance().getChapterId() != -1) {
                    intent = new Intent(getContext(), CourseDetailsActivity.class);
                    intent.putExtra("chapterId", MyApplication.getInstance().getChapterId());
                    intent.putExtra("isMusic", true);
                } else {
                    ToastUtils.showToast(getActivity(), "暂时没有播放音频");
                }

            }
            break;
            case R.id.item_head_message:// 通知
            {
                if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
                    intent = new Intent(getContext(), MessageActivity.class);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                }
            }
            break;
        }

        if (intent != null) {
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.listIndexCourses();
    }

    @Override
    public void showCourseData(FindItemBean findItemBean) {
        homeRefresh.setRefreshing(false);
        list.clear();
        FindBean findBean = new FindBean();
        // 添加头部数据
        findBean.setItemType(FindBean.ITEM_HEAD);
        list.add(findBean);

        findBean = new FindBean();
        findBean.setItemType(FindBean.ITEM_TYPE);
        findBean.setTypeName("心颖说");
        findBean.setId(5);
        list.add(findBean);
        list.addAll(findItemBean.getData().getNewSay().subList(0, 3));

        findBean = new FindBean();
        findBean.setItemType(FindBean.ITEM_TYPE);
        findBean.setTypeName("免费课程");
        list.add(findBean);
        list.addAll(findItemBean.getData().getFree());

        findBean = new FindBean();
        findBean.setItemType(FindBean.ITEM_TYPE);
        findBean.setTypeName("本周新课");
        findBean.setId(37);
        list.add(findBean);
        list.addAll(findItemBean.getData().getWeekNew());

        findBean = new FindBean();
        findBean.setItemType(FindBean.ITEM_TYPE);
        findBean.setTypeName("热门推荐");
        list.add(findBean);
        list.addAll(findItemBean.getData().getHot().subList(0, 3));

//        findBean = new FindBean();
//        findBean.setItemType(FindBean.ITEM_TYPE);
//        findBean.setTypeName("家庭");
//        findBean.setId(3);
//        list.add(findBean);
//        list.addAll(findItemBean.getData().getFamily().subList(0, 3));
//
//        findBean = new FindBean();
//        findBean.setItemType(FindBean.ITEM_TYPE);
//        findBean.setTypeName("事业");
//        findBean.setId(2);
//        list.add(findBean);
        // list.addAll(findItemBean.getData().getWork().subList(0, 3));


        adapter.setBanners(findItemBean.getData().getBanner());

        setFindAdapter();
        mPresenter.listTypePresenter(1, 1);
    }

    @Override
    public void showListData(CourseBean courseBean) {
        courseBean.getData().getSecTags();
        int size = courseBean.getData().getSecTags().size();
        List<FindBean> typeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i > 2) {
                break;
            }
            CourseBean.DataBean.SecTagsBean oldItem = courseBean.getData().getSecTags().get(i);
            FindBean item = new FindBean();

            item.setChapter_title(oldItem.getName());
            item.setCoursePic(oldItem.getCoursePic());
            item.setCourseView(Integer.parseInt(oldItem.getCourseView()));
            item.setSummary(oldItem.getSummary());
            item.setFree(Integer.parseInt(oldItem.getFreeFlag()));
            item.setCourseId(Integer.parseInt(oldItem.getId()));
            typeList.add(item);
        }
        Log.i("FJ", typeList.toString());
        FindBean findBean;
        switch (courseBean.getData().getSecTags().get(0).getParentId()) {
            case "1":
                findBean = new FindBean();
                findBean.setItemType(FindBean.ITEM_TYPE);
                findBean.setTypeName("个人成长");
                findBean.setId(1);
                list.add(findBean);
                list.addAll(typeList);
                mPresenter.listTypePresenter(1, 3);
                break;
            case "3":
                findBean = new FindBean();
                findBean.setItemType(FindBean.ITEM_TYPE);
                findBean.setTypeName("家庭");
                findBean.setId(3);
                list.add(findBean);
                list.addAll(typeList);
                mPresenter.listTypePresenter(1, 2);
                break;
            case "2":
                findBean = new FindBean();
                findBean.setItemType(FindBean.ITEM_TYPE);
                findBean.setTypeName("事业");
                findBean.setId(2);
                list.add(findBean);
                list.addAll(typeList);
                break;
        }
        setFindAdapter();
    }

    @Override
    public void onRequestError(String msg) {
        super.onRequestError(msg);
        stopRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isPrepared == true && isVisibleToUser == true) {
            getMessageFroUser();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getMessageFroUser();
    }

    // 获取是否有未读消息
    private boolean isMessage;

    private void getMessageFroUser() {
        if (MyApplication.getInstance().getUserInfo() != null && StringUtils.notBlank(MyApplication.getInstance().getUserInfo().getId())) {
            FengHuangApi.getUserInformation(MyApplication.getInstance().getUserInfo().getId(), new HttpCallBack<BizResult>() {
                @Override
                public void onSuccess(String data, BizResult body) {
                    UserInfo information = JSON.parseObject(body.getData(), UserInfo.class);
                    if (information == null)
                        return;
                    adapter.setMessageNotify(information.isUnreadedMessageExist());
                }

                @Override
                public void onFailure(String data, String errorMsg) {

                }
            });
        }
    }
}

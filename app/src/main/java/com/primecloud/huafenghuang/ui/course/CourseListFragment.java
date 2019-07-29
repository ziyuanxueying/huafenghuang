package com.primecloud.huafenghuang.ui.course;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseModel;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCoursePresenter;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class CourseListFragment extends BasePresenterFragment<MainCoursePresenter,MainCourseModel> implements MainCourseContract.View {


    @BindView(R.id.course_list_tab_layout)
    TabLayout mTab;

    @BindView(R.id.course_list_viewpager)
    ViewPager mViewPager;



    private CourseListFragmentPagerAdapter pagerAdapter;
    private String parentId;
    private CourseBean courseBean=null;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_tab, null);
    }

    @Override
    public void initListener() {

    }


    @Override
    public void initData() {
        parentId = getArguments().getString("parentId");



        if (StringUtils.notBlank(parentId))
        {
            mPresenter.listByCateGoryPresenter(1,Integer.parseInt(parentId));
        }


        mTab.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mTab.setupWithViewPager(mViewPager);




    }


    @Override
    public void listByCateGoryView(CourseBean courseBean) {
        if (courseBean!=null&&courseBean.getData()!=null)
        {
            this.courseBean = courseBean;
            CourseBean.DataBean dataBean = courseBean.getData();
            if (dataBean.getSecTags()!=null&&dataBean.getSecTags().size()>0)
            {
                mTab.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                pagerAdapter = new CourseListFragmentPagerAdapter(getChildFragmentManager(), dataBean.getSecTags());
                mViewPager.setAdapter(pagerAdapter);
                Utils.reflex(mTab, (int) (getResources().getDisplayMetrics().widthPixels * 0.09), (int) (getResources().getDisplayMetrics().widthPixels * 0.09));

            }
        }
    }

    @Override
    public void mainCourseView(MainCourseBean mainCourseBean) {

    }

    public class CourseListFragmentPagerAdapter extends FragmentStatePagerAdapter {
        private List<CourseBean.DataBean.SecTagsBean> pagerList = null;
        private HashMap<Integer, Fragment> hashMap = null;

        public CourseListFragmentPagerAdapter(FragmentManager fm, List<CourseBean.DataBean.SecTagsBean> list) {
            super(fm);
            pagerList = list;
            hashMap = new HashMap<>();
        }

        @Override
        public int getCount() {
            return pagerList.size()+1;
        }


        @Override
        public Fragment getItem(int position) {
            if (hashMap != null) {
                Fragment fragment = hashMap.get(position);
                if (fragment == null) {
                    CourseFragment courseFragment = new CourseFragment();
                    Bundle bundle = new Bundle();
                    if (position == 0)
                    {
                        bundle.putString("secTagId",parentId);
                    }
                    else
                    {
                        bundle.putString("secTagId",pagerList.get(position-1).getId());
                    }
                    courseFragment.setArguments(bundle);
                    hashMap.put(position, courseFragment);

                }
                return hashMap.get(position);
            }
            return new Fragment();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0)
            {
                return getResources().getString(R.string.all);
            }
            return pagerList.get(position-1).getName();
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        pagerAdapter = null;

    }
}




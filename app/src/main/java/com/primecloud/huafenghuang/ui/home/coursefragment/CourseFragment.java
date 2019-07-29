package com.primecloud.huafenghuang.ui.home.coursefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.course.CourseListFragment;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 *课程
 *
 */

public class CourseFragment extends BasePresenterFragment<MainCoursePresenter,MainCourseModel> implements MainCourseContract.View {


    @BindView(R.id.course_tab)
    TabLayout mTab;
    @BindView(R.id.course_viewPager)
    ViewPager mViewPager;

    private CoursePagerAdapter coursePagerAdapter;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, null, false);
        return view;
    }

    @Override
    public void initData() {

        mPresenter.mainCoursePresenter();

        mTab.setupWithViewPager(mViewPager);



        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTab.getLayoutParams();
        layoutParams.leftMargin = (int)(getResources().getDisplayMetrics().widthPixels*0.1);
        layoutParams.rightMargin = (int)(getResources().getDisplayMetrics().widthPixels*0.1);

        Utils.reflex(mTab,(int)(getResources().getDisplayMetrics().widthPixels*0.05),(int)(getResources().getDisplayMetrics().widthPixels*0.05));


    }

    @Override
    public void initListener() {

    }


    @Override
    public void mainCourseView(MainCourseBean mainCourseBean) {
        if (mainCourseBean.getData()!=null&&mainCourseBean.getData().size()>0)
        {
            coursePagerAdapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager(),mainCourseBean.getData());
            mViewPager.setAdapter(coursePagerAdapter);
        }
    }


    @Override
    public void listByCateGoryView(CourseBean courseBean) {

    }

    public class CoursePagerAdapter extends FragmentStatePagerAdapter
    {
        private List<MainCourseBean.CateGoryBean> pagerList = null;
        private HashMap<Integer,Fragment> hashMap = null;

        public CoursePagerAdapter(FragmentManager fm,List<MainCourseBean.CateGoryBean> list) {
            super(fm);
            pagerList = list;
            hashMap = new HashMap<>();
        }

        @Override
        public Fragment getItem(int position) {
            if (hashMap!=null)
            {


                Fragment fragment = hashMap.get(position);
                if (fragment == null)
                {
                    CourseListFragment courseFragment = new CourseListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("parentId",pagerList.get(position).getId());
                    courseFragment.setArguments(bundle);


                    hashMap.put(position,courseFragment);

                }
                return hashMap.get(position);
            }
            return new Fragment();
        }



        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return pagerList.get(position).getName();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (coursePagerAdapter == null)
            {
                mPresenter.mainCoursePresenter();
            }
        }
    }
}

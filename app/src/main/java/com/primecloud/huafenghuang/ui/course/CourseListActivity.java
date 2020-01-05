package com.primecloud.huafenghuang.ui.course;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseModel;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCoursePresenter;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.base.BasePresenterActivity;
import com.primecloud.library.baselibrary.base.BasePresenterFragment;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 带二级tab的列表页
 */
public class CourseListActivity extends BasePresenterActivity<MainCoursePresenter,MainCourseModel> implements MainCourseContract.View,View.OnClickListener {


    @BindView(R.id.course_list_tab_layout)
    TabLayout mTab;

    @BindView(R.id.course_list_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.head_left_image)
    ImageView image_return;
    @BindView(R.id.head_center_text)
    TextView title;

    private CourseListPagerAdapter pagerAdapter;
    private String parentId;
    private String name;


    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_course_list);
    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected void initData() {
        parentId =getIntent().getStringExtra("parentId");
        name = getIntent().getStringExtra("name");

        mTab.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mTab.setupWithViewPager(mViewPager);


        if (StringUtils.notBlank(name))
        {
            title.setText(name);
        }



        mPresenter.listByCateGoryPresenter(1,Integer.parseInt(parentId));
    }




    @Override
    public void listByCateGoryView(CourseBean courseBean) {
        if (courseBean!=null&&courseBean.getData()!=null)
        {
            CourseBean.DataBean dataBean = courseBean.getData();
            if (dataBean.getSecTags()!=null&&dataBean.getSecTags().size()>0)
            {
                mTab.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                pagerAdapter = new CourseListPagerAdapter(getSupportFragmentManager(), dataBean.getSecTags());
                mViewPager.setAdapter(pagerAdapter);
                Utils.reflex(mTab, (int) (getResources().getDisplayMetrics().widthPixels * 0.09), (int) (getResources().getDisplayMetrics().widthPixels * 0.09));
            }
        }
    }

    @Override
    public void mainCourseView(MainCourseBean mainCourseBean) {

    }


    public class CourseListPagerAdapter extends FragmentStatePagerAdapter
    {


        private List<CourseBean.DataBean.SecTagsBean> pagerList = null;
        private HashMap<Integer,Fragment> hashMap = null;

        public CourseListPagerAdapter(FragmentManager fm,List<CourseBean.DataBean.SecTagsBean> list) {
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
            if (hashMap!=null)
            {
                Fragment fragment = hashMap.get(position);
                if (fragment == null)
                {
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
    @OnClick(R.id.head_left_image)
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.head_left_image:
                finish();
                break;
        }
    }
}

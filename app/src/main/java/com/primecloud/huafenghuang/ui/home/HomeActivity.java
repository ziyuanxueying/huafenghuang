package com.primecloud.huafenghuang.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.adapter.ViewPagerAdapter;
import com.primecloud.huafenghuang.ui.home.coursefragment.CourseFragment;
import com.primecloud.huafenghuang.ui.home.findfragment.FindFragment;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.UserCenterFragment;
import com.primecloud.huafenghuang.ui.update.UpdateManager;
import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.huafenghuang.widget.NoScrollViewPager;
import com.primecloud.library.baselibrary.base.AppManager;
import com.primecloud.library.baselibrary.base.CommonBaseActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by zy on 2018/12/20.
 */

public class HomeActivity extends CommonBaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.act_home_viewpager)
    NoScrollViewPager actHomeViewpager;
    @BindView(R.id.act_home_navigation)
    BottomNavigationViewEx actHomeNavigation;

    private ViewPagerAdapter viewPagerAdapter;

    private List<Fragment> fragments;


    private FindFragment findFragment;
    private CourseFragment signUpFragment;
    private UserCenterFragment userCenterFragment;

    private MenuItem menuItem;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.act_home);
    }

    @Override
    protected void initData() {

        applypermission();

        fragments = new ArrayList<>();

        findFragment = new FindFragment();
        signUpFragment = new CourseFragment();
        userCenterFragment = new UserCenterFragment();

        fragments.add(findFragment);
        fragments.add(signUpFragment);
        fragments.add(userCenterFragment);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        actHomeViewpager.setAdapter(viewPagerAdapter);
        actHomeViewpager.setNoScroll(true);

        viewPagerAdapter.setList(fragments);

        actHomeNavigation.setupWithViewPager(actHomeViewpager);
        actHomeNavigation.setItemIconTintList(null);
        actHomeNavigation.enableAnimation(false);
        actHomeNavigation.enableShiftingMode(false);
        actHomeNavigation.enableItemShiftingMode(false);

        actHomeViewpager.setOffscreenPageLimit(3);

        UpdateManager.getUpdateManager().checkAppVersionInfo(this,0);
        EventBus.getDefault().register(this);

    }

    // 权限申请
    private void applypermission() {
        String[] pressions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE
        };

        RxPermissions.getInstance(this).request(pressions)
                //这里填写所需要的权限
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
//         true表示获取权限成功（注意这里在android6.0以下默认为true）
                            Log.i("permissions", Manifest.permission.READ_CALENDAR + "：获取成功");
                        } else {
                            Log.i("permissions", Manifest.permission.READ_CALENDAR + "：获取失败");
                        }
                    }
                });


    }


    @Override
    protected void initEvent() {

        actHomeNavigation.setOnNavigationItemSelectedListener(this);
        actHomeViewpager.addOnPageChangeListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        menuItem = item;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                actHomeViewpager.setCurrentItem(0);
                return true;
            case R.id.navigation_kecheng:
                actHomeViewpager.setCurrentItem(1);
                return true;
            case R.id.navigation_zuoye:
                actHomeViewpager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            actHomeNavigation.getMenu().getItem(0).setChecked(false);
        }
        menuItem = actHomeNavigation.getMenu().getItem(position);
        menuItem.setChecked(true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showToast(HomeActivity.this, getString(R.string.app_exit_hint));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                AppManager.getInstance().appExit(HomeActivity.this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePage(Integer position) {
        if(position == 0){
            menuItem = actHomeNavigation.getMenu().getItem(2);
            menuItem.setChecked(true);
            actHomeViewpager.setCurrentItem(2, false);
            userCenterFragment.changeInfo("1");
        }else if(position == 2){
            menuItem = actHomeNavigation.getMenu().getItem(2);
            menuItem.setChecked(true);
            actHomeViewpager.setCurrentItem(2, false);
            userCenterFragment.changeInfo("2");
        }else if(position == 3){
            userCenterFragment.changeInfo("2");
        }
    }
}

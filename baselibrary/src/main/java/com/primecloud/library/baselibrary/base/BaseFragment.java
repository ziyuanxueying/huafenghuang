package com.primecloud.library.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.primecloud.library.baselibrary.R;
import com.primecloud.library.baselibrary.widget.toolbar.CustomToolbar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zy on 2018/5/2.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    public CustomToolbar mCustomToolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = setContentView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this,mRootView);//绑定framgent
        mCustomToolbar = mRootView.findViewById(R.id.toolbar);

        configToolbar();
        initData();
        initListener();
        return mRootView;
    }

    private void configToolbar() {
    }

    public void setNotifyData(boolean isDealWith){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       if(null != unbinder){
           unbinder.unbind();
       }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public abstract View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void initData();

    public abstract void initListener();
}

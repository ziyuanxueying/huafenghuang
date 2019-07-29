package com.primecloud.library.baselibrary.base;

import android.app.Application;

import com.primecloud.library.baselibrary.net.NetProvider;

/**
 * Created by zy on 2018/5/17.
 */

public abstract class BaseApplication extends Application {

    public NetProvider netProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        registerProvider();
    }

    /**
     * 给okhttp注册NetProvider,默认有个BaseProvider
     */
    protected abstract void registerProvider();
}

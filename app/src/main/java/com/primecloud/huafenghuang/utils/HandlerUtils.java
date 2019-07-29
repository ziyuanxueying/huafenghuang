package com.primecloud.huafenghuang.utils;

import android.os.Handler;

import com.primecloud.huafenghuang.application.MyApplication;

/**
 * Created by zy on 2018/12/21.
 */

public class HandlerUtils {
    public static final Handler mHander = new Handler();

    public static final Handler mainHandler = new Handler(MyApplication.getInstance().getMainLooper());
}

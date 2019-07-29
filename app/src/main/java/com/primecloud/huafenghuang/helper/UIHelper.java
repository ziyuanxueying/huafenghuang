package com.primecloud.huafenghuang.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackActivity;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.set.SimpleBackPage;
import com.primecloud.huafenghuang.utils.PathUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;


public class UIHelper {

    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    public static void showSimpleBack(Context context, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    /**
     * 清除缓存
     *
     * @param activity
     */
    public static void clearAppCache(final Activity activity) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtils.showToast(activity, "缓存清除成功");
                } else {
                    ToastUtils.showToast(activity, "缓存清除失败");
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    MyApplication.getInstance().clearAppCache();
                    PathUtils.createFilePath();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }
}

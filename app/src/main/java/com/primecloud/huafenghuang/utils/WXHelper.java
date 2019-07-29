package com.primecloud.huafenghuang.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class WXHelper {
    private static final String WECHAT_APP_PACKAGE = "com.tencent.mm";
    private static final String WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI";
    private static final String WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut";

    public static void  openScanner(Context context) {
        // 检查微信是否安装
        if(ShareManager.isAppAvilible(context, WECHAT_APP_PACKAGE)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            ComponentName component = new ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS);
            intent.setComponent(component);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            ToastUtils.showToast(context, "未安裝微信");
        }
    }

}

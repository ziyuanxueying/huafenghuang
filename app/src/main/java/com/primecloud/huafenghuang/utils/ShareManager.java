package com.primecloud.huafenghuang.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.primecloud.library.baselibrary.log.XLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShareManager {

    /**
     * 微信7.0版本号，兼容处理微信7.0版本分享到朋友圈不支持多图片的问题
     */
    private static final int VERSION_CODE_FOR_WEI_XIN_VER7 = 1380;
    /**
     * 微信包名
     */
    public static final String PACKAGE_NAME_WEI_XIN = "com.tencent.mm";

    private Context mContext;
    ArrayList<Uri> imageUris = new ArrayList<Uri>();

    public ShareManager(Context mContext) {
        this.mContext = mContext;
    }

    public void setShareImage( int flag,  List<File> files,  String Kdescription,  String mType, String s) {
        XLog.i("mType:"+mType);

        if (mType.equals("qq") && !isAppAvilible(mContext, "com.tencent.mobileqq")) {

            Toast.makeText(mContext, "您还没有安装QQ", Toast.LENGTH_SHORT).show();
            return;
        } else if (mType.equals("wchat") && !isAppAvilible(mContext, "com.tencent.mm")) {

            Toast.makeText(mContext, "您还没有安装微信", Toast.LENGTH_SHORT).show();
            return;
        } else if (mType.equals("qq_zone") && !isAppAvilible(mContext, "com.qzone")) {
            Toast.makeText(mContext, "您还没有安装QQ空间", Toast.LENGTH_SHORT).show();
            return;
        } else if (mType.equals("weibo") && !isAppAvilible(mContext, "com.sina.weibo")) {
            Toast.makeText(mContext, "您还没有安装新浪微博", Toast.LENGTH_SHORT).show();
            return;
        }
        XLog.i("wo lai le :");

        for (File f : files) {
            Uri uri = null;
            try {
                ApplicationInfo applicationInfo = mContext.getApplicationInfo();
                int targetSDK = applicationInfo.targetSdkVersion;
                if (targetSDK >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                } else {
                    uri = Uri.fromFile(new File(f.getPath()));
                }
                imageUris.add(uri);
            } catch (Exception ex) {

            }

        }



        Intent intent = new Intent();
        ComponentName comp = null;
        if (mType.contains("qq")) {

            if (flag == 0) {
                comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
            } else {
                comp = new ComponentName("com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity");
            }
        } else if (mType.contains("weibo")) {
            if (flag == 2) {
                comp = new ComponentName("com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity");
            }
        } else {
            if (flag == 0) {
                comp = new ComponentName(PACKAGE_NAME_WEI_XIN, "com.tencent.mm.ui.LauncherUI");
            } else {
                comp = new ComponentName(PACKAGE_NAME_WEI_XIN, "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            }
        }

        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);


        intent.setType("image/*");
        intent.putExtra("Kdescription", "内容");

        mContext.startActivity(intent);

    }

    //判断是否安装了微信,QQ,QQ空间
    public static boolean isAppAvilible(Context context, String mType) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(mType)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取制定包名应用的版本的versionCode
     * @param context
     * @param
     * @return
     */
    public static int getVersionCode(Context context,String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
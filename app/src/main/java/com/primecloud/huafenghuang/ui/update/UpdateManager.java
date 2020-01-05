package com.primecloud.huafenghuang.ui.update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.primecloud.huafenghuang.R;
import com.primecloud.huafenghuang.api.BizResult;
import com.primecloud.huafenghuang.api.FengHuangApi;
import com.primecloud.huafenghuang.api.HttpCallBack;
import com.primecloud.huafenghuang.utils.DialogUtils;
import com.primecloud.huafenghuang.utils.NetUtils;
import com.primecloud.huafenghuang.utils.PathUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class UpdateManager {

    private boolean flag = false; //进度框消失标示 之后发送通知


    private static UpdateManager updateManager;
    private VersionUpdateModel updateModel;

    public UpdateManager() {
    }

    public static UpdateManager getUpdateManager() {
        if (updateManager == null) {
            updateManager = new UpdateManager();
        }
        return updateManager;
    }

    /**
     * 检查App版本信息
     */
    public void checkAppVersionInfo(final Activity context, final int loginSign) {
        this.mContext = context;
        FengHuangApi.getNewRelease(Utils.getVersionName(context), new HttpCallBack<BizResult>() {
            @Override
            public void onSuccess(String data, final BizResult body) {
                try {
                    if (null!=body) {
                        Log.i("FJ",body.getData().toString());
                        if (StringUtils.notBlank(body.getData()) && !body.equals("false")) {
                            updateModel = JSON.parseObject(body.getData(), VersionUpdateModel.class);
                            if (updateModel != null) {
                                if(updateModel.getIsUpdate()==1){//强制升级
                                    showNewVision(context.getString(R.string.update_button_new), mContext.getResources().getString(R.string.update_message) + updateModel.getRelease(), 1);
                                }else{
                                    showUpdateDialog(updateModel);
                                }
                            }
                        } else if (loginSign == 1) {
                            showNewVision(context.getString(R.string.choose_photo_know),context.getString(R.string.is_update_button_message), 0);
                        }
                    } else if (null==body) {
                        if (loginSign == 1) {
                            showNewVision(context.getString(R.string.choose_photo_know), context.getString(R.string.is_update_button_message), 0);
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String data, String errorMsg) {

            }

        });

    }

    /**
     * 最新版本的对话框
     */
    private void showNewVision(String title, String message,final int isUpload) {
        if (deteleDialog != null && deteleDialog.isShowing())
            deteleDialog.dismiss();
        if(isUpload==1){
            updateUrl = updateModel.getDownloadUrl();
        }

        deteleDialog = DialogUtils.showNewVersion(mContext, title, message, isUpload, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                if (isUpload == 1) {
                    showDownloadDialog(updateModel.getIsUpdate());
                    downloadApk();
                }
                deteleDialog.dismiss();
            }
        });
        deteleDialog.show();
    }

    private Dialog deteleDialog = null;
    private String updateUrl = null;
    public String tmpSize;
    public String apkSize;

    /**
     * 显示版本更新对话框
     *
     * @param updateModel
     */
    private void showUpdateDialog(final VersionUpdateModel updateModel) {
        if (deteleDialog != null && deteleDialog.isShowing())
            deteleDialog.dismiss();
        updateUrl = updateModel.getDownloadUrl();
        deteleDialog = DialogUtils.showVersionUpdate(mContext, mContext.getResources().getString(R.string.update_message) + updateModel.getRelease(), updateModel.getIsUpdate(), mContext.getResources().getString(R.string.update_button), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iknow_alert_dialog_button1:
                        // 取消
                        deteleDialog.dismiss();
                        break;
                    case R.id.iknow_alert_dialog_button2:
                        // 立即更新
                        if(NetUtils.isConnected(mContext)){
                            showDownloadDialog(updateModel.getIsUpdate());
                            downloadApk();
                        }

                        deteleDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
        deteleDialog.show();
    }

    private Context mContext;
    // 下载对话框
    // private Dialog downloadDialog;

    // 终止标记
    private boolean interceptFlag;
    // 下载线程
    private Thread downLoadThread;

    private Downloaded downloaded = null;

    /**
     * 显示下载更新对话框
     */
    private void showDownloadDialog(int isUpdate) {
        interceptFlag = false;
        if (downloaded != null && downloaded.isShowing())
            downloaded.dismiss();
        downloaded = new Downloaded(mContext, isUpdate);
        interceptFlag = false;
        downloaded.setButton(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtils.deleteDirFile(savePath);
                dialog.dismiss();
                interceptFlag = true;
            }
        });
        downloaded.setCanceledOnTouchOutside(false);
        downloaded.show();
    }

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    // 下载包保存路径
    private static String savePath = "";
    // apk保存完整路径
    private String apkFilePath = "";
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    private int apkFileSize;
    // 已下载文件大小
    private int tmpFileSize;
    // 进度值
    private int progress;

    private String getApkName(String name) {
        String result = "";
        String names[] = name.split("/");

        for (int i = 0; i < names.length; i++) {
            String string = names[i].toString();
            if (string.endsWith(".apk")) {
                result = string;
            }
        }
        return result;
    }

    String apkName;
    private Runnable mdownApkRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                if (updateUrl != null && StringUtils.notBlank(updateUrl)) {
                    apkName = getApkName(updateUrl);
                    String tmpApk = getApkName(updateUrl).replace("apk", "tmp");
                    // 判断是否挂载了SD卡
                    savePath = PathUtils.getDownloadPath();
                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    apkFilePath = savePath + apkName;
                    tmpFilePath = savePath + tmpApk;
                    // }

                    // 没有挂载SD卡，无法下载文件
                    if (apkFilePath == null || apkFilePath == "") {
                        mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                        return;
                    }

                    File ApkFile = new File(apkFilePath);

                    // 输出临时下载文件
                    File tmpFile = new File(tmpFilePath);
                    FileOutputStream fos = new FileOutputStream(tmpFile);
                    URL url = new URL(updateUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();

                    // 显示文件大小格式：2个小数点显示
                    DecimalFormat df = new DecimalFormat("0.00");
                    // 进度条下面显示的总文件大小
                    apkSize = df.format((float) length / 1024 / 1024) + "MB";
                    apkFileSize = length / 1024 / 1024;
                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 进度条下面显示的当前下载文件大小
                        tmpSize = df.format((float) count / 1024 / 1024) + "MB";
                        tmpFileSize = count / 1024 / 1024;

                        // 当前进度值
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        // downloaded.seekbar_progress.setMaxCount(100);
                        mHandler.sendEmptyMessage(DOWN_UPDATE);

                        if (numread <= 0) {
                            // 下载完成 - 将临时下载文件转成APK文件
                            if (tmpFile.renameTo(ApkFile)) {
                                // 通知安装
                                mHandler.sendEmptyMessage(DOWN_OVER);
                            }
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消就停止下载

                    fos.close();
                    is.close();
//					if (flag) notificationManager.cancel(1); //取消通知
                }
            }catch (Exception e) {
                if(null!=downloaded){
                    downloaded.dismiss();
                }
                e.printStackTrace();
            }
        }
    };

    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_FAILURE = 3;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    downloaded.tv_download_progress.setText("当前进度：" + tmpSize + "/" + apkSize);
                    break;

                case DOWN_OVER:
                    downloaded.dismiss();
                    installApk();
                    break;

                case DOWN_NOSDCARD:
                    downloaded.dismiss();
                    break;

                case DOWN_FAILURE:
                    downloaded.dismiss();
                    break;
            }
        }

        ;
    };

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(savePath, apkName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        Uri uri = null;

        //兼容8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
            if (!hasInstallPermission) {
                startInstallPermissionSettingActivity();
//                return;
            }
        }
        if (Build.VERSION.SDK_INT >= 24) {
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            uri = FileProvider.getUriForFile(mContext.getApplicationContext(), "com.primecloud.huafenghuang.fileprovider", apkfile);
            XLog.i(uri.toString());
        } else {
            uri = Uri.fromFile(apkfile);
        }


        i.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(i);
//        if (updateModel.getIsUpdate() == 1) {
//            AppManager.getInstance().appExit(mContext);//强制结束所有activity
//        }

    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }


    /**
     * 显示一个普通的通知
     */
    public static void showNotification(Context context) {

    }

}

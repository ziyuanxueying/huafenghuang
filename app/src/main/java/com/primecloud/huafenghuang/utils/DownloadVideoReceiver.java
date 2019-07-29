package com.primecloud.huafenghuang.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.primecloud.library.baselibrary.log.XLog;

import java.io.File;

public class DownloadVideoReceiver extends BroadcastReceiver {

    private String path;
    public DownloadVideoReceiver(String path){
        this.path = path;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        XLog.i("視頻下載完成:"+action);
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            ToastUtils.showToast(context, "视频保存到："+path);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(path))));
        }

    }
}

package com.primecloud.huafenghuang.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.material.MaterialContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PictureDown {

    static List<File> files;

    /**
     *
     * @param flag true 图片下载， false 转发
     * @param mContext
     * @param paths
     * @param view
     */
    public static void pictureDown(boolean flag, Context mContext, List<String> paths, MaterialContract.View view){


        Observable.from(paths).flatMap(new Func1<String, Observable<File>>() {
            @Override
            public Observable<File> call(String path) {
                try {
                    return Observable.from(new File[]{Glide.with(mContext)
                            .load(path)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get()});
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }
        })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        if(flag == false)
                          files = new ArrayList<>();
                    }

                    @Override
                    public void onCompleted() {
                        ToastUtils.showToast(mContext, "下载完成");

                        if(flag == false && view != null) {
                            view.downLoadFileResult(files);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(mContext, "下载失败");
                    }

                    @Override
                    public void onNext(File file) {
                        //获取到下载得到的图片，进行本地保存
                        File pictureFolder = Environment.getExternalStorageDirectory();
                        //第二个参数为你想要保存的目录名称
                        File appDir = new File(pictureFolder, "HuaFengHuang");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //把gilde下载得到图片复制到定义好的目录中去
                        copy(file, destFile);

                        if(flag == false){
                            files.add(destFile);
                        }


                        // 最后通知图库更新
                        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                    }
                });
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    private static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

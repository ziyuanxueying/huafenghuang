package com.primecloud.huafenghuang.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.primecloud.huafenghuang.R;

/**
 * Created by FJ on 2017/9/4
 */

public class GlideImageUtil {
    public static final int IMAGE_LOADING = R.mipmap.app_launcher;   //loading占位图
    public static final int IMAGE_ERROR = R.mipmap.app_launcher;  //error图

    /**
     * 从网络加载头像
     *
     * @param context
     * @param img
     */
    public static void glideImgLoder(Context context, String imgUrl, ImageView img) {
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(IMAGE_LOADING)//预加载图片
                .error(IMAGE_ERROR)//加载失败显示图片
                .priority(Priority.HIGH)//优先级
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);//缓存策略
//        Glide.with(context).load(imgUrl).apply(options).into(img);
        Glide.with(context).load(imgUrl).apply(options).into(img);
    }
}
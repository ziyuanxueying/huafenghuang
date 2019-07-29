package com.primecloud.library.baselibrary.utils.glideutils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by Administrator on 2017/1/10.
 */

public class GlideImageLoader extends BaseImageLoader {

    private static GlideImageLoader instance;

    public static GlideImageLoader getInstance() {
        if (instance == null) {
            instance = new GlideImageLoader();
        }
        return instance;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
//                .transition(new DrawableTransitionOptions().crossFade(2000))
                .into(imageView);
    }

    @Override
    public void displayCircleImage(Context context, Object path, ImageView imageView) {
//        RequestOptions options = new RequestOptions();
//        options.bitmapTransform(new CropCircleTransformation());
//        Glide.with(context)
//                .load(path)
//                .apply(options)
//                .transition(new DrawableTransitionOptions().crossFade(2000))
//                .into(imageView);
    }
}

package com.primecloud.library.baselibrary.utils.glideutils;

import android.content.Context;
import android.widget.ImageView;




public abstract class BaseImageLoader implements ImageLoaderInterface<ImageView> {
    @Override
    public ImageView creteImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }
}

package com.primecloud.huafenghuang.application;

import android.content.Context;
import android.text.TextUtils;

import com.primecloud.huafenghuang.ui.user.UserInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        String token = MyApplication.getInstance().getToken();
        if (!TextUtils.isEmpty(token))
            builder.addHeader("token", token);
        return chain.proceed(builder.build());
    }

}

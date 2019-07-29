package com.primecloud.library.baselibrary.net;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by zy on 2018/5/24.
 */

public class BaseNetProvider implements NetProvider {
    @Override
    public Interceptor[] configInterceptors() {
        return new Interceptor[0];
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return null;
    }

    @Override
    public long configConnectTimeoutMills() {
        return 0;
    }

    @Override
    public long configReadTimeoutMills() {
        return 0;
    }

    @Override
    public boolean configLogEnable() {
        return false;
    }
}

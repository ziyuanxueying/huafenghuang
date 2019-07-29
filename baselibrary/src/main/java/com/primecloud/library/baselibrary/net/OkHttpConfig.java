package com.primecloud.library.baselibrary.net;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class OkHttpConfig {


    //连接超时时间
    public static final long connectTimeoutMills = 10 * 1000l;
    //读取超时时间
    public static final long readTimeoutMills = 10 * 1000l;

    // okhttp配置类
    private static NetProvider provider = null;

    // 初始化一个okhttp
    private static OkHttpClient okHttpClient;

    private static OkHttpConfig okHttpConfig;

    private OkHttpConfig(){

    }
    public static OkHttpConfig getInstance(){
        if(okHttpConfig == null){
            synchronized (OkHttpConfig.class) {
                if(okHttpConfig == null){
                    okHttpConfig = new OkHttpConfig();
                }
            }
        }
        return okHttpConfig;
    }
    /**
     * 设置okhttp配置类，这里的配置才是整个项目的主配置
     * @param sProvider
     */
    public  void registerProvider(NetProvider sProvider) {
        OkHttpConfig.provider = sProvider;
    }

    public  OkHttpClient getOkHttpClient(){
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
        if(okHttpClient == null){
            okHttpClient = configOkHttp();
        }
        return okHttpClient;
    }
    // 配置okhttp
    private  OkHttpClient  configOkHttp(){
        OkHttpClient.Builder  builder = new OkHttpClient.Builder();
        //连接超时时间
        builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                ? provider.configConnectTimeoutMills()
                : connectTimeoutMills, TimeUnit.MILLISECONDS);


        builder.readTimeout(provider.configReadTimeoutMills() != 0
                ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);


        CookieJar cookieJar = provider.configCookie();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

//        RequestHandler handler = provider.configHandler();
//        if (handler != null) {
//            builder.addInterceptor(new XInterceptor(handler));
//        }
//
//        if (provider.dispatchProgressEnable()) {
//            builder.addInterceptor(ProgressHelper.get().getInterceptor());
//        }

        // 添加拦截器
        Interceptor[] interceptors = provider.configInterceptors();
        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        // 添加log拦截器
        if (provider.configLogEnable()) {
            LogInterceptor logInterceptor = new LogInterceptor();
            builder.addInterceptor(logInterceptor);
        }
        return builder.build();
    }

    /**
     * 用于在原有的okhttp的配置中，修改某个配置
     * @return
     */
    public OkHttpClient.Builder getOkHttpBuild(){
        if(okHttpClient != null){
            return okHttpClient.newBuilder();
        }
        return new OkHttpClient.Builder();
    }
}

package com.primecloud.library.baselibrary.net;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zy on 2018/4/24.
 */

public class RetrofitConfig {

    // 请求根路径
    private String baseUrl;

    private Retrofit retrofit;

    private static RetrofitConfig retrofitConfig;

    private static NetProvider sProvider = null;

    private OkHttpConfig okHttpConfig = null;


    private RetrofitConfig(String baseUrl){
        this.baseUrl = baseUrl;
        okHttpConfig = OkHttpConfig.getInstance();
    }
    /**
     * @param baseUrl
     * @return
     */
    public static RetrofitConfig getRetrofitConfig(String baseUrl){
        if(retrofitConfig == null){
            synchronized (RetrofitConfig.class) {
                if(retrofitConfig == null){
                    retrofitConfig = new RetrofitConfig(baseUrl);
                }
            }
        }
        return retrofitConfig;
    }

    /**
     * 初始化Retrofit对象
     * @param service
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpConfig.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }
    /**
     * 初始化Retrofit对象，可以更改某次请求baseUrl
     *
     * @param service
     * @param tempBaseUrl
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service, String tempBaseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(tempBaseUrl)
                .client(okHttpConfig.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    /**
     * 用于okhttp的某个配置生成okhttpClineat。
     * @param service
     * @param okBuilder
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service, OkHttpClient.Builder okBuilder) {
        if(okBuilder == null){
            throw new NullPointerException("传入的OkHttpClient.Builder不能为空");
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    /**
     * 重新更改baseUrl
     * @param baseUrl
     */
    public void setBaseUrl(@NotNull String baseUrl){
        if(TextUtils.isEmpty(baseUrl)){
            throw new NullPointerException("传入的baseUrlt不能为空");
        }
        this.baseUrl = baseUrl;
    }
}

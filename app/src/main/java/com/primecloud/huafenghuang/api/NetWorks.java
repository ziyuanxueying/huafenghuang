package com.primecloud.huafenghuang.api;


import com.primecloud.huafenghuang.BuildConfig;
import com.primecloud.library.baselibrary.net.RetrofitConfig;

import retrofit2.Retrofit;

/**
 * Created by zy on 2018/10/15.
 */

public class NetWorks {

    private static Retrofit retrofit;
    private static NetWorks mNetWorks;
    public static final String baseUrl = BuildConfig.URL;
//    public static final String  imageUrl = BuildConfig.PIC_URL;
    private Api api;
    private MyApi myApi;

    private NetWorks(){

    }
    public static NetWorks getInstance() {

        if (mNetWorks == null) {
            mNetWorks = new NetWorks();
        }
        return mNetWorks;
    }

    public <T> T MyConfigRetrofit(Class<T> service){
        return RetrofitConfig.getRetrofitConfig(baseUrl).configRetrofit(service);
    }

    public Api getApi(){
        return api == null ? MyConfigRetrofit(Api.class): api;
    }
    public MyApi getMyApi(){
        return myApi == null ? MyConfigRetrofit(MyApi.class): myApi;
    }

}

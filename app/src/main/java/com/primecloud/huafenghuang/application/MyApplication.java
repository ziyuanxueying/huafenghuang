package com.primecloud.huafenghuang.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.mob.MobSDK;
import com.primecloud.huafenghuang.BuildConfig;
import com.primecloud.huafenghuang.store.DataCleanManager;
import com.primecloud.huafenghuang.store.MethodsCompat;
import com.primecloud.huafenghuang.store.Preference;
import com.primecloud.huafenghuang.store.Storage;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.GlideCacheUtil;
import com.primecloud.huafenghuang.utils.PathUtils;
import com.primecloud.library.baselibrary.base.BaseApplication;
import com.primecloud.library.baselibrary.cache.SharedPref;
import com.primecloud.library.baselibrary.net.NetProvider;
import com.primecloud.library.baselibrary.net.OkHttpConfig;
import com.primecloud.library.baselibrary.utils.FileUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class MyApplication extends BaseApplication {

    private static MyApplication appContext;
    public UserInfo userInfo;
    private Context buyConetxt = null;
    private String productType;
    private int chapterId = -1;
    private int courseId;
    private String token;

    public static MyApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "629213d704", true);
        MobSDK.init(this);
        appContext = this;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public int getCourseId() {
        return courseId;
    }

    public Context getBuyConetxt() {
        return buyConetxt;
    }

    public void setBuyConetxt(Context buyConetxt) {
        this.buyConetxt = buyConetxt;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * 登录信息的保存
     *
     * @param user
     */
    public static void doLogin(Context context, UserInfo user) {
        // 为了防止获取用户信息时，把token的值给覆盖了。
        if( MyApplication.getInstance().getUserInfo().getToken() != null){
            user.setToken(MyApplication.getInstance().getUserInfo().getToken());
        }

        Storage.saveObject(AppConfig.USER_INFO, user);
        Preference.saveStringPreferences(appContext, AppConfig.USER_ID, user.getId());
        MyApplication.getInstance().init();

    }

    /**
     * 清除登录信息(退出账号)
     */
    public static void doLogout(Context context) {


        Storage.clearObject(AppConfig.USER_INFO);

        SharedPref.getInstance(context).putBoolean("msg", true);
        appContext.getSharedPreferences("TimeLogin", MODE_PRIVATE).edit().clear().commit();
        appContext.getSharedPreferences("seleCourseInfo", MODE_PRIVATE).edit().clear().commit();
        MyApplication.getInstance().init();
    }

    private void init() {
        userInfo = Storage.getObject(AppConfig.USER_INFO, UserInfo.class);

        if (userInfo == null) {
            userInfo = new UserInfo();
        }
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {
        if (userInfo == null) {
            init();
        }
        return userInfo;
    }


    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return TextUtils.isEmpty(token) ? getUserInfo().getToken() : token;
    }


    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        //清除指定文件夹下的文件
        try {
            FileUtils.deleteDir(new File(PathUtils.getRootPath()));

//            Imageloader.getInstance(this).clearCache();
//            ImageLoader.getInstance().clearDiskCache();
            GlideCacheUtil.getInstance().clearImageDiskCache(this);
//        	DataCleanManager.cleanDatabases(this);
//            // 清除数据缓存
//            DataCleanManager.cleanInternalCache(this);
            // 2.2版本才有将应用缓存转移到sd卡的功能
            if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
                DataCleanManager.cleanCustomCache(MethodsCompat.getExternalCacheDir(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void registerProvider() {

        OkHttpConfig.getInstance().registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                HeaderInterceptor headerInterceptor = new HeaderInterceptor(getApplicationContext());
                Interceptor[] interceptors = {headerInterceptor};
                return interceptors;
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
                return BuildConfig.DEBUG_LOG;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}


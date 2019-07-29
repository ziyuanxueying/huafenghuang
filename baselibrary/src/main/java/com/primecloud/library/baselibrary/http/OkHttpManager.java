package com.primecloud.library.baselibrary.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.net.OkHttpConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.primecloud.library.baselibrary.net.LogInterceptor.TAG;

/**
 * Created by zy on 2018/7/6.
 */

public class OkHttpManager{

    private String baseUrl;

    public static OkHttpManager okHttpManager;
    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    public OkHttpManager(String baseUrl){
        this.baseUrl = baseUrl;
    }
    /**
     * @param baseUrl
     * @return
     */
    public static OkHttpManager getOkHttpManager(String baseUrl){
        if(okHttpManager == null){
            synchronized (OkHttpManager.class) {
                if(okHttpManager == null){
                    okHttpManager = new OkHttpManager(baseUrl);
                }
            }
        }
        return okHttpManager;
    }


    /**
     * 拼接完整的请求路径
     * @param url
     * @return
     */
    private String getCompleteUrl(String url){
        if (!TextUtils.isEmpty(url) && (url.startsWith("http://") || url.startsWith("https://"))){
            return url;
        }
        String completeUrl = baseUrl + url;
        XLog.i("url:"+completeUrl);
        return completeUrl;
    }

    /**
     * get请求获取Request对象
     * @param url
     * @return
     */
    private Request getRequest(String url){
        Request request = new Request.Builder()
                .get()
                .url(getCompleteUrl(url))
                .build();
        return request;
    }

    /**
     * post请求获取Request对象
     * @param url
     * @param headMap 请求头
     * @param requestBody 请求体
     * @return
     */
    private Request getRequest(String url, Map<String, String> headMap, RequestBody requestBody){
        if(headMap == null){
            Request request = new Request.Builder()
                    .url(getCompleteUrl(url))
                    .post(requestBody)
                    .build();
            return request;
        }else{
            Request.Builder builder = new Request.Builder();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            return builder.url(getCompleteUrl(url))
                    .post(requestBody)
                    .build();
        }
    }

    /**
     * get 同步请求，在异步线程中调用
     * @param url
     * @return
     * @throws IOException
     */
    public Response get(@NonNull String url) throws IOException {
      Request request = getRequest(url);
      return OkHttpConfig.getInstance().getOkHttpClient().newCall(request).execute();
    }
    /**
     * get 异步请求
     * @param url
     * @param callback
     */
    public void getAsny(@NonNull String url, Callback callback){
        Request request = getRequest(url);
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * 带参的get请求
     * @param url
     * @param paramsMap
     * @param callback
     */
    public void getAsny(@NonNull String url,HashMap<String, String> paramsMap, Callback callback){
        Request request = getRequest(url+"?"+requestGetBySyn(paramsMap));
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }
    /**
     * post 同步请求，不带请求头
     * @param url
     * @param requestBody
     * @param callback
     */
    public void post(String url, RequestBody requestBody, Callback callback){
        post(url, requestBody, null, callback);
    }

    /**
     * post 同步请求，带请求头
     * @param url
     * @param requestBody
     * @param headMap
     * @param callback
     */
    public void post(String url, RequestBody requestBody, Map<String, String> headMap, Callback callback){
        Request request = getRequest(url, headMap, requestBody);
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * post 异步请求，带请求头
     * @param url
     * @param requestBody
     * @param headMap
     * @param callback
     */
    public void postAsy(String url, RequestBody requestBody, Map<String, String> headMap, Callback callback){
        Request request = getRequest(url, headMap, requestBody);
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * delete请求
     * @param url
     * @param requestBody
     * @param callback
     */

    public void deleteAsy(String url,RequestBody requestBody, Callback callback){
        Request request = getRequestDelete(url, null, requestBody);
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }
    /**
     * post 异步请求，不带请求头
     * @param url
     * @param requestBody
     * @param callback
     */
    public void postAsy(String url, RequestBody requestBody, Callback callback){
        postAsy(url, requestBody, null, callback);
    }
    /**
     *处理请求参数
     */
    public String requestGetBySyn(HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
//            if(reqbody.startsWith("?")){
//                reqbody=reqbody.substring(1);
//            }
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            return tempParams.toString();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * put请求
     * @param url
     * @param requestBody
     * @param callback
     */
    public void putAsy(String url, RequestBody requestBody, Callback callback){
        Request request = getRequestPut(url, null, requestBody);
        OkHttpConfig.getInstance().getOkHttpClient().newCall(request).enqueue(callback);
    }
    /**
     * put请求获取Request对象
     * @param url
     * @param headMap 请求头
     * @param requestBody 请求体
     * @return
     */
    private Request getRequestPut(String url, Map<String, String> headMap, RequestBody requestBody){
        if(headMap == null){
            Request request = new Request.Builder()
                    .url(getCompleteUrl(url))
                    .put(requestBody)
                    .build();
            return request;
        }else{
            Request.Builder builder = new Request.Builder();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            return builder.url(getCompleteUrl(url))
                    .put(requestBody)
                    .build();
        }
    }

    /**
     * delete请求获取Request对象
     * @param url
     * @param headMap
     * @param requestBody
     * @return
     */
    private Request getRequestDelete(String url,Map<String,String> headMap,RequestBody requestBody){
        if(headMap == null){
            Request request = new Request.Builder()
                    .url(getCompleteUrl(url))
                    .delete(requestBody)
                    .build();
            return request;
        }else{
            Request.Builder builder = new Request.Builder();
            for (Map.Entry<String, String> entry : headMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            return builder.url(getCompleteUrl(url))
                    .delete(requestBody)
                    .build();
        }
    }
}

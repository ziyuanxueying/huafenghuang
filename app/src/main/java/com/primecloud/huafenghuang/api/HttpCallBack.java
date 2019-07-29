package com.primecloud.huafenghuang.api;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.utils.HandlerUtils;
import com.primecloud.huafenghuang.utils.StringUtils;
import com.primecloud.huafenghuang.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zy on 2018/7/6.
 */

public abstract class HttpCallBack<T> implements Callback {

    public abstract void onSuccess(String data, T body);

    String message = "未知异常";

    public abstract void onFailure(String data, String errorMsg);

    Handler mainHandler = HandlerUtils.mainHandler;

    @Override
    public void onFailure(Call call, IOException e) {
        this.onFailure("", e.getMessage());

    }

    @Override
    public void onResponse(Call call, Response response) {

        try {
            if (response != null && response.isSuccessful()) {

                String b = response.body().string();

                Class<?> c = getClass();
                Type type = c.getGenericSuperclass();
                type = ((ParameterizedType) type).getActualTypeArguments()[0];

                Gson gson = new Gson();
                String res = new String(b.getBytes());
                final T body = JSON.parseObject(res, type);

                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(b, body);
                    }
                });


            } else {
                String body = response.body().string();

                JSONObject object = new JSONObject(body);

                if (!object.isNull("message")) {
                    message = object.getString("message");
                }
                final String finalMessage = message;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(body, message);
                        if (StringUtils.notBlank(message)) {
                            ToastUtils.showToast(MyApplication.getInstance(), message);
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.onFailure("", "网络异常");
        } catch (JSONException e) {
            e.printStackTrace();
            this.onFailure("", "数据异常");
        } catch (Exception e){
            e.printStackTrace();
            this.onFailure("", "未知异常");
        }


    }

}

package com.primecloud.library.baselibrary.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.primecloud.library.baselibrary.log.XLog;

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

public abstract class  HttpCallBack<T> implements Callback {

    public abstract void onSuccess(String data,T body);

    public abstract void onFailure(int statusCode, String errorMsg);

    @Override
    public void onFailure(Call call, IOException e) {
        this.onFailure(-1, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(response != null && response.isSuccessful()){
//            this.onSuccess(response.body().string());
            String a=response.body().string();
            Class<?> c = getClass();

            Type type = c.getGenericSuperclass();

            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            String res = new String(a.getBytes());

            T body = JSON.parseObject(res, type);//JsonParseUtils.fromJson(response, type);
            this.onSuccess(null, body);
        }else{
            String body = response.body().string();
            XLog.i("body:"+body);
            try {
                JSONObject object = new JSONObject(body);
                int code = -1;
                if(!object.isNull("code")){
                    code = object.getInt("code");
                }
                String message = "" ;
                this.onFailure(code, message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

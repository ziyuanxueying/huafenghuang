package com.primecloud.huafenghuang.base;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.primecloud.huafenghuang.application.MyApplication;
import com.primecloud.huafenghuang.ui.user.LoginActivity;
import com.primecloud.library.baselibrary.base.AppManager;
import com.primecloud.library.baselibrary.error.NoDataError;
import com.primecloud.library.baselibrary.error.TokenInvalidError;
import com.primecloud.library.baselibrary.log.XLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by zy on 2018/10/15.
 */

public abstract class BaseSubscrible<T> extends Subscriber<T> {

    @Override
    public void onNext(T t) {
        if (t instanceof Response) {
            Response response = (Response) t;
            Headers headers = response.headers();

            String tokenCode = headers.get("tokencode");
            XLog.i("tokenCode:"+tokenCode+"..."+"10000".equals(tokenCode));
            if("10000".equals(tokenCode)){
                onError(new TokenInvalidError("登录异常"));
                return;
            }

            int code = response.code();

            if (code >= 200 && code < 300) {
                onSuccess(t);
            } else if(code == 10000){
                onError(new TokenInvalidError("登录异常"));
            }else {
                try {
                    String error = response.errorBody().string();
                    if (!TextUtils.isEmpty(error)) {
                        JSONObject object = new JSONObject(error);
                        if (!object.isNull("message")) {
                            String message = object.getString("message");
                            onError(new Throwable(message));
                        }
                    } else {
                        onError(new Throwable("未知异常"));
                    }

                } catch (IOException e) {
                    onError(new Throwable("网络异常"));
                } catch (JSONException e) {
                    onError(e);
                }

            }
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onFail(String errorMsg);


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        // 没有数据
        if (e instanceof NoDataError) {
        } else if (e instanceof TokenInvalidError) {// tonken失效

            XLog.i(e.getMessage());
            onFail(e.getMessage());

            Activity activity = AppManager.getInstance().getTopActivity();

            Intent intent = new Intent(MyApplication.getInstance(), LoginActivity.class);
            intent.putExtra("isLogin", 1);
            MyApplication.doLogout(activity);
            AppManager.getInstance().finishAllActivity();
//
            MyApplication.getInstance().startActivity(intent);

        } else if (e instanceof IllegalStateException) {
            onError(new Throwable("未知异常"));
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            onFail("数据异常");
        } else {
            onFail(e.getMessage());
        }
    }
}

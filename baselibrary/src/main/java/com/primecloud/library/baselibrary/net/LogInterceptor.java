package com.primecloud.library.baselibrary.net;

import android.util.Log;

import com.primecloud.library.baselibrary.BaseLibraryConfig;
import com.primecloud.library.baselibrary.log.XLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Log拦截器，用于显示请求之前的log和请求之后的log
 */
public class LogInterceptor implements Interceptor {

    public static final String TAG = BaseLibraryConfig.INTERCEPTOR_LOG_TAG;

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 请求之前调用
        Request request = chain.request();
        logRequest(request);
        // 请求之后调用
        Response response = chain.proceed(request);
        return logResponse(response);
    }
    // 请求的参数
    private void logRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            XLog.d(TAG, "method : " + request.method()+"..url:"+url);
            if (headers != null && headers.size() > 0) {
                XLog.d(TAG, "headers : " + headers.toString());
            }

            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    XLog.i("mediaType:"+mediaType);
                    if (isText(mediaType)) {
                        XLog.d(TAG, "params : " + bodyToString(request));
                    } else {
                        XLog.d(TAG, "params : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 相应的参数
    private Response logResponse(Response response) {

        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String resp = body.string();
                        XLog.json(Log.DEBUG, TAG, resp);
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        XLog.d(TAG, "data : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}

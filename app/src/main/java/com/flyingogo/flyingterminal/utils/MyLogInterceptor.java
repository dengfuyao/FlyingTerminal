package com.flyingogo.flyingterminal.utils;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 作者：dfy on 23/7/2017 18:19
 * <p>
 * 邮箱：dengfuyao@163.com
 */
public class MyLogInterceptor implements Interceptor {
    private static final String TAG = "REQUEST";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e(TAG, "-------------------------begin---------------------------");
        Log.v(TAG, "request:" + request.toString()+"\r\n"+"headers:"+request.headers());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Log.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.v(TAG, "response body:" + content);
        Log.e(TAG, "--------------------------end--------------------------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}

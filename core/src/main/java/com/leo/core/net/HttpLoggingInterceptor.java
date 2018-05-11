package com.leo.core.net;

import android.annotation.SuppressLint;

import com.leo.core.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class HttpLoggingInterceptor implements Interceptor {

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Buffer buffer = new Buffer();
        if (request.body() != null)
            request.body().writeTo(buffer);
        LogUtil.ee(this, String.format("Sending request %s on %s%n%sRequest Params: %s",
                request.url(), chain.connection(), request.headers(), buffer.clone().readUtf8()));
        buffer.close();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        buffer = source.buffer().clone();
        LogUtil.ee(this, String.format("Received response for %s in %.1fms%n%sResponse Json: %s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(),
                buffer.readUtf8()));
        buffer.close();
        return response;
    }

}

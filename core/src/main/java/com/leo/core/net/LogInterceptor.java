package com.leo.core.net;

import android.annotation.SuppressLint;

import com.leo.core.bean.HttpError;
import com.leo.core.factory.DataFactory;
import com.leo.core.iapi.IDataApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

import static com.leo.core.config.Config.COOKIE;
import static com.leo.core.config.Config.USER;

public class LogInterceptor implements Interceptor {

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        String url = request.url().toString();
        Request.Builder builder = request.newBuilder();
        List<String> cookies = getUserDataApi().getStringData(COOKIE);
        if (!TextUtils.isEmpty(cookies)) {
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
            }
        }
        Buffer req = new Buffer();
        if (request.body() != null)
            request.body().writeTo(req);
        Response response = chain.proceed(builder.build());
        cookies = response.headers(COOKIE);
        if (!TextUtils.isEmpty(cookies)) {
            getUserDataApi().saveData(COOKIE, cookies);
        }
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE);
        Buffer res = source.buffer().clone();
        print("url", url);
        print("code", String.format("%d", response.code()));
        print("method", request.method());
        print("time", String.format("%.1fms", (System.nanoTime() - t1) / 1e6d));
        print("head", response.headers().toString());
        print("params", req.readUtf8());
        print("json", decode(res.readUtf8()));
        req.close();
        res.close();
        return response;
    }

    private IDataApi getUserDataApi() {
        return DataFactory.getApi().switchTable(USER);
    }

    private void print(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            if (TextUtils.isTrimEmpty(value)) {
                value = null;
            } else if (value.length() > 10000) {
                value = "长度超过了10000,被隐藏掉了!!!";
            }
            LogUtil.ii(this, key + ": " + value);
        }
    }

    private String decode(String text) {
        if (!TextUtils.isEmpty(text)) {
            try {
                return URLDecoder.decode(text, "utf-8");
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return text;
    }

}

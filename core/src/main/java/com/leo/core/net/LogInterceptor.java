package com.leo.core.net;

import android.annotation.SuppressLint;

import com.leo.core.factory.DataFactory;
import com.leo.core.iapi.api.IDataApi;
import com.leo.core.util.FileSizeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.leo.core.config.Config.COOKIE;
import static com.leo.core.config.Config.RX;
import static com.leo.core.config.Config.RX_TO;
import static com.leo.core.util.DecoderUtil.decode;

public class LogInterceptor implements Interceptor {

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        String url = request.url().toString();
        Request.Builder builder = request.newBuilder();
        builder.url(decode(url));
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
        InterceptorBean bean = new InterceptorBean();
        bean.setUrl(decode(url));
        bean.setRequestSize(FileSizeUtil.getFormetFileSize(getLength(request.body())));
        bean.setResponseSize(FileSizeUtil.getFormetFileSize(getLength(response.body())));
        bean.setCode(String.format("%d", response.code()));
        bean.setMethod(request.method());
        bean.setTime(String.format("%.1fms", (System.nanoTime() - t1) / 1e6d));
        bean.setRequestHead(request.headers().toString());
        bean.setResponseHead(response.headers().toString());
        bean.setContentType(getContentType(request.body()));
        bean.setParams(printParams(request.body(), req));
        bean.setResponse(decode(res.readUtf8()));
        bean.completed();
        req.close();
        res.close();
        print(bean);
        return response;
    }

    private String printParams(RequestBody body, Buffer req) {
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < formBody.size(); i++) {
                builder.append(i == 0 ? "" : "&")
                        .append(decode(formBody.encodedName(i)))
                        .append("=")
                        .append(getParams(decode(formBody.encodedValue(i)), null));
            }
            return builder.toString().replaceAll(RX, RX_TO);
        } else {
            return req == null ? null : decode(req.readUtf8());
        }
    }

    private String getParams(String params, Object obj) {
        try {
            if (!TextUtils.isEmpty(params)) {
                obj = new JSONTokener(params).nextValue();
            }
            if (obj instanceof String) {
                return check(params);
            } else if (obj instanceof JSONObject) {
                for (Iterator<String> it = ((JSONObject) obj).keys(); it.hasNext(); ) {
                    String key = it.next();
                    Object item = ((JSONObject) obj).get(key);
                    if (item instanceof String) {
                        ((JSONObject) obj).put(key, check((String) item));
                    } else {
                        ((JSONObject) obj).put(key, getParams(null, item));
                    }
                }
                return obj.toString();
            } else if (obj instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                    Object item = ((JSONArray) obj).get(i);
                    if (item instanceof String) {
                        ((JSONArray) obj).put(i, check((String) item));
                    } else {
                        ((JSONArray) obj).put(i, getParams(null, item));
                    }
                }
                return obj.toString();
            }
        } catch (JSONException ignored) {
        }
        return check(params);
    }

    private String check(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (text.length() > 100) {
                return text.substring(0, 100) + "...";
            }
        }
        return text;
    }

    private IDataApi getUserDataApi() {
//        return DataFactory.getApi().switchTable(USER);
        return DataFactory.getApi();
    }

    private void print(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            if(TextUtils.count(value) > 10000){
                value = value.substring(0, 10000);
            }
            LogUtil.ii(this, key + ": " + value);
        }
    }

    private void print(InterceptorBean bean) {
        if (bean != null && !bean.check()) {
            print("url", bean.getUrl());
            print("code", bean.getCode());
            print("method", bean.getMethod());
            print("time", bean.getTime());
            print("contentType", bean.getContentType());
            print("request size", bean.getRequestSize());
            print("response size", bean.getResponseSize());
            print("request head", bean.getRequestHead());
            print("response head", bean.getResponseHead());
            print("params", bean.getParams());
        }
        print("response", bean.getResponse());
    }

    private long getLength(RequestBody body) throws IOException {
        return body == null ? 0 : body.contentLength();
    }

    private long getLength(ResponseBody body) {
        return body == null ? 0 : body.contentLength();
    }

    private String getContentType(RequestBody body) {
        return body != null && body.contentType() != null ? body.contentType().toString() : null;
    }

}
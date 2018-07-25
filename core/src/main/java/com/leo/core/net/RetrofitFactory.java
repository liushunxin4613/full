package com.leo.core.net;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.leo.core.config.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 用于获取配置好的retrofit对象
 * 参数与相应结果.
 * <br/>
 * Created by Ryan on 2015/12/30.
 */
public class RetrofitFactory {

    private final static int TIME_OUT_SECONDS = Config.TIME_OUT_SECONDS;

    private static boolean log;
    private static RetrofitFactory instance;
    private static Retrofit.Builder builder;

    public static RetrofitFactory getInstance() {
        if (instance == null) {
            synchronized (RetrofitFactory.class) {
                if (instance == null) {
                    instance = new RetrofitFactory();
                }
            }
        }
        return instance;
    }

    public static void show(boolean show) {
        RetrofitFactory.log = show;
    }

    /**
     * 获取配置好的retrofit对象来生产Manager对象
     */
    public Retrofit getRetrofit(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalStateException("请在调用getInstance之前先调用init");
        }
        if (builder == null) {
            builder = new Retrofit.Builder();
            // Gson double类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp))
                            tmp = "0";
                        return Double.parseDouble(tmp);
                    }
                    return in.nextDouble();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };
            // Gson long类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp))
                            tmp = "0";
                        return Long.parseLong(tmp);
                    }
                    return in.nextLong();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };
            // Gson int类型转换, 避免空字符串解析出错
            final TypeAdapter<Number> INT = new TypeAdapter<Number>() {
                @Override
                public Number read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    if (in.peek() == JsonToken.STRING) {
                        String tmp = in.nextString();
                        if (TextUtils.isEmpty(tmp))
                            tmp = "0";
                        return Integer.parseInt(tmp);
                    }
                    return in.nextInt();
                }

                @Override
                public void write(JsonWriter out, Number value) throws IOException {
                    out.value(value);
                }
            };
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(
                    double.class, Double.class, DOUBLE));
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(
                    long.class, Long.class, LONG));
            gsonBuilder.registerTypeAdapterFactory(TypeAdapters.newFactory(
                    int.class, Integer.class, INT));
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(NullOnEmptyConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new LogInterceptor())
                            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
//                            .sslSocketFactory(SSLUtil.getSSLSocketFactory())
                            .build());
        }
        return builder.baseUrl(url).build();
    }

}

package com.leo.core.api.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leo.core.iapi.api.IGsonDecodeApi;

import java.lang.reflect.Type;

public class GsonDecodeApi implements IGsonDecodeApi<GsonDecodeApi, Object, Object, Object> {

    private Gson gson;

    public GsonDecodeApi() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public String encode(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <R> R decode(Object in, Object param) {
        try {
            if (param instanceof Class) {
                if (in instanceof String) {
                    return (R) decode((String) in, (Class) param);
                }
                return (R) decode(in, (Class) param);
            } else if (param instanceof Type) {
                if (in instanceof String) {
                    return (R) decode((String) in, (Type) param);
                }
                return (R) decode(in, (Type) param);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public <R> R decode(String in, Class<R> clz) {
        return gson.fromJson(in, clz);
    }

    @Override
    public <R> R decode(String in, Type type) {
        return gson.fromJson(in, type);
    }

    @Override
    public <R> R decode(Object obj, Class<R> clz) {
        return decode(encode(obj), clz);
    }

    @Override
    public <R> R decode(Object obj, Type type) {
        return decode(encode(obj), type);
    }

}
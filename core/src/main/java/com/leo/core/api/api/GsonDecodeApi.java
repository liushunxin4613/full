package com.leo.core.api.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.leo.core.iapi.api.IGsonDecodeApi;

import java.io.Reader;
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
            if (in instanceof String) {
                if (param instanceof Class) {
                    return (R) decode((String) in, (Class) param);
                } else if (param instanceof Type) {
                    return (R) decode((String) in, (Type) param);
                }
            } else if (in instanceof JsonElement) {
                if (param instanceof Class) {
                    return (R) decode((JsonElement) in, (Class) param);
                } else if (param instanceof Type) {
                    return (R) decode((JsonElement) in, (Type) param);
                }
            } else if (in instanceof JsonReader && param instanceof Type) {
                return (R) decode((JsonReader) in, (Type) param);
            } else if (in instanceof Reader) {
                if (param instanceof Class) {
                    return (R) decode((Reader) in, (Class) param);
                } else if (param instanceof Type) {
                    return (R) decode((Reader) in, (Type) param);
                }
            }
        } catch (Exception ignored) {
//            ignored.printStackTrace();
        }
        return null;
    }

    @Override
    public <R> R decode(JsonElement in, Class<R> clz) {
        return gson.fromJson(in, clz);
    }

    @Override
    public <R> R decode(JsonElement in, Type type) {
        return gson.fromJson(in, type);
    }

    @Override
    public <R> R decode(JsonReader in, Type type) {
        return gson.fromJson(in, type);
    }

    @Override
    public <R> R decode(Reader in, Class<R> clz) {
        return gson.fromJson(in, clz);
    }

    @Override
    public <R> R decode(Reader in, Type type) {
        return gson.fromJson(in, type);
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
}

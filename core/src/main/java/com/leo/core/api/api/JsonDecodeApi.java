package com.leo.core.api.api;

import com.alibaba.fastjson.JSON;
import com.leo.core.iapi.api.IGsonDecodeApi;

import java.lang.reflect.Type;

public class JsonDecodeApi implements IGsonDecodeApi<JsonDecodeApi, Object, Object, Object> {

    @Override
    public String encode(Object obj) {
        return JSON.toJSONString(obj);
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
//            ignored.printStackTrace();
        }
        return null;
    }

    @Override
    public <R> R decode(String in, Class<R> clz) {
        return JSON.parseObject(in, clz);
    }

    @Override
    public <R> R decode(String in, Type type) {
        return JSON.parseObject(in, type);
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
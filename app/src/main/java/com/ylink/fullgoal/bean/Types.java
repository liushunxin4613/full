package com.ylink.fullgoal.bean;

import com.google.gson.internal.$Gson$Types;
import com.leo.core.util.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Types<T> {

    public Type getType(){
        Type superclass = getClass().getGenericSuperclass();
        LogUtil.ee(this, "superclass: " + superclass);
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        LogUtil.ee(this, "parameterized: " + parameterized);
        LogUtil.ee(this, "getActualTypeArguments: " + parameterized.getActualTypeArguments()[0]);
        Type type = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        LogUtil.ee(this, "type: " + type);
        return type;
    }

}

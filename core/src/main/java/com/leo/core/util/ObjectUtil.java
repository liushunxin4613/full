package com.leo.core.util;

import com.leo.core.api.api.ObjectApi;
import com.leo.core.iapi.api.IObjectApi;

public class ObjectUtil {

    private static IObjectApi objectApi;

    private static IObjectApi getApi() {
        if(objectApi == null){
            objectApi = new ObjectApi();
        }
        return objectApi;
    }

    public static Class getClass(String name) {
        return getApi().getClass(name);
    }

    public static Object getObject(String name) {
        return getApi().getObject(name);
    }

    public static Object getObject(String name, Class[] cs, Object[] os) {
        return getApi().getObject(name, cs, os);
    }

    public static Object getObject(String name, Class cs, Object os) {
        return getApi().getObject(name, cs, os);
    }

    public static <P> P getObject(String name, Class<P> pClz, Class[] cs, Object[] os) {
        return (P) getApi().getObject(name, pClz, cs, os);
    }

    public static <P> P getObject(String name, Class<P> pClz, Class cs, Object os) {
        return (P) getApi().getObject(name, pClz, cs, os);
    }

    public static Object getObject(Class clz, Class[] cs, Object[] os) {
        return getApi().getObject(clz, cs, os);
    }

    public static Object getObject(Class clz, Class cs, Object os) {
        return getApi().getObject(clz, cs, os);
    }

    public static <P> P getObject(String name, Class<P> pClz) {
        return (P) getApi().getObject(name, pClz);
    }

    public static <P> P getObject(Class<P> clz) {
        return (P) getApi().getObject(clz);
    }

}

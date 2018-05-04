package com.leo.core.util;

import com.leo.core.api.ClassChild1HelperApi;
import com.leo.core.iapi.IClassChild1Api;

/**
 * class 辅助工具类
 */
public class ClassApiUtil {

    private static IClassChild1Api getApi() {
        return ClassBindUtil.getApi(ClassChild1HelperApi.class);
    }

    public static String getClassName(Object obj, String exclude) {
        return getApi().getClassName(obj, exclude);
    }

    public static String getClassName(Class clz, String exclude) {
        return getApi().getClassName(clz, exclude);
    }

    public static String getClassName(Object obj) {
        return getApi().getClassName(obj);
    }

    public static String getClassName(Class clz) {
        return getApi().getClassName(clz);
    }

}

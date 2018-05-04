package com.leo.core.api;

import android.text.TextUtils;

import com.leo.core.iapi.IClassChild1Api;

/**
 * class辅助类
 */
public class ClassChild1HelperApi implements IClassChild1Api<ClassChild1HelperApi> {

    private final static String DEFAULT = "";
    private static String EXCLUDE = "";

    public static void init(String exclude){
        EXCLUDE = exclude;
    }

    @Override
    public String getClassName(Object obj, String exclude) {
        if (obj != null)
            return getClassName(obj.getClass(), exclude);
        return DEFAULT;
    }

    @Override
    public String getClassName(Class clz, String exclude) {
        if (clz == null)
            return DEFAULT;
        String name = clz.getSimpleName();
        if (!TextUtils.isEmpty(name)) {
            return name;
        }
        name = clz.getName();
        if (TextUtils.isEmpty(exclude)) {
            return name;
        }
        if (name.startsWith(exclude)) {
            return name.substring(exclude.length());
        }
        return name;
    }

    @Override
    public String getClassName(Object obj) {
        if (obj != null)
            return getClassName(obj.getClass());
        return DEFAULT;
    }

    @Override
    public String getClassName(Class clz) {
        return getClassName(clz, EXCLUDE);
    }
}

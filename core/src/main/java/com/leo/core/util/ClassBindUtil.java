package com.leo.core.util;

import android.content.Context;

import com.leo.core.api.api.ClassBindImplApi;
import com.leo.core.api.api.ClassCreateApi;
import com.leo.core.core.MainManage;
import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.api.IBindContextApi;

public class ClassBindUtil {

    private static ClassCreateApi api = new ClassCreateApi();

    private static <T extends IApi> ClassBindImplApi<T> getBindApi(Class<T> clz) {
        return api.create(clz, new ClassBindImplApi<>(clz));
    }

    public static <T extends IApi> T getApi(Class<T> clz) {
        return getBindApi(clz).getApi();
    }

    public static <T extends IBindContextApi> T getApi(Context context, Class<T> clz) {
        return MainManage.getContextApi(context, clz);
    }

    public static void close() {
        api.clear();
    }

}

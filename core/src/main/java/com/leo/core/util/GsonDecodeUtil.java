package com.leo.core.util;

import com.leo.core.api.api.GsonDecodeApi;
import com.leo.core.iapi.api.IGsonDecodeApi;

public class GsonDecodeUtil {

    private static IGsonDecodeApi<GsonDecodeApi, Object, Object, Object> getApi() {
        return ClassBindUtil.getApi(GsonDecodeApi.class);
    }

    public static String encode(Object obj) {
        return getApi().encode(obj);
    }

    public static <R> R decode(Object in, Object param) {
        return getApi().decode(in, param);
    }

}

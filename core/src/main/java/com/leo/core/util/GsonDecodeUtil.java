package com.leo.core.util;

import com.leo.core.api.api.JsonDecodeApi;
import com.leo.core.iapi.api.IGsonDecodeApi;

public class GsonDecodeUtil {

    private static IGsonDecodeApi<JsonDecodeApi, Object, Object, Object> getApi() {
        return ClassBindUtil.getApi(JsonDecodeApi.class);
    }

    public static String encode(Object obj) {
        return getApi().encode(obj);
    }

    public static <R> R decode(Object in, Object param) {
        return getApi().decode(in, param);
    }

}
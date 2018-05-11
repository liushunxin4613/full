package com.leo.core.util;

import com.leo.core.api.LogApi;
import com.leo.core.iapi.ILogApi;

public class LogUtil {

    private static ILogApi<LogApi, Object, Object> getApi() {
        return ClassBindUtil.getApi(LogApi.class);
    }

    public static void openLog() {
        getApi().openLog();
    }

    public static void closeThisLog(Object in) {
        getApi().closeThisLog(in);
    }

    public static void closeILog() {
        getApi().closeILog();
    }

    public static void closeELog() {
        getApi().closeELog();
    }

    public static String getLog(Object obj) {
        return getApi().getLog(obj);
    }

    public static String getLog(Object... obj) {
        return getApi().getLog(obj);
    }

    public static void ii(Object in, Object param) {
        getApi().ii(in, param);
    }

    public static void ii(Object in, Object... param) {
        getApi().ii(in, param);
    }

    public static void ee(Object in, Object param) {
        getApi().ee(in, param);
    }

    public static void ee(Object in, Object... param) {
        getApi().ee(in, param);
    }
}

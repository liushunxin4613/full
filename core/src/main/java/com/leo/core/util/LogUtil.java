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

    public static void i(Object in, Object param) {
        getApi().i(in, param);
    }

    public static void i(Object in, Object... param) {
        getApi().i(in, param);
    }

    public static void e(Object in, Object param) {
        getApi().e(in, param);
    }

    public static void e(Object in, Object... param) {
        getApi().e(in, param);
    }
}

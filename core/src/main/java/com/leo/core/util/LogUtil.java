package com.leo.core.util;

import com.leo.core.api.api.LogApi;
import com.leo.core.iapi.api.ILogApi;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.other.MMap;

import java.util.LinkedHashMap;

public class LogUtil {

    private static ILogApi<LogApi, Object, Object> getApi() {
        return ClassBindUtil.getApi(LogApi.class);
    }

    public synchronized static void openLog() {
        getApi().openLog();
    }

    public synchronized static void closeThisLog(Object in) {
        getApi().closeThisLog(in);
    }

    public synchronized static void closeILog() {
        getApi().closeILog();
    }

    public synchronized static void closeELog() {
        getApi().closeELog();
    }

    public synchronized static String getLog(Object obj) {
        return getApi().getLog(obj);
    }

    public synchronized static String getLog(Object... args) {
        return getApi().getLog(args);
    }

    public synchronized static String getLog(boolean json, Object... args) {
        return getApi().getLog(json, args);
    }

    public synchronized static void ii(Object in, Object param) {
        getApi().ii(in, param);
    }

    public synchronized static void ii(Object in, Object... args) {
        getApi().ii(in, args);
    }

    public synchronized static void ee(Object in, Object param) {
        getApi().ee(in, param);
    }

    public synchronized static void ee(Object in, Object... args) {
        getApi().ee(in, args);
    }

    public synchronized static void ee(Object in, IObjAction<MMap<String, Object>> action) {
        if (action != null) {
            MMap<String, Object> mMap = new MMap<>();
            action.execute(mMap.map(new LinkedHashMap<>()));
            getApi().ee(in,  mMap.map());
        }
    }

}
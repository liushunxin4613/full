package com.leo.core.api.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leo.core.iapi.api.ILogApi;
import com.leo.core.util.ClassApiUtil;
import com.leo.core.util.JsonShowUtil;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class LogApi implements ILogApi<LogApi, Object, Object> {

    private static boolean debug = false;
    private static boolean i = true;
    private static boolean e = true;
    private static List<String> exclude;
    private final static String DEFAULT = "";
    private final static String INTERVAL = ", ";
    private String name;
    private Gson gson;

    @Override
    public LogApi openLog() {
        debug = true;
        exclude = new ArrayList<>();
        gson = new GsonBuilder().disableHtmlEscaping().create();
        return this;
    }

    @Override
    public LogApi closeThisLog(Object obj) {
        if (debug && obj != null) {
            exclude.add(ClassApiUtil.getClassName(obj));
        }
        return this;
    }

    @Override
    public LogApi closeILog() {
        i = false;
        return this;
    }

    @Override
    public LogApi closeELog() {
        e = false;
        return this;
    }

    @Override
    public String getLog(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof Comparable) {
            return String.valueOf(obj);
        } else if (obj != null) {
            try {
                String json = gson.toJson(obj);
                return JsonShowUtil.getShowJson("\n" + json);
            } catch (Exception e) {
                return obj.toString();
            }
        }
        return DEFAULT;
    }

    @Override
    public String getLog(Object... obj) {
        StringBuilder log = new StringBuilder(DEFAULT);
        if (obj != null) {
            for (int j = 0; j < obj.length; j++) {
                if (j != 0) {
                    log.append(INTERVAL);
                }
                log.append(getLog(obj[j]));
            }
        }
        return log.toString();
    }

    @Override
    public LogApi ii(Object obj, Object log) {
        if (check(obj, log) && i) {
            log(true, name, getLog(log));
        }
        return this;
    }

    @Override
    public LogApi ii(Object obj, Object... param) {
        if (check(obj, param) && i) {
            log(true, name, getLog(param));
        }
        return this;
    }

    @Override
    public LogApi ee(Object obj, Object log) {
        if (check(obj, log) && e) {
            log(false, name, getLog(log));
        }
        return this;
    }

    @Override
    public LogApi ee(Object obj, Object... param) {
        if (check(obj, param) && e) {
            log(false, name, getLog(param));
        }
        return this;
    }

    private boolean contains(String name) {
        return exclude.contains(name);
    }

    private boolean check(Object obj, Object log) {
        if (debug && obj != null && log != null) {
            if (obj instanceof String) {
                name = (String) obj;
            } else {
                name = ClassApiUtil.getClassName(obj);
            }
            return !contains(name);
        }
        return false;
    }

    private void log(boolean ii, String tag, String msg) {
        if (!TextUtils.isEmpty(tag)) {
            int max = 1024 * 3;
            if (msg == null) {
                msg = "";
            }
            int length = msg.length();
            int num = length / max;
            num += (length % max == 0 && length != 0) ? 0 : 1;
            int start;
            int end;
            for (int j = 0; j < num; j++) {
                start = j * max;
                end = (j == num - 1) ? length : (j + 1) * max;
                if (ii) {
                    Log.i(tag, msg.substring(start, end));
                } else {
                    Log.e(tag, msg.substring(start, end));
                }
            }
        }
    }

}

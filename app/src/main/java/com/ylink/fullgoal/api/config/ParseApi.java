package com.ylink.fullgoal.api.config;

import android.annotation.SuppressLint;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.leo.core.api.core.ThisApi;
import com.leo.core.bean.Completed;
import com.leo.core.bean.DataEmpty;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.api.IParseApi;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.net.Exceptions;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.util.ToastUtil;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.hb.CodeHb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;

import static com.leo.core.config.Config.RX;
import static com.leo.core.config.Config.RX_TO;
import static com.leo.core.util.TextUtils.getEmptyLength;

public class ParseApi<T extends ParseApi> extends ThisApi<T> implements IParseApi<T> {

    private int what;
    private String msg;
    private String path;
    private List<Type> data;
    private Map<String, Type> typeMap;
    private Map<Type, List<IPathMsgAction>> apiMap;

    public ParseApi() {
        this.data = new ArrayList<>();
        this.typeMap = new HashMap<>();
        this.apiMap = new HashMap<>();
    }

    @Override
    public T init(String path, int what, String msg) {
        this.path = path;
        this.what = what;
        this.msg = msg;
        return getThis();
    }

    @Override
    public T copy() {
        ParseApi<T> api = new ParseApi<>();
        api.data = data;
        api.typeMap = typeMap;
        api.apiMap = apiMap;
        return (T) api;
    }

    @Override
    public T addRootType(Class... args) {
        execute(args, obj -> executeNon(getExecute(TypeToken.get(obj), TypeToken::getType),
                item -> data.add(item)));
        return getThis();
    }

    @Override
    public T addRootType(TypeToken... args) {
        execute(args, obj -> executeNon(getExecute(obj, TypeToken::getType),
                item -> data.add(item)));
        return getThis();
    }

    @Override
    public T clearRootData() {
        data.clear();
        return getThis();
    }

    @Override
    public <A> T put(String key, TypeToken<A> token) {
        if (!TextUtils.isEmpty(key) && token != null) {
            typeMap.put(key, token.getType());
        }
        return getThis();
    }

    @Override
    public <A> T put(String key, Class<A> clz) {
        put(key, TypeToken.get(clz));
        return getThis();
    }

    @Override
    public T clearPutParseMap() {
        typeMap.clear();
        return getThis();
    }

    @Override
    public <A> T add(TypeToken<A> token, IPathMsgAction<A> action) {
        if (token != null && action != null) {
            List<IPathMsgAction> data = apiMap.get(token.getType());
            if (data == null) {
                apiMap.put(token.getType(), new ArrayList<>());
            }
            apiMap.get(token.getType()).add(action);
        }
        return getThis();
    }

    @Override
    public <A> T add(Class<A> clz, IPathMsgAction<A> action) {
        add(TypeToken.get(clz), action);
        return getThis();
    }

    @Override
    public <A> T addList(Class<A> clz, IPathMsgAction<List<A>> action) {
        add((TypeToken<List<A>>) TypeToken.getParameterized(List.class, clz), action);
        return getThis();
    }

    @Override
    public T clearAddParseMap() {
        apiMap.clear();
        return getThis();
    }

    @Override
    public T clearParse() {
        clearRootData();
        clearPutParseMap();
        clearAddParseMap();
        return getThis();
    }

    @Override
    public <B> T execute(B bean) {
        if (bean instanceof String) {
            onString((String) bean);
        } else if (bean instanceof ResponseBody) {
            onResponseBody((ResponseBody) bean);
        } else if (bean instanceof Completed) {
            onCompleted((Completed) bean);
        } else if (bean instanceof Exceptions) {
            onExceptions((Exceptions) bean);
        }
        return getThis();
    }

    private <A> void onItem(A item, Type type) {
        /*LogUtil.ee(this, "type: " + type + ", apiMap.get(type): " + (apiMap.get(type) == null) +
                ", item: " + LogUtil.getLog(item));*/
        executeNon(item, obj -> execute(apiMap.get(type), action -> {
            if (obj instanceof DataFg) {
                if (!((DataFg) obj).isSuccess() && !TextUtils.isEmpty(((DataFg) obj).getMessage())) {
                    ToastUtil.show(((DataFg) obj).getMessage());
                }
            }
            if (obj instanceof CodeHb) {
                if (!((CodeHb) obj).isSuccess() && !TextUtils.isEmpty(((CodeHb) obj).getMessage())) {
                    ToastUtil.show(((CodeHb) obj).getMessage());
                }
            }
            action.execute(path == null ? "" : path, what, msg, obj);
        }));
    }

    private void onString(String text) {
        final String txt = getCleanJsonString(text, null);
        if (!TextUtils.isEmpty(data)) {
            int emptyCount = getEmptyLength(txt);
            /*LogUtil.ee(this, "txt: " + txt);
            LogUtil.ee(this, "emptyCount: " + emptyCount);*/
            execute(data, type -> {
                Object obj = GsonDecodeUtil.decode(txt, type);
                String encode = GsonDecodeUtil.encode(obj);
                /*LogUtil.ee(this, "type: " + type.toString());
                LogUtil.ee(this, "getEmptyLength(encode): " + getEmptyLength(encode));
                LogUtil.ee(this, "encode: " + encode);*/
                if (emptyCount == getEmptyLength(encode)) {
                    onData(obj, type);
                }
            });
        }
        if (!TextUtils.isEmpty(typeMap)) {
            toJson(txt);
        }
    }

    @SuppressLint("NewApi")
    private String getCleanJsonString(String text, Object obj) {
        try {
            if (!TextUtils.isEmpty(text)) {
                obj = new JSONTokener(text).nextValue();
            }
            if (obj instanceof JSONObject) {
                JSONObject jo = (JSONObject) obj;
                Set<String> set = new HashSet<>();
                for (Iterator<String> it = jo.keys(); it.hasNext(); ) {
                    String key = it.next();
                    Object item = jo.get(key);
                    if (TextUtils.isEmpty(key)) {
                        jo.remove(key);
                    } else if (item instanceof String || item == null) {
                        if (TextUtils.isTrimEmpty((String) item)) {
                            set.add(key);
                        }
                    } else {
                        getCleanJsonString(null, item);
                    }
                }
                if (!TextUtils.isEmpty(set)) {
                    for (String key : set) {
                        jo.remove(key);
                    }
                }
                text = jo.toString();
                if (!TextUtils.isEmpty(text)) {
                    return text.replaceAll(RX, RX_TO);
                }
            } else if (obj instanceof JSONArray) {
                JSONArray ja = (JSONArray) obj;
                Set<Integer> set = new HashSet<>();
                for (int i = 0; i < ja.length(); i++) {
                    Object item = ja.get(i);
                    if (TextUtils.isEmpty(item)) {
                        set.add(i);
                    } else {
                        getCleanJsonString(null, item);
                    }
                }
                if (!TextUtils.isEmpty(set)) {
                    for (Integer key : set) {
                        ja.remove(key);
                    }
                }
                text = ja.toString();
                if (!TextUtils.isEmpty(text)) {
                    return text.replaceAll(RX, RX_TO);
                }
            }
        } catch (JSONException ignored) {
        }
        return null;
    }

    private void onData(Object obj, Type type) {
        if (obj != null) {
            onItem(obj, type);
            Class clz = obj.getClass();
            if (obj instanceof List) {
                if (!TextUtils.isEmpty(obj)) {
                    Class itemClz = getDataItemType((List) obj);
                    if (itemClz != null) {
                        onItem(obj, TypeToken.getParameterized(List.class, itemClz).getType());
                    } else {
                        onItem(new DataEmpty(), DataEmpty.class);
                    }
                }
            } else if (check(clz)) {
                execute(clz.getDeclaredFields(), field -> execute(!Modifier.isStatic(
                        field.getModifiers()) && check(field.getType()) &&
                        !TextUtils.equals(field.getName(), "val$contentType"), () -> {
                    try {
                        field.setAccessible(true);
                        Object item = field.get(obj);
                        if (item != null) {
                            onData(item, item.getClass());
                        }
                    } catch (IllegalAccessException e) {
                        onExceptions(new Exceptions("解析异常", 101, e));
                    }
                }));
            }
        }
    }

    private void toJson(String text) {
        try {
            if (!TextUtils.isEmpty(text)) {
                Object obj = new JSONTokener(text).nextValue();
                if (obj instanceof JSONObject) {
                    JSONObject jo = (JSONObject) obj;
                    execute(typeMap, (key, value) -> execute(jo.has(key), () -> {
                        try {
                            Object mode = jo.get(key);
                            if (mode instanceof String) {
                                Object item = GsonDecodeUtil.decode(mode, value);
                                onData(item, item.getClass());
                            }
                        } catch (JSONException e) {
                            onExceptions(new Exceptions("json解析异常", HttpError.ERROR_JSON_PARSE, e));
                        }
                    }));
                }
            } else {
                onExceptions(new Exceptions("json字符串不能为空", HttpError.ERROR_DATA_NULL,
                        new RuntimeException("json字符串不能为空")));
            }
        } catch (Exception e) {
            onExceptions(new Exceptions("json解析异常", HttpError.ERROR_JSON_PARSE, e));
        }
    }

    private void onResponseBody(ResponseBody body) {
        try {
            onString(body.string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean check(Class clz) {
        return clz != null && !clz.isPrimitive() && !String.class.isAssignableFrom(clz) &&
                !LinkedTreeMap.class.isAssignableFrom(clz);
    }

    private void onCompleted(Completed completed) {
        LogUtil.ii(this, "*********************** 本次请求结束!!! ***********************");
        onItem(completed, completed.getClass());
    }

    private void onExceptions(Exceptions exception) {
        onItem(exception, exception.getClass());
//        executeNon(exception.getE(), Throwable::printStackTrace);
        LogUtil.ii(this, "本次异常: " + exception.getMessage());
    }

    private Class getDataItemType(List data) {
        if (!TextUtils.isEmpty(data)) {
            for (Object obj : data) {
                if (obj != null) {
                    return obj.getClass();
                }
            }
        }
        return null;
    }

}

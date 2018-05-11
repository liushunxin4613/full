package com.leo.core.net;

import com.leo.core.bean.Completed;
import com.leo.core.bean.HttpError;
import com.leo.core.iapi.IClassAddApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.other.ParamType;
import com.leo.core.util.GsonDecodeUtil;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

@SuppressWarnings("rawtypes")
public class RetrofitSubscriber<T extends RetrofitSubscriber, R> extends Subscriber<R> implements IClassAddApi<T>, IThisApi<T> {

    private Map<String, List<IRunApi>> apiMap = new HashMap<>();

    protected ParamType get(Type rawType, Type... typeArguments) {
        return ParamType.get(rawType, typeArguments);
    }

    protected Class getClz(String mode) {
        return null;
    }

    protected Class getListClz(String mode) {
        return getClz(mode);
    }

    protected Class getDataClz(String mode) {
        return getClz(mode);
    }

    @Override
    public void onCompleted() {
        LogUtil.ii(this, "本次请求完成!!!");
        onItem(new Completed(), Completed.class);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException) {
            toError(HttpError.ERROR_CONNECT, "网络异常", e);
        } else if (e instanceof HttpException) {
            toError(HttpError.ERROR_HTTP, "服务器异常", e);
        } else if (e instanceof SocketTimeoutException) {
            toError(HttpError.ERROR_SOCKET_TIME_OUT, "连接超时", e);
        } else {
            toError(HttpError.ERROR_DATA, "数据异常", e);
        }
    }

    @Override
    public void onNext(R next) {
        if(next != null){
            if(next instanceof String){
                onItem(next, String.class);
                onJson((String) next);
            } else {
                onData(next, null);
            }
        } else {
            toError(HttpError.ERROR_DATA_NULL, "next数据为空", null);
        }
    }

    @Override
    public <R> T add(Class<R> clz, IRunApi<R> api) {
        if (clz != null && api != null) {
            String name = clz.toString();
            List<IRunApi> data = apiMap.get(name);
            if (data == null) {
                apiMap.put(name, new ArrayList<>());
            }
            apiMap.get(name).add(api);
        }
        return getThis();
    }

    @Override
    public <R> T listAdd(Class<R> clz, IRunApi<List<R>> api) {
        if (clz != null && api != null) {
            String name = get(List.class, clz).toString();
            List<IRunApi> data = apiMap.get(name);
            if (data == null) {
                apiMap.put(name, new ArrayList<>());
            }
            apiMap.get(name).add(api);
        }
        return getThis();
    }

    @Override
    public T replaceClzAddApiAll(Map<String, List<IRunApi>> map) {
        if (!TextUtils.isEmpty(map)) {
            this.apiMap = map;
        } else {
            this.apiMap.clear();
        }
        return getThis();
    }

    @Override
    public T getThis() {
        return (T) this;
    }

    //私有的

    private boolean check(Class clz) {
        return clz != null && !clz.isPrimitive() && !clz.isPrimitive() &&
                !Modifier.isAbstract(clz.getModifiers()) && !String.class.isAssignableFrom(clz);
    }

    private String getLog(R next) {
        String str = LogUtil.getLog(next);
        if (!TextUtils.isEmpty(str)) {
            try {
                str = URLDecoder.decode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                toError(HttpError.ERROR_STRING_ENCODE, "string解码异常", e);
            }
        }
        return str;
    }

    private void toError(int what, String msg, Throwable e) {
        onItem(new HttpError(what, msg, e), HttpError.class);
        e.printStackTrace();
    }

    private void onJson(String in) {
        try {
            if (!TextUtils.isEmpty(in)) {
                Object obj = new JSONTokener(in).nextValue();
                if (obj instanceof JSONObject) {
                    JSONObject jo = (JSONObject) obj;
                    if (jo.has("mode")) {
                        Object mode = jo.get("mode");
                        if (mode instanceof String) {
                            if (jo.has("list")) {
                                Class clz = getListClz((String) mode);
                                if (clz != null) {
                                    Type type = get(List.class, clz);
                                    onData(GsonDecodeUtil.decode(jo.get("list").toString(), type), type);
                                }
                            }
                            if (jo.has("data")) {
                                Class clz = getDataClz((String) mode);
                                if (clz != null) {
                                    onData(GsonDecodeUtil.decode(jo.get("data").toString(), clz), clz);
                                }
                            }
                        }
                    }
                }
            } else {
                toError(HttpError.ERROR_DATA_NULL, "json字符串不能为空", null);
            }
        } catch (Exception e) {
            toError(HttpError.ERROR_JSON_PARSE, "json解析异常", e);
        }
    }

    private void onData(Object obj, Type type) {
        if (obj != null) {
            Throwable e = null;
            String msg = null;
            Class clz = obj.getClass();
            if (obj instanceof List) {
                List data = (List) obj;
                if (type != null || !TextUtils.isEmpty(data)) {
                    type = type != null ? type : get(List.class, data.get(0).getClass());
                    onItem(obj, type);
                    if (!TextUtils.isEmpty(data)) {
                        for (int i = 0; i < data.size(); i++) {
                            Object item = data.get(i);
                            Class itemClz = item.getClass();
                            if (check(itemClz)) {
                                onData(item, itemClz);
                            }
                        }
                    }
                }
            } else if (check(clz)){
                onItem(obj, clz);
                Field[] fields = clz.getDeclaredFields();
                if (!TextUtils.isEmpty(fields)) {
                    for (Field field : fields) {
                        if (!Modifier.isStatic(field.getModifiers()) && check(field.getType())) {
                            String name = field.getName();
                            String getName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                            try {
                                onData(clz.getMethod(getName).invoke(obj), null);
                            } catch (Exception ignored) {
                                e = ignored;
                                msg = getName;
                            }
                        }
                    }
                }
            } else {
                onItem(obj, clz);
            }
            if (e != null) {
                toError(HttpError.ERROR_DATA_METHOD, String.format("%s()解析异常", msg), e);
            }
        }
    }

    private <RR> void onItem(RR item, Type type) {
        List<IRunApi> data = apiMap.get(type.toString());
        if (!TextUtils.isEmpty(data)) {
            for (IRunApi api : data) {
                if (api != null) {
                    api.execute(item);
                }
            }
        }
    }

}

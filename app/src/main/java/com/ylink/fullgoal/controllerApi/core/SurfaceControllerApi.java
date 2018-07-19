package com.ylink.fullgoal.controllerApi.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.bean.Completed;
import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiFragment;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.net.Exceptions;
import com.leo.core.util.SoftInputUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.util.ToastUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.full.FullSearchControllerApi;
import com.ylink.fullgoal.api.surface.LoadingDialogControllerApi;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.main.SurfaceActivity;
import com.ylink.fullgoal.vo.SearchVo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.count;
import static com.ylink.fullgoal.config.ComConfig.SHOW;
import static com.ylink.fullgoal.config.ComConfig.UPDATE;
import static com.ylink.fullgoal.config.Config.FIELDS;

@SuppressWarnings("ReturnInsideFinallyBlock")
public class SurfaceControllerApi<T extends SurfaceControllerApi, C> extends ControllerApi<T, C> {

    private LoadingDialogControllerApi dialogApi;

    private LoadingDialogControllerApi getDialogApi() {
        return dialogApi;
    }

    public SurfaceControllerApi(C controller) {
        super(controller);
    }

    public void showLoading() {
        if (!(check(getDialogApi()) && check(getDialogApi().getDialog())
                && getDialogApi().getDialog().isShowing())) {
            dialogApi = (LoadingDialogControllerApi) getDialogControllerApi(getActivity(),
                    LoadingDialogControllerApi.class).dialogShow();
        }
    }

    public void dismissLoading() {
        executeNon(getDialogApi(), CoreControllerApi::dismiss);
    }

    @Override
    public void initView() {
        super.initView();
        add(Completed.class, (path, what, msg, bean) -> dismissLoading());
        add(Exceptions.class, (path, what, msg, bean) -> dismissLoading());
        add(Exceptions.class, (path, what, msg, bean) -> {
            if (!TextUtils.isEmpty(bean.getMessage())) {
                ToastUtil.show(this, bean.getMessage());
            }
        });
    }

    @Override
    public void onCom(int what, String com, String msg, Object... args) {
        super.onCom(what, com, msg, args);
        executeNon(com, c -> {
            switch (c) {
                case UPDATE:
                    notifyDataChanged();
                    break;
                case SHOW:
                    show(msg);
                    break;
            }
        });
    }

    //自定义

    @Override
    public BaseControllerApiDialog getDialog() {
        return super.getDialog();
    }

    @SafeVarargs
    public final T startSurfaceActivity(Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, args);
        return getThis();
    }

    @SafeVarargs
    public final T startFinishSurfaceActivity(Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, args);
        return getThis();
    }


    @SafeVarargs
    public final T startSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    public final T startFinishSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    public final BaseControllerApiFragment getFragment(Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, args);
    }

    @SafeVarargs
    public final BaseControllerApiFragment getFragment(Bundle bundle, Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, bundle, args);
    }

    public <B extends IControllerApi> B getViewControllerApi(Class<B> clz, Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, layoutResId)
                .controllerApi();
    }

    public <B extends IControllerApi> B getViewControllerApi(Class<B> clz, View rootView) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, rootView)
                .controllerApi();
    }

    public <B extends IControllerApi> B getViewControllerApi(Class<B> clz) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz)
                .controllerApi();
    }

    public <B extends IControllerApi> B getViewControllerApi(IControllerApi api, Integer layoutResId) {
        return api == null ? null : (B) new BaseControllerApiView(getContext()).init(api, layoutResId)
                .controllerApi();
    }

    public <B extends IControllerApi> B getDialogControllerApi(Activity activity, Class<B> clz,
                                                               Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiDialog<>(getContext()).init(activity,
                clz, layoutResId).controllerApi();
    }

    public <B extends IControllerApi> B getDialogControllerApi(Activity activity, Class<B> clz) {
        return clz == null ? null : (B) new BaseControllerApiDialog<>(getContext()).init(activity,
                clz, null).controllerApi();
    }

    protected <B> B getBundle(Bundle bundle, Class<B> clz) {
        if (bundle != null && clz != null) {
            return decode(bundle.getString(clz.toString()), clz);
        }
        return null;
    }

    protected <B> List<B> getBundleList(Bundle bundle, Class<B> clz) {
        if (bundle != null && clz != null) {
            Type type = TypeToken.getParameterized(List.class, clz).getType();
            return decode(bundle.getString(type.toString()), type);
        }
        return null;
    }

    protected Bundle getBundle(Object... args) {
        if (!TextUtils.isEmpty(args)) {
            Bundle bundle = new Bundle();
            execute(args, obj -> bundle.putString(getObjectType(obj).toString(), encode(obj)));
            return bundle;
        }
        return null;
    }

    /**
     * 查询基础类
     *
     * @param clz    clz
     * @param action action
     * @param <B>    B
     */
    protected <B> void addList(Class<B> clz, IPathMsgAction<List<B>> action) {
        addList(DataFg.class, clz, action);
    }

    private void startSearch(Class<? extends IControllerApi> clz, String search, String key,
                             String value, ArrayList<String> filterData) {
        if (check(clz)) {
            SoftInputUtil.hidSoftInput(getRootView());
            Bundle bundle = new Bundle();
            bundle.putString(Config.SEARCH, search);
            bundle.putString(Config.KEY, key);
            bundle.putString(Config.VALUE, value);
            bundle.putStringArrayList(Config.FILTERS, filterData);
            startSurfaceActivity(bundle, clz);
        }
    }

    private void startSearch(String search, String key, ArrayList<String> filterData) {
        startSearch(FullSearchControllerApi.class, search, key, null, filterData);
    }

    protected void startSearch(String search, ArrayList<String> filterData) {
        startSearch(search, null, filterData);
    }

    protected void startSearch(String search, String key) {
        startSearch(search, key, null);
    }

    protected void startSearch(String search) {
        startSearch(search, null, null);
    }

    protected void startSearch(Class<? extends IControllerApi> clz, String search, String key) {
        startSearch(clz, search, key, null, null);
    }

    protected void startSearch(Class<? extends IControllerApi> clz, String search, String key,
                               String value) {
        startSearch(clz, search, key, value, null);
    }

    public int getResTvColor(CharSequence text) {
        return !TextUtils.isEmpty(text) ? R.color.tv : R.color.tv1;
    }

    @SuppressLint("ResourceAsColor")
    protected T setTextView(TextView tv, String name, String hint) {
        if (TextUtils.isEmpty(name)) {
            setText(tv, hint).setTextColor(tv, R.color.tv1);
        } else {
            setText(tv, name).setTextColor(tv, R.color.tv);
        }
        return getThis();
    }

    protected Map<String, Object> getCheck(Object obj, Set<String> must, Set<String> all) {
        if (!TextUtils.isEmpty(all) && obj != null) {
            Map<String, Object> mp = new HashMap<>();
            Class clz = obj.getClass();
            for (String key : all) {
                Object item = null;
                try {
                    Field field = clz.getDeclaredField(key);
                    field.setAccessible(true);
                    item = field.get(obj);
                } catch (Exception ignored) {
                } finally {
                    if (count(item) > 0) {
                        mp.put(key, item);
                    } else if (!TextUtils.isEmpty(must) && must.contains(getValue(key))) {
                        String value = getValue(key);
                        value = TextUtils.isEmpty(value) ? key : value;
                        show(String.format("%s不能为空", value));
                        return null;
                    }
                }
            }
            return mp;
        }
        return null;
    }

    protected Map<String, Object> getCheck(Object obj, Set<String> must) {
        Set<String> all = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(FIELDS)) {
            for (String[] args : FIELDS) {
                if (count(args) == 2 && !TextUtils.isEmpty(args[0])) {
                    all.add(args[0]);
                }
            }
        }
        return getCheck(obj, must, all);
    }

    protected Map<String, Object> getCheckMap(Map<String, Object> map, Set<String> must) {
        if (!TextUtils.isEmpty(map) && !TextUtils.isEmpty(must)) {
            Set<String> keys = map.keySet();
            for (String key : must) {
                if (!TextUtils.isEmpty(key) && !keys.contains(getKey(key))) {
                    show(String.format("%s(%s)不能为空", key, getKey(key)));
                    return null;
                }
            }
        }
        return map;
    }

    protected Map<String, Object> getCheckMap(Object obj, Set<String> must) {
        if (obj != null) {
            return getCheckMap(getFieldMap(obj), must);
        }
        return null;
    }

    /**
     * 获取value
     *
     * @param value value
     * @return key
     */
    protected String getKey(String[][] argss, String value) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(argss)) {
            for (String[] args : argss) {
                if (count(args) == 2 && TextUtils.equals(args[1], value)) {
                    return args[0];
                }
            }
        }
        return null;
    }

    /**
     * 获取key
     *
     * @param key key
     * @return value
     */
    protected String getValue(String[][] argss, String key, String def) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(argss)) {
            for (String[] args : argss) {
                if (TextUtils.count(args) == 2 && TextUtils.equals(args[0], key)) {
                    return args[1];
                }
            }
        }
        return def;
    }


    protected String getKey(String value) {
        return getKey(FIELDS, value);
    }

    protected String getValue(String key) {
        return getValue(FIELDS, key, null);
    }

    protected String getValue(String[][] argss, String key) {
        return getValue(argss, key, null);
    }

    protected <A> void executeSearch(Class<A> clz, IObjAction<SearchVo<A>> action) {
        if (clz != null && action != null) {
            TypeToken<SearchVo<A>> token = (TypeToken<SearchVo<A>>) TypeToken
                    .getParameterized(SearchVo.class, clz);
            execute(getFinish(), token, action);
        }
    }

    protected Type getObjectType(Object obj) {
        if (obj instanceof List) {
            List data = (List) obj;
            if (!TextUtils.isEmpty(data)) {
                for (Object item : data) {
                    if (item != null) {
                        return TypeToken.getParameterized(List.class,
                                getObjectType(item)).getType();
                    }
                }
            }
            return List.class;
        } else if (obj != null) {
            return obj.getClass();
        }
        return null;
    }

    public Map<String, String> getFieldStringMap(Object obj) {
        if (obj != null) {
            Map<String, String> map = new HashMap<>();
            initFieldMap(obj, (key, value) -> map.put(key, getLog(value)));
            return map;
        }
        return null;
    }

    public Map<String, Object> getFieldMap(Object obj) {
        if (obj != null) {
            Map<String, Object> map = new HashMap<>();
            initFieldMap(obj, map::put);
            return map;
        }
        return null;
    }

    public void initFieldMap(Object obj, IMapAction<String, Object> action) {
        if (obj != null && action != null) {
            Set<Field> set = getFields(obj);
            if (!TextUtils.isEmpty(set)) {
                for (Field field : set) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        if (value != null) {
                            action.execute(field.getName(), value);
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
        }
    }

    public Set<Field> getFields(Object obj) {
        if (obj != null) {
            Set<Field> set = new LinkedHashSet<>();
            Class clz = obj.getClass();
            while (clz != Object.class) {
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    if (checkField(field)) {
                        set.add(field);
                    }
                }
                clz = clz.getSuperclass();
            }
            return set;
        }
        return null;
    }

    private boolean checkField(Field field) {
        if (field != null) {
            int m = field.getModifiers();
            return field != null && !TextUtils.isEmpty(field.getName())
                    && !Modifier.isAbstract(m) && !Modifier.isFinal(m)
                    && !Modifier.isTransient(m) && !Modifier.isStatic(m)
                    && !Modifier.isInterface(field.getType().getModifiers());
        }
        return false;
    }

    protected <D> D get(List<D> data) {
        if (!TextUtils.isEmpty(data)) {
            return data.get(0);
        }
        return null;
    }

    protected String getNull(String text) {
        return TextUtils.isEmpty(text) ? null : text;
    }

    //有关于数据处理的

    protected <S, A extends IController, B> B vorv(IReturnAction<S, A> action) {
        A obj = vor(action);
        if (check(obj)) {
            return (B) obj.getViewBean();
        }
        return null;
    }

    protected <S, A extends IController, B> B vord(IReturnAction<S, A> action) {
        A obj = vor(action);
        if (check(obj)) {
            return (B) obj.getDB();
        }
        return null;
    }

}
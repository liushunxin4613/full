package com.ylink.fullgoal.controllerApi.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.main.CoreControllerApi;
import com.leo.core.bean.DataEmpty;
import com.leo.core.bean.ParseCompleted;
import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.helper.TimeFactory;
import com.leo.core.iapi.api.IDisplayApi;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.iapi.inter.IController;
import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IPathMsgAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.iapi.inter.OnBVDialogClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.net.Exceptions;
import com.leo.core.other.MMap;
import com.leo.core.util.DisneyUtil;
import com.leo.core.util.TextUtils;
import com.leo.core.util.ToastUtil;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.LoadingDialogControllerApi;
import com.ylink.fullgoal.config.UrlConfig;
import com.ylink.fullgoal.controllerApi.surface.ContentControllerApi;
import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;
import com.ylink.fullgoal.fg.DataFg;
import com.ylink.fullgoal.norm.HintDialogNorm;
import com.ylink.fullgoal.vo.RVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.count;
import static com.ylink.fullgoal.config.ComConfig.SHOW;
import static com.ylink.fullgoal.config.ComConfig.SHOW_LOADING_NO;
import static com.ylink.fullgoal.config.ComConfig.SHOW_LOADING_YES;
import static com.ylink.fullgoal.config.ComConfig.UPDATE;
import static com.ylink.fullgoal.config.Config.FIELDS;
import static com.ylink.fullgoal.config.UrlConfig.FULL_REIMBURSE_QUERY;
import static com.ylink.fullgoal.config.UrlConfig.PATH_QUERY_MESSAGE_BACK_DATA;

@SuppressWarnings("ReturnInsideFinallyBlock")
public class SurfaceControllerApi<T extends SurfaceControllerApi, C> extends ControllerApi<T, C> {

    private LoadingDialogControllerApi dialogApi;

    private void setDialogApi(LoadingDialogControllerApi api) {
        this.dialogApi = api;
    }

    private LoadingDialogControllerApi getDialogApi() {
        return dialogApi;
    }

    private TimeFactory getTimeFactory() {
        return TimeFactory.getInstance();
    }

    protected boolean isNoDialogShowing() {
        return !no(vr(getDialogApi(), SurfaceControllerApi::getDialog, Dialog::isShowing), false);
    }

    public SurfaceControllerApi(C controller) {
        super(controller);
    }

    protected boolean isLoading() {
        return true;
    }

    public void showLoading() {
        if (isLoading()) {
            if (!(check(getDialogApi()) && check(getDialogApi().getDialog())
                    && getDialogApi().getDialog().isShowing())) {
                setDialogApi((LoadingDialogControllerApi) getDialogControllerApi(getActivity(),
                        LoadingDialogControllerApi.class).dialogShow());
                if (this instanceof ContentControllerApi) {
                    ((ContentControllerApi) this).hideViews();
                }
                getTimeFactory().start();
            }
        }
    }

    public void dismissLoading() {
        if (isLoading()) {
            getTimeFactory().check(100, () -> {
                executeNon(getDialogApi(), CoreControllerApi::dismiss);
                if (this instanceof ContentControllerApi) {
                    ((ContentControllerApi) this).renewViews();
                }
            });
        }
    }

    @Override
    public void initAddAction() {
        super.initAddAction();
        add(Exceptions.class, (type, baseUrl, path, map, what, msg, field, bean) -> {
            checkView(what, path, field, () -> {
                if (this instanceof ContentControllerApi) {
                    if (TextUtils.check(path)) {
                        switch (path) {
                            case FULL_REIMBURSE_QUERY://报销请求
                                ((ContentControllerApi) this).showErrorView();
                                break;
                            default:
                                ((ContentControllerApi) this).showContentView();
                                break;
                        }
                    }
                }
            });
            dismissLoading();
        });
        add(Exceptions.class, (type, baseUrl, path, map, what, msg, field, bean) -> {
            if (!TextUtils.isEmpty(bean.getMessage())) {
                ToastUtil.show(this, bean.getMessage());
            }
        });
        add(DataEmpty.class, (type, baseUrl, path, map, what, msg, field, bean) -> checkView(what, path, field, () -> {
            if (this instanceof ContentControllerApi) {
                ((ContentControllerApi) this).showNullView(true);
            }
            dismissLoading();
        }));
        add(ParseCompleted.class, (type, baseUrl, path, map, what, msg, field, bean) -> checkView(what, path, field, () -> {
            if (!TextUtils.isEmpty(path)) {
                switch (path) {
                    case PATH_QUERY_MESSAGE_BACK_DATA://报销确认
                        if (!bean.contains(RVo.class)) {
                            if (this instanceof ContentControllerApi) {
                                ((ContentControllerApi) this).showErrorView();
                            }
                            dismissLoading();
                            show("数据解析异常");
                        }
                        break;
                }
            }
        }));
        add(DataFg.class, (type, baseUrl, path, map, what, msg, field, bean) -> {
            if (!bean.isSuccess()) {
                ToastUtil.show(this, bean.getMessage());
            } else {
                if (this instanceof RecycleControllerApi) {
                    List<? extends IModel> data = getOnDataFg(type, baseUrl, path, map, what,
                            msg, field, bean);
                    if (!TextUtils.isEmpty(data)) {
                        ((RecycleControllerApi) this).initActionData(path, map, data);
                    }
                    return;
                }
            }
            dismissLoading();
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

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz, Integer layoutResId) {
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

    protected <B extends IControllerApi> B getDialogControllerApi(Activity activity, Class<B> clz,
                                                                  Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiDialog<>(getContext()).init(activity,
                clz, layoutResId).controllerApi();
    }

    protected <B extends IControllerApi> B getDialogControllerApi(Activity activity, Class<B> clz) {
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

    protected void dialog(String detail, String confirm, String cancel,
                          OnBVDialogClickListener<HintDialogNorm> confirmListener,
                          OnBVDialogClickListener<HintDialogNorm> cancelListener) {
        if (TextUtils.check(detail, confirm, confirmListener)) {
            HintDialogNorm norm = new HintDialogNorm("温馨提示", detail, confirm,
                    cancel, confirmListener, cancelListener);
            IControllerApi api = getDialogControllerApi(getActivity(),
                    norm.getControllerApiClass());
            api.dialogShow().onNorm(norm, 0);
            Window window = api.getDialog().getWindow();
            if (window != null) {
                window.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams lp = window.getAttributes();
                IDisplayApi.ScreenDisplay display = DisneyUtil.getScreenDisplay();
                lp.width = (int) (display.getX() * 0.8);
                window.setAttributes(lp);
            }
        }
    }

    protected List<? extends IModel> getOnDataFg(String type, String baseUrl, String path, Map<String, String> map, int what,
                                                 String msg, String field, DataFg fg) {
        return null;
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

    private void checkView(int what, String path, String fieldName, IAction action) {
        if (action != null) {
            boolean execute = false;
            switch (what) {
                case SHOW_LOADING_YES:
                    execute = true;
                    break;
                case SHOW_LOADING_NO:
                    break;
                default:
                    if (TextUtils.getListData(UrlConfig.LOADING_DIALOGS).contains(path)) {
                        execute = true;
                    }
                    break;
            }
            if (TextUtils.check(path)) {
                switch (path) {
                    case "Dimension_information.action":
                        if (!TextUtils.equals(fieldName, "dimenList")) {
                            execute = false;
                        }
                        break;
                }
            }
            if (execute) {
                execute(action);
            }
        }
    }

    private Map<String, Object> getCheck(Object obj, Set<String> must, Set<String> all) {
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
                    if (TextUtils.equals(getKey(key), "serialNo")) {
                        show("票据不能为空");
                    } else {
                        show(String.format("%s不能为空", key));
                    }
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
    protected String getKey(String[][] argss, String value, String def) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(argss)) {
            for (String[] args : argss) {
                if (count(args) == 2 && TextUtils.equals(args[1], value)) {
                    return args[0];
                }
            }
        }
        return def;
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
        return getKey(FIELDS, value, null);
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

    public Map<String, String> getFieldStringMap(Object obj) {
        if (obj != null) {
            Map<String, String> map = new HashMap<>();
            initFieldMap(obj, (key, value) -> map.put(key, getLog(value)));
            return map;
        }
        return null;
    }

    private Map<String, Object> getFieldMap(Object obj) {
        if (obj != null) {
            Map<String, Object> map = new HashMap<>();
            initFieldMap(obj, map::put);
            return map;
        }
        return null;
    }

    private void initFieldMap(Object obj, IMapAction<String, Object> action) {
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

    private Set<Field> getFields(Object obj) {
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
            return !TextUtils.isEmpty(field.getName())
                    && !Modifier.isAbstract(m)
                    && !Modifier.isFinal(m)
                    && !Modifier.isTransient(m)
                    && !Modifier.isStatic(m)
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

    protected <S, A extends IController> String vorc(IReturnAction<S, A> action) {
        A obj = vor(action);
        if (check(obj)) {
            return obj.getApiCode();
        }
        return null;
    }

    protected void ee(String name, IObjAction<MMap<String, Object>> action) {
        ee(name, map(action));
    }

}
package com.ylink.fullgoal.controllerApi.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiFragment;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.SearchControllerApi;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.main.SurfaceActivity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static com.leo.core.util.TextUtils.count;
import static com.ylink.fullgoal.config.Config.FIELDS;

@SuppressWarnings("ReturnInsideFinallyBlock")
public class SurfaceControllerApi<T extends SurfaceControllerApi, C> extends ControllerApi<T, C> {

    public SurfaceControllerApi(C controller) {
        super(controller);
    }

    //自定义

    @SafeVarargs
    protected final T startSurfaceActivity(Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, args);
        return getThis();
    }

    @SafeVarargs
    protected final T startFinishSurfaceActivity(Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, args);
        return getThis();
    }


    @SafeVarargs
    protected final T startSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    protected final T startFinishSurfaceActivity(Bundle bundle, Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, bundle, args);
        return getThis();
    }

    @SafeVarargs
    protected final BaseControllerApiFragment getFragment(Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, args);
    }

    @SafeVarargs
    protected final BaseControllerApiFragment getFragment(Bundle bundle, Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, bundle, args);
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz, Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz, View rootView) {
        return clz == null ? null : (B) new BaseControllerApiView(getContext()).init(clz, rootView).controllerApi();
    }

    protected <B extends IControllerApi> B getViewControllerApi(Class<B> clz) {
        return getViewControllerApi(clz, (Integer) null);
    }

    protected <B extends IControllerApi> B getDialogControllerApi(Class<B> clz, Integer layoutResId) {
        return clz == null ? null : (B) new BaseControllerApiDialog<>(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <B extends IControllerApi> B getDialogControllerApi(Class<B> clz) {
        return getDialogControllerApi(clz, null);
    }

    protected <B> B getBundle(Bundle bundle, Class<B> clz) {
        if (bundle != null && clz != null) {
            return decode(bundle.getString(clz.getName()), clz);
        }
        return null;
    }

    protected Bundle getBundle(Object... args) {
        if (!TextUtils.isEmpty(args)) {
            Bundle bundle = new Bundle();
            execute(args, obj -> bundle.putString(obj.getClass().getName(), encode(obj)));
            return bundle;
        }
        return null;
    }

    protected void startSearch(String search) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.SEARCH, search);
        startSurfaceActivity(bundle, SearchControllerApi.class);
    }

    protected int getResTvColor(CharSequence text) {
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

    protected Map<String, String> getCheck(Object obj, Set<String> must, Set<String> all) {
        if (!TextUtils.isEmpty(all) && obj != null) {
            Map<String, String> mp = new HashMap<>();
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
                        mp.put(key, getLog(item));
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

    protected Map<String, String> getCheck(Object obj, Set<String> must) {
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

    protected String getKey(String value) {
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(FIELDS)) {
            for (String[] args : FIELDS) {
                if (count(args) == 2 && TextUtils.equals(args[1], value)) {
                    return args[0];
                }
            }
        }
        return null;
    }

    protected String getValue(String key) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(FIELDS)) {
            for (String[] args : FIELDS) {
                if (count(args) == 2 && TextUtils.equals(args[0], key)) {
                    return args[1];
                }
            }
        }
        return null;
    }

}
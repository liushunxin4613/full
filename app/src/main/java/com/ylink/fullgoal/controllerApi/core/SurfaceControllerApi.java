package com.ylink.fullgoal.controllerApi.core;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiFragment;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ResUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.SearchControllerApi;
import com.ylink.fullgoal.config.Config;
import com.ylink.fullgoal.main.SurfaceActivity;

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

    protected <R extends IControllerApi> R getViewControllerApi(Class<R> clz, Integer layoutResId) {
        return clz == null ? null : (R) new BaseControllerApiView(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <R extends IControllerApi> R getViewControllerApi(Class<R> clz, View rootView) {
        return clz == null ? null : (R) new BaseControllerApiView(getContext()).init(clz, rootView).controllerApi();
    }

    protected <R extends IControllerApi> R getViewControllerApi(Class<R> clz) {
        return getViewControllerApi(clz, (Integer) null);
    }

    protected <R extends IControllerApi> R getDialogControllerApi(Class<R> clz, Integer layoutResId) {
        return clz == null ? null : (R) new BaseControllerApiDialog<>(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <R extends IControllerApi> R getDialogControllerApi(Class<R> clz) {
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
    protected T setTextView(TextView tv, String name, String hint){
        if(TextUtils.isEmpty(name)){
            setText(tv, hint).setTextColor(tv, R.color.tv1);
        } else {
            setText(tv, name).setTextColor(tv, R.color.tv);
        }
        return getThis();
    }

}
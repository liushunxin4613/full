package com.ylink.fullgoal.controllerApi.core;

import android.os.Bundle;

import com.leo.core.core.BaseControllerApiDialog;
import com.leo.core.core.BaseControllerApiFragment;
import com.leo.core.core.BaseControllerApiView;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.main.SurfaceActivity;

public class SurfaceControllerApi<T extends SurfaceControllerApi, C> extends ControllerApi<T, C> {

    public SurfaceControllerApi(C controller) {
        super(controller);
    }

    //自定义
    protected T startSurfaceActivity(Class<? extends IControllerApi>... args) {
        startActivity(SurfaceActivity.class, args);
        return getThis();
    }

    protected T startFinishSurfaceActivity(Class<? extends IControllerApi>... args) {
        startFinishActivity(SurfaceActivity.class, args);
        return getThis();
    }

    protected BaseControllerApiFragment getFragment(Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, args);
    }

    protected BaseControllerApiFragment getFragment(Bundle bundle, Class<? extends IControllerApi>... args) {
        return (BaseControllerApiFragment) getFragment(BaseControllerApiFragment.class, bundle, args);
    }

    protected <R extends IControllerApi> R getViewControllerApi(Class<R> clz, Integer layoutResId){
        return clz == null ? null : (R) new BaseControllerApiView(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <R extends IControllerApi> R getViewControllerApi(Class<R> clz){
        return getViewControllerApi(clz, null);
    }

    protected <R extends IControllerApi> R getDialogControllerApi(Class<R> clz, Integer layoutResId){
        return clz == null ? null : (R) new BaseControllerApiDialog<>(getContext()).init(clz, layoutResId).controllerApi();
    }

    protected <R extends IControllerApi> R getDialogControllerApi(Class<R> clz){
        return getDialogControllerApi(clz, null);
    }

}
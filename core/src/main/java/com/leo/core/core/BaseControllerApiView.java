package com.leo.core.core;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

public class BaseControllerApiView<T extends BaseControllerApiView, C extends IControllerApi> extends FrameLayout implements IAFVApi<T, C> {

    private Bundle savedInstanceState;//数据存储
    private IControllerApi controllerApi;
    private Class<? extends IControllerApi> viewControllerApiClz;

    public BaseControllerApiView(Class<? extends IControllerApi> viewControllerApiClz, Context context) {
        super(context);
        init(viewControllerApiClz, context, null);
    }

    public BaseControllerApiView(Class<? extends IControllerApi> viewControllerApiClz, Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(viewControllerApiClz, context, attrs);
    }

    @Override
    public IControllerApi<C, T> controllerApi() {
        if(controllerApi == null){
            if(viewControllerApiClz != null){
                controllerApi = (IControllerApi) ObjectUtil.getObject(viewControllerApiClz, Object.class, this);
            } else {
                controllerApi = newControllerApi();
            }
            if(controllerApi == null){
                throw new NullPointerException("newControllerApi 不能为空");
            }
        }
        return controllerApi;
    }

    @Override
    public IControllerApi<C, T> newControllerApi(){
        return new BaseControllerApi(this);
    }

    /**
     *  自定义初始化
     * @param context context
     * @param attrs attrs
     */
    protected void init(Class<? extends IControllerApi> viewControllerApiClz, Context context, @Nullable AttributeSet attrs){
        this.viewControllerApiClz = viewControllerApiClz;
        if(controllerApi() != null){
            savedInstanceState = new Bundle();
            controllerApi().onCreateView(LayoutInflater.from(context), this, savedInstanceState);
            if(controllerApi().getStyleableRes() != null){
                TypedArray a = getContext().obtainStyledAttributes(attrs, controllerApi().getStyleableRes());
                try {
                    controllerApi().initStyleable(a);
                } finally {
                    a.recycle();
                }
            }
            controllerApi().onViewCreated(this, savedInstanceState);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (controllerApi() != null && controllerApi().onKeyDown(keyCode, event)) || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        execute(controllerApi(), api -> api.onConfigurationChanged(newConfig));
    }

    protected <R> void execute(R obj, IRunApi<R> api){
        RunUtil.executeNon(obj, api);
    }

}

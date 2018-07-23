package com.leo.core.core;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.leo.core.R;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.LogUtil;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

public class BaseControllerApiView<T extends BaseControllerApiView, C extends IControllerApi> extends LinearLayout implements IAFVApi<T, C> {

    private AttributeSet attrs;
    private IControllerApi controllerApi;
    private Class<? extends IControllerApi> apiClz;

    public BaseControllerApiView(Context context) {
        super(context);
    }

    public BaseControllerApiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        init(attrs);
    }

    @Override
    public IControllerApi<C, T> controllerApi() {
        if (controllerApi == null) {
            setControllerApi(newControllerApi());
        }
        return controllerApi;
    }

    @Override
    public void setControllerApi(IControllerApi<C, T> api) {
        if (apiClz != null) {
            controllerApi = (IControllerApi) ObjectUtil.getObject(apiClz, Object.class, this);
        } else {
            controllerApi = api;
        }
        if (controllerApi == null) {
            throw new NullPointerException("newControllerApi 不能为空");
        }
        controllerApi.setRootContainer(this);
    }

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return new BaseControllerApi(this);
    }

    private void init(AttributeSet attrs) {
        try {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseControllerApiView);
            try {
                String className = a.getString(R.styleable.BaseControllerApiView_api);
                Integer layoutResId = a.getResourceId(R.styleable.BaseControllerApiView_layout, 0);
                layoutResId = layoutResId == 0 ? null : layoutResId;
                init(ObjectUtil.getClass(className), layoutResId);
            } finally {
                a.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initControllerApi() {
        if (controllerApi() != null) {
            Bundle savedInstanceState = new Bundle();
            controllerApi().onCreateView(LayoutInflater.from(getContext()), this,
                    savedInstanceState);
            if (attrs != null && controllerApi().getStyleableRes() != null) {
                TypedArray a = getContext().obtainStyledAttributes(attrs, controllerApi()
                        .getStyleableRes());
                try {
                    controllerApi().initStyleable(a);
                } finally {
                    a.recycle();
                }
            }
            controllerApi().onViewCreated(this, savedInstanceState);
        }
    }

    /**
     * 自定义初始化
     */
    public T init(Class<? extends IControllerApi> clz) {
        this.apiClz = clz;
        initControllerApi();
        return (T) this;
    }

    /**
     * 自定义初始化
     *
     * @param clz         clz
     * @param layoutResId layoutResId
     */
    public T init(Class<? extends IControllerApi> clz, Integer layoutResId) {
        this.apiClz = clz;
        controllerApi().setRootViewResId(layoutResId);
        initControllerApi();
        return (T) this;
    }

    /**
     * 自定义初始化
     *
     * @param clz      clz
     * @param rootView rootView
     */
    public T init(Class<? extends IControllerApi> clz, View rootView) {
        this.apiClz = clz;
        controllerApi().setRootView(rootView);
        initControllerApi();
        return (T) this;
    }

    public T init(IControllerApi controllerApi, Integer layoutResId) {
        setControllerApi(controllerApi);
        if (controllerApi() != null) {
            controllerApi().setRootViewResId(layoutResId);
            controllerApi().initController((T) this);
        }
        initControllerApi();
        return (T) this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (controllerApi() != null && controllerApi().onKeyDown(keyCode, event))
                || super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        execute(controllerApi(), api -> api.onConfigurationChanged(newConfig));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        execute(controllerApi(), api -> api.onMeasure(widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        try {
            super.onLayout(changed, left, top, right, bottom);
        } catch (Exception e) {
            LogUtil.ee(this, "controllerApi: " + controllerApi().getClass().getName());
            e.printStackTrace();
        }
        execute(controllerApi(), api -> api.onLayout(changed, left, top, right, bottom));
    }

    protected <B> void execute(B obj, IObjAction<B> api) {
        RunUtil.executeNon(obj, api);
    }

}
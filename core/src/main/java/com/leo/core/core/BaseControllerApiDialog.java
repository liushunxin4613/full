package com.leo.core.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;

import com.leo.core.R;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

public class BaseControllerApiDialog<T extends BaseControllerApiDialog, C extends IControllerApi> extends AlertDialog implements IAFVApi<T, C> {

    private IControllerApi controllerApi;
    private Class<? extends IControllerApi> apiClz;

    public BaseControllerApiDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
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
        controllerApi.setRootContainer(getWindow()
                .getDecorView().findViewById(android.R.id.content));
    }

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return new BaseControllerApi(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        execute(controllerApi(), api -> api.onCreate(savedInstanceState));
    }

    /**
     * 自定义初始化
     *
     * @param activity    activity
     * @param clz         clz
     * @param layoutResId layoutResId
     */
    public T init(Activity activity, Class<? extends IControllerApi> clz, Integer layoutResId) {
        this.apiClz = clz;
        if (controllerApi() != null) {
            controllerApi().setActivity(activity).setRootViewResId(layoutResId);
        }
        return (T) this;
    }

    protected <B> void execute(B obj, IObjAction<B> api) {
        RunUtil.executeNon(obj, api);
    }

}

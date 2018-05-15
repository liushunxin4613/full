package com.leo.core.core;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.leo.core.R;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.main.IAFVApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.ObjectUtil;
import com.leo.core.util.RunUtil;

public class BaseControllerApiDialog<T extends BaseControllerApiDialog, C extends IControllerApi> extends Dialog implements IAFVApi<T, C> {

    private IControllerApi controllerApi;
    private Class<? extends IControllerApi> apiClz;

    public BaseControllerApiDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    public IControllerApi<C, T> controllerApi() {
        if(controllerApi == null){
            if(apiClz != null){
                controllerApi = (IControllerApi) ObjectUtil.getObject(apiClz, Object.class, this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        execute(controllerApi(), api -> api.onCreate(savedInstanceState));
    }

    /**
     *  自定义初始化
     * @param clz clz
     * @param layoutResId layoutResId
     */
    public T init(Class<? extends IControllerApi> clz, Integer layoutResId){
        this.apiClz = clz;
        if(controllerApi() != null){
            controllerApi().setRootViewResId(layoutResId);
        }
        return (T) this;
    }

    protected <B> void execute(B obj, IRunApi<B> api){
        RunUtil.executeNon(obj, api);
    }

}

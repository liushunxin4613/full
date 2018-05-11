package com.ylink.fullgoal.controllerApi.surface;

import android.view.ViewGroup;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.IBindItemCallback;
import com.leo.core.iapi.IRunApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.HashMap;
import java.util.Map;

public class BaseItemControllerApi<T extends BaseItemControllerApi, C> extends SurfaceControllerApi<T, C> {

    private Map<Class<? extends BaseApiBean>, IBindItemCallback> map;

    public BaseItemControllerApi(C controller) {
        super(controller);
        map = new HashMap<>();
    }

    private T addView(IRunApi<ViewGroup> api) {
        if (api != null) {
            api.execute((ViewGroup) getRootView());
        }
        return getThis();
    }

    protected String getEnd(double m) {
        String str = formatString("%.2f", m);
        if (!TextUtils.isEmpty(str) && str.contains(".")) {
            return str.substring(str.indexOf(".") + 1);
        }
        return null;
    }

    protected String getDm(double dm) {
        if (dm % 1 == 0) {
            return formatString("%d", (int) dm);
        } else {
            return formatString("%.2f", dm);
        }
    }

    protected T clearItemApi() {
        map.clear();
        return getThis();
    }

    protected <B extends BaseApiBean> void putBindItemCallback(Class<B> clz, IBindItemCallback<T, B> api) {
        if (clz != null && api != null) {
            map.put(clz, api);
        }
    }

    protected <B extends BaseApiBean> IBindItemCallback<T, B> getItemControllerApi(Class<B> clz){
        return clz == null ? null : map.get(clz);
    }

}

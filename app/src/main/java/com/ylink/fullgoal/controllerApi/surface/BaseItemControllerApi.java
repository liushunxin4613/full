package com.ylink.fullgoal.controllerApi.surface;

import android.view.ViewGroup;

import com.leo.core.iapi.IRunApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class BaseItemControllerApi<T extends BaseItemControllerApi, C> extends SurfaceControllerApi<T, C> {

    public BaseItemControllerApi(C controller) {
        super(controller);
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

}

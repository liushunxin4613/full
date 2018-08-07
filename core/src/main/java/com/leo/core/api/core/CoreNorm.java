package com.leo.core.api.core;

import android.view.View;

import com.leo.core.core.Norm;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;

public abstract class CoreNorm<T extends CoreNorm, P extends IControllerApi> extends Norm<T, P> {

    protected abstract IControllerApi createControllerApi(P controllerApi, Object controller);

    @Override
    public void initControllerApi() {
        super.initControllerApi();
        if (controllerApi() == null) {
            setControllerApi(createControllerApi(parentControllerApi(), getController()));
        }
    }

    protected View.OnClickListener getOnBVClickListener(OnBVClickListener<T> bvListener) {
        return bvListener == null ? null : v -> bvListener.onBVClick(getThis(), v);
    }

}
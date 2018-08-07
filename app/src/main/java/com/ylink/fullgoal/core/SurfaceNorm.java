package com.ylink.fullgoal.core;

import android.view.View;

import com.leo.core.api.core.CoreNorm;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public abstract class SurfaceNorm<T extends SurfaceNorm, P extends SurfaceControllerApi> extends CoreNorm<T, P> {

    private transient View.OnClickListener onClickListener;

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

}
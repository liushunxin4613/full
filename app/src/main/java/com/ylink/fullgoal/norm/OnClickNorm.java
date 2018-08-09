package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public abstract class OnClickNorm<T extends OnClickNorm, P extends SurfaceControllerApi> extends SurfaceNorm<T, P> {

    private transient View.OnClickListener onClickListener;

    public OnClickNorm() {
    }

    public OnClickNorm(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

}
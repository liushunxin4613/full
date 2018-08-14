package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public abstract class OnClickNorm<T extends OnClickNorm, P extends SurfaceControllerApi> extends SurfaceNorm<T, P> {

    private transient View.OnClickListener onClickListener;

    OnClickNorm() {
    }

    OnClickNorm(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnBVClickListener<T> listener) {
        this.onClickListener = getOnBVClickListener(listener);
    }

    public boolean isSelected() {
        return !TextUtils.isEmpty(getApiCode())
                && TextUtils.equals(getApiCode(),
                getSelectedApiCode());
    }

}
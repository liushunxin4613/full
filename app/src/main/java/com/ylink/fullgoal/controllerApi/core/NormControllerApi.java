package com.ylink.fullgoal.controllerApi.core;

import android.support.annotation.NonNull;

import com.leo.core.iapi.core.INorm;

public abstract class NormControllerApi<T extends NormControllerApi, C, N extends INorm> extends SurfaceControllerApi<T, C> {

    public NormControllerApi(C controller) {
        super(controller);
    }

    @Override
    public void onNorm(INorm norm, int position) {
        super.onNorm(norm, position);
        if(norm != null){
            norm.setPosition(position);
            try {
                onSafeNorm((N) norm, position);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    protected abstract void onSafeNorm(@NonNull N norm, int position);

}
package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;

import com.ylink.fullgoal.controllerApi.core.NormControllerApi;
import com.ylink.fullgoal.norm.OnClickNorm;

public class OnClickControllerApi<T extends OnClickControllerApi, C, N extends OnClickNorm> extends
        NormControllerApi<T, C, N> {

    public OnClickControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected void onSafeNorm(@NonNull N norm, int position) {
        setOnClickListener(norm.getOnClickListener());
    }

}

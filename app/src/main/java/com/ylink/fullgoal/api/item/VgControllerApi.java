package com.ylink.fullgoal.api.item;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.norm.VgNorm;

import butterknife.Bind;

public class VgControllerApi<T extends VgControllerApi, C, N extends VgNorm> extends OnClickControllerApi<T, C, N> {

    @Bind(R.id.vg)
    ViewGroup vg;

    public VgControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected void onSafeNorm(@NonNull N norm, int position) {
        super.onSafeNorm(norm, position);
        setViewGroupApi(vg, vg -> {
            vg.removeAllViews();
            for (int i = 0; i < norm.getCount(); i++) {
                INorm item = norm.getChildAt(i);
                item.initControllerApi();
                IControllerApi controllerApi = getViewControllerApi(
                        item.controllerApi(), item.getApiType());
                controllerApi.onNorm(item, i);
                vg.addView(controllerApi.getRootView());
                if (norm.getLineLayoutResId() != null && i < norm.getCount() - 1) {
                    LayoutInflater.from(getContext())
                            .inflate(norm.getLineLayoutResId(), vg);
                }
            }
        });
    }

}

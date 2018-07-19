package com.ylink.fullgoal.core;

import android.view.ViewGroup;

import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.List;

public abstract class BaseVgBi<T extends BaseVgBi, B> extends SurfaceBi<T, B> {

    protected abstract ViewGroup getVg();

    protected abstract List<BaseBiBean> getData(B bean);

    @Override
    public void onBindApi(SurfaceControllerApi api, B bean) {
        super.onBindApi(api, bean);
        api.setViewGroupApi(getVg(), vg -> {
            vg.removeAllViews();
            executePos(getData(bean), (item, position)
                    -> vg.addView(api.getViewControllerApi(
                    item.getControllerApi(api), item.getApiType())
                    .onBindViewHolder(item, position)
                    .getRootView()));
        });
    }

}
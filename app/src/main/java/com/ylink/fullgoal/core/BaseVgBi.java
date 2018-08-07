package com.ylink.fullgoal.core;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.List;

public abstract class BaseVgBi<T extends BaseVgBi, B> extends SurfaceBi<T, B> {

    protected abstract ViewGroup getVg();

    protected abstract List<BaseBiBean> getData(B bean);

    @Override
    public void updateBind(@NonNull SurfaceControllerApi api, @NonNull B bean) {
        super.updateBind(api, bean);
        api.setViewGroupApi(getVg(), vg -> {
            vg.removeAllViews();
            executePos(getData(bean), (item, position) -> {
                IControllerApi controllerApi = api.getViewControllerApi(item.getControllerApi(api), item.getApiType());
//                controllerApi.onBindViewHolder(item, position); //TODO
                vg.addView(controllerApi.getRootView());
            });
        });
    }

}
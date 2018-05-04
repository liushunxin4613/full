package com.ylink.fullgoal.controllerApi.core;

import com.leo.core.core.BaseRecycleControllerApiAdapter;
import com.leo.core.iapi.main.IControllerApi;

public class RecycleControllerApiAdapter<T extends RecycleControllerApiAdapter,
        C extends IControllerApi> extends BaseRecycleControllerApiAdapter<T, C> {

    @Override
    public IControllerApi<C, T> newControllerApi() {
        return new SurfaceControllerApi(this);
    }

}

package com.ylink.fullgoal.api.surface;

import com.ylink.fullgoal.controllerApi.surface.RecycleControllerApi;

public class SetRecycleControllerApi<T extends SetRecycleControllerApi, C> extends RecycleControllerApi<T, C> {

    private Integer layoutResId;

    public SetRecycleControllerApi(C controller) {
        super(controller);
    }

    @Override
    public T setRootViewResId(Integer resId) {
        layoutResId = resId;
        return super.setRootViewResId(resId);
    }

    @Override
    public Integer getRootViewResId() {
        return layoutResId;
    }

    @Override
    public void initView() {
        super.initView();
        getRecyclerView().setFocusable(false);
        getRecyclerView().setFocusableInTouchMode(false);
    }

}

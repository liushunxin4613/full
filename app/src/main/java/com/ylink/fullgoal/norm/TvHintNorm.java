package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvHintControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHintNorm extends BaseTestNorm<TvHintNorm> {

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvHintControllerApi(controller);
    }

    private transient boolean show;

    public TvHintNorm(String name, boolean show) {
        super(name);
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

}
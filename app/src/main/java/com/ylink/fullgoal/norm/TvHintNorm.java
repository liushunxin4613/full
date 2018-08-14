package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvHintControllerApi;

public class TvHintNorm extends BaseTestNorm<TvHintNorm> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return TvHintControllerApi.class;
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
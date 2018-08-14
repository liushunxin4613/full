package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvControllerApi;

public class TvNorm extends BaseTestNorm<TvNorm>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return TvControllerApi.class;
    }

    public TvNorm(String name) {
        super(name);
    }

    public TvNorm(String name, OnBVClickListener<TvNorm> listener) {
        super(name, listener);
    }

}

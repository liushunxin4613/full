package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvHTv3ControllerApi;

public class TvHTv3Norm extends BaseTestNorm<TvHTv3Norm>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return TvHTv3ControllerApi.class;
    }

    public TvHTv3Norm(String name, String detail) {
        super(name, detail);
    }

}
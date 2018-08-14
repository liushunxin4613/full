package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.TvH2ControllerApi;

public class TvH2Norm extends BaseTestNorm<TvH2Norm>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return TvH2ControllerApi.class;
    }

    public TvH2Norm(String name, String detail) {
        super(name, detail);
    }

}
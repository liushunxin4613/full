package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHTv3Bi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHTv3Bean extends ApiBean<TvHTv3Bean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHTv3Bean> newDefApi() {
        return new TvHTv3Bi();
    }

    public TvHTv3Bean(String name, String detail) {
        super(name, detail);
    }

}
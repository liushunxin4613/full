package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvH2Bi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2Bean extends ApiBean<TvH2Bean>{

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvH2Bean> newDefApi() {
        return new TvH2Bi();
    }

    public TvH2Bean(String name, String detail) {
        super(name, detail);
    }

}
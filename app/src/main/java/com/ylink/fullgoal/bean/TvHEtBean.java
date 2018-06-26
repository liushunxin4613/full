package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHEtBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHEtBean extends ApiBean<TvHEtBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHEtBean> newDefApi() {
        return new TvHEtBi();
    }

    public TvHEtBean(String name, String detail, String hint) {
        super(name, detail, hint);
    }

}
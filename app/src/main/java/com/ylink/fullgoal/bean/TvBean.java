package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvBean extends ApiBean<TvBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvBean> newDefApi() {
        return new TvBi();
    }

    public TvBean(String name) {
        super(name);
    }

    public TvBean(String name, OnBVClickListener<TvBean> listener) {
        super(name, listener);
    }

}
package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvH2MoreBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2MoreBean extends ApiBean<TvH2MoreBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvH2MoreBean> newDefApi() {
        return new TvH2MoreBi();
    }

    public TvH2MoreBean(String name, String detail) {
        super(name, detail);
    }

    public TvH2MoreBean(String name, String detail, String hint, OnBVClickListener<TvH2MoreBean> listener) {
        super(name, detail, hint, listener);
    }

}
package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHintBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHintBean extends ApiBean<TvHintBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHintBean> newDefApi() {
        return new TvHintBi();
    }

    public TvHintBean(String name) {
        super(name);
    }

    public TvHintBean(String name, OnBVClickListener<TvHintBean> listener) {
        super(name, listener);
    }

}
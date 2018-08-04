package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHintBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHintBean extends ApiBean<TvHintBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHintBean> newDefApi() {
        return new TvHintBi();
    }

    private transient boolean show;

    public TvHintBean(String name, boolean show) {
        super(name);
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

}
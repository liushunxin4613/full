package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvH2SBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvH2SBean extends ApiBean<TvH2SBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvH2SBean> newDefApi() {
        return new TvH2SBi();
    }

    private transient boolean selected;

    public TvH2SBean(String name, String detail) {
        super(name, detail);
    }

    public TvH2SBean(String name, String detail, OnBVClickListener<TvH2SBean> listener) {
        super(name, detail, listener);
    }

    public TvH2SBean(String name, String detail, boolean selected, OnBVClickListener<TvH2SBean>
            listener) {
        super(name, detail, listener);
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
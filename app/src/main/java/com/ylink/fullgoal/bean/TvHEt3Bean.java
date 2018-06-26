package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHEt3Bi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHEt3Bean extends ApiBean<TvHEt3Bean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHEt3Bean> newDefApi() {
        return new TvHEt3Bi();
    }

    private transient IObjAction<String> action;

    public TvHEt3Bean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint, null);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(getDetail());
        }
    }

}
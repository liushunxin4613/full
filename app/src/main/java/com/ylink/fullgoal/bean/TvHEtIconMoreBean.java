package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvHEtIconMoreBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHEtIconMoreBean extends ApiBean<TvHEtIconMoreBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHEtIconMoreBean> newDefApi() {
        return new TvHEtIconMoreBi();
    }

    private transient IObjAction<String> action;

    public TvHEtIconMoreBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        this.action = action;
    }

    public TvHEtIconMoreBean(Integer iconResId, String name, String detail, String hint,
                             OnBVClickListener<TvHEtIconMoreBean> listener, IObjAction<String> action) {
        super(iconResId, name, detail, hint, listener);
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
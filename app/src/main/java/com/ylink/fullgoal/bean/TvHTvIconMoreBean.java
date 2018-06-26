package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bi.TvHTvIconMoreBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvHTvIconMoreBean extends ApiBean<TvHTvIconMoreBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvHTvIconMoreBean> newDefApi() {
        return new TvHTvIconMoreBi();
    }

    private transient IObjAction<String> action;

    public TvHTvIconMoreBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        this.action = action;
    }

    public TvHTvIconMoreBean(Integer iconResId, String name, String detail, String hint,
                             OnBVClickListener<TvHTvIconMoreBean> listener, IObjAction<String> action) {
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
package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvSBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class TvSBean extends ApiBean<TvSBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvSBean> newDefApi() {
        return new TvSBi();
    }

    public TvSBean(String name) {
        super(name);
    }

    private transient IObjAction<String> textApi;

    public IObjAction<String> getTextApi() {
        return textApi;
    }

    public void setTextApi(IObjAction<String> textApi) {
        this.textApi = textApi;
    }

}

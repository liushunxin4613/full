package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.bi.IndicatorBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class IndicatorBean extends SurfaceBiBean<IndicatorBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, IndicatorBean> newDefApi() {
        return new IndicatorBi();
    }

    private String name;

    public IndicatorBean(String name, IControllerApi api) {
        this.name = name;
        setApi(api);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.api.surface.GridRecycleControllerApi;
import com.ylink.fullgoal.bi.GridBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;

import java.util.List;

public class GridBean extends BaseBiBean<GridBean, GridRecycleControllerApi> {

    @Override
    public GridRecycleControllerApi getControllerApi(SurfaceControllerApi api) {
        return new GridRecycleControllerApi(api.getController());
    }

    @Override
    protected IBindControllerApi<GridRecycleControllerApi, GridBean> newDefApi() {
        return new GridBi();
    }

    private List<? extends BaseBiBean> data;

    public GridBean(List<? extends BaseBiBean> data) {
        this.data = data;
    }

    public void setData(List<? extends BaseBiBean> data) {
        this.data = data;
    }

    public List<? extends BaseBiBean> getData() {
        return data;
    }

}
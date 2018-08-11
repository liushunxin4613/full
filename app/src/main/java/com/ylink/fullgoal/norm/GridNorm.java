package com.ylink.fullgoal.norm;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.GridControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

import java.util.List;

public class GridNorm extends SurfaceNorm<GridNorm, SurfaceControllerApi> {

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new GridControllerApi(controller);
    }

    private List<? extends INorm> data;

    public GridNorm(List<? extends INorm> data) {
        this.data = data;
    }

    public void setData(List<? extends INorm> data) {
        this.data = data;
    }

    public List<? extends INorm> getData() {
        return data;
    }

}
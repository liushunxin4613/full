package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.ImageControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public class ImageNorm extends SurfaceNorm<ImageNorm, SurfaceControllerApi>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new ImageControllerApi(controller);
    }

    private Object res;

    public ImageNorm(Object res) {
        this.res = res;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

}
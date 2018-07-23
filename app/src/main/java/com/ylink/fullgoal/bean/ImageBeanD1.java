package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.ImageBiD1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class ImageBeanD1 extends SurfaceBiBean<ImageBeanD1> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ImageBeanD1> newDefApi() {
        return new ImageBiD1();
    }

    private Object res;

    public ImageBeanD1(Object res) {
        this.res = res;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

}
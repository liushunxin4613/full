package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.ImageBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class ImageBean extends SurfaceBiBean<ImageBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ImageBean> newDefApi() {
        return new ImageBi();
    }

    private Object path;

    public ImageBean(Object path) {
        this.path = path;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

}

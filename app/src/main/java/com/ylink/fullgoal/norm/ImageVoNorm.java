package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.ImageVoControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public class ImageVoNorm extends SurfaceNorm<ImageVoNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ImageVoControllerApi.class;
    }

    private Object photo;
    private String amount;
    private transient boolean show;

    public ImageVoNorm(Object photo, String amount, boolean show) {
        this.photo = photo;
        this.amount = amount;
        this.show = show;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

}
package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.ImageVoControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.QZ;
import static com.ylink.fullgoal.config.ComConfig.XG;

public class ImageVoNorm extends SurfaceNorm<ImageVoNorm, SurfaceControllerApi> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ImageVoControllerApi.class;
    }

    @Override
    public Integer getApiType() {
        if (state == null) {
            state = "";
        }
        switch (state) {
            default:
                return R.layout.l_photo_v1;
            case QZ:
                return R.layout.l_child_photo;
            case QR:
            case XG:
                return R.layout.l_photo;
        }
    }

    private Object photo;
    private String amount;
    private transient boolean show;
    private transient String state;

    public ImageVoNorm(Object photo, String amount, boolean show, String state) {
        this.photo = photo;
        this.amount = amount;
        this.show = show;
        this.state = state;
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
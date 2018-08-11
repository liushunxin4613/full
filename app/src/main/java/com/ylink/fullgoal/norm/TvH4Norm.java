package com.ylink.fullgoal.norm;

import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.TvH4ControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

public class TvH4Norm extends SurfaceNorm<TvH4Norm, SurfaceControllerApi>{

    @Override
    public Integer getApiType() {
        if(isTitle()){
            return R.layout.l_process_title;
        }
        return R.layout.l_process;
    }

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new TvH4ControllerApi(controller);
    }

    private String name;
    private String detail;
    private String type;
    private String time;
    private transient boolean isTitle;

    private boolean isTitle() {
        return isTitle;
    }

    public TvH4Norm() {
        this.isTitle = true;
    }

    public TvH4Norm(String name, String detail, String type, String time) {
        this.isTitle = false;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
package com.ylink.fullgoal.bean;

import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.TvH4Bi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

public class TvH4Bean extends SurfaceBiBean<TvH4Bean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, TvH4Bean> newDefApi() {
        return new TvH4Bi();
    }

    private String name;
    private String detail;
    private String type;
    private String time;
    private transient boolean isTitle;

    public boolean isTitle() {
        return isTitle;
    }

    public TvH4Bean() {
        this.isTitle = true;
    }

    public TvH4Bean(String name, String detail, String type, String time) {
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
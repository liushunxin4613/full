package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.CCSQDControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class CCSQDNorm extends OnClickNorm<CCSQDNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return CCSQDControllerApi.class;
    }

    private String start;
    private String name;
    private String end;
    private String detail;

    public CCSQDNorm(String start, String name, String end, String detail,
                     OnBVClickListener<CCSQDNorm> listener) {
        super(listener);
        this.start = start;
        this.name = name;
        this.end = end;
        this.detail = detail;
    }

    public View.OnClickListener getOnClickListener() {
        return getEnable(super.getOnClickListener());
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
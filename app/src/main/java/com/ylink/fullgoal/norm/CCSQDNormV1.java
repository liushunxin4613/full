package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.CCSQDControllerApiV1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class CCSQDNormV1 extends OnClickNorm<CCSQDNormV1, SurfaceControllerApi>{

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new CCSQDControllerApiV1(controller);
    }

    private String start;
    private String name;
    private String detail;
    private transient Integer colorResId;

    public CCSQDNormV1(String start, String name, String detail, Integer resId,
                       OnBVClickListener<CCSQDNormV1> listener) {
        super(listener);
        this.start = start;
        this.name = name;
        this.detail = detail;
        this.colorResId = resId;
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

    public Integer getColorResId() {
        return colorResId;
    }

    public void setColorResId(Integer colorResId) {
        this.colorResId = colorResId;
    }

}
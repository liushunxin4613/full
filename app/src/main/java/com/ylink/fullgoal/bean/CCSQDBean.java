package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.CCSQDBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

/**
 * 出差申请单
 */
public class CCSQDBean extends SurfaceBiBean<CCSQDBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, CCSQDBean> newDefApi() {
        return new CCSQDBi();
    }

    private transient View.OnClickListener onClickListener;

    private String start;
    private String name;
    private String end;
    private String detail;

    public CCSQDBean(String start, String name, String end, String detail,
                     OnBVClickListener<CCSQDBean> listener) {
        this.start = start;
        this.name = name;
        this.end = end;
        this.detail = detail;
        this.onClickListener = getOnBVClickListener(listener);
    }

    public View.OnClickListener getOnClickListener() {
        return getEnable(onClickListener, null);
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
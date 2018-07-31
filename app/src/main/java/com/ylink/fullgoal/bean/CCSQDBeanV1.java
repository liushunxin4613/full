package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.CCSQDBiV1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

/**
 * 出差申请单
 */
public class CCSQDBeanV1 extends SurfaceBiBean<CCSQDBeanV1> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, CCSQDBeanV1> newDefApi() {
        return new CCSQDBiV1();
    }

    private transient View.OnClickListener onClickListener;

    private String start;
    private String name;
    private String detail;
    private transient Integer colorResId;

    public CCSQDBeanV1(String start, String name, String detail, Integer resId,
                       OnBVClickListener<CCSQDBeanV1> listener) {
        this.start = start;
        this.name = name;
        this.detail = detail;
        this.colorResId = resId;
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

    public Integer getColorResId() {
        return colorResId;
    }

    public void setColorResId(Integer colorResId) {
        this.colorResId = colorResId;
    }

}
package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.XCJPBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

/**
 * 携程机票
 */
public class XCJPBean extends SurfaceBiBean<XCJPBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, XCJPBean> newDefApi() {
        return new XCJPBi();
    }

    private transient View.OnClickListener onClickListener;

    @Override
    protected String getDefaultKeyword() {
        return getType();
    }

    private String name;
    private String detail;
    private String type;
    private String start;
    private String end;
    private String place;

    public XCJPBean(String name, String detail, String type, String start, String end, String place) {
        this(name, detail, type, start, end, place, null);
    }

    public XCJPBean(String name, String detail, String type, String start, String end, String place,
                    OnBVClickListener<XCJPBean> listener) {
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.start = start;
        this.end = end;
        this.place = place;
        this.setOnBVClickListener(listener);
    }

    public void setOnBVClickListener(OnBVClickListener<XCJPBean> listener) {
        this.onClickListener = listener == null ? null : v -> listener.onBVClick(this, v);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}

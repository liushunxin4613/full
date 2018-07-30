package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.DiaoyanBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class DiaoyanBean extends OnClickBean<DiaoyanBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, DiaoyanBean> newDefApi() {
        return new DiaoyanBi();
    }

    private String code;
    private String name;
    private String type;
    private String state;
    private String startDate;
    private String endDate;
    private String text;

    public DiaoyanBean(String code, String name, String type, String state, String startDate,
                       String endDate, String text, OnBVClickListener<DiaoyanBean> listener) {
        super(listener);
        this.code = code;
        this.name = name;
        this.type = type;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

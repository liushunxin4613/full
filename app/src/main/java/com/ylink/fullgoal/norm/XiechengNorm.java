package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.XiechengControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class XiechengNorm extends OnClickNorm<XiechengNorm, SurfaceControllerApi> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return XiechengControllerApi.class;
    }

    private String code;
    private String startPlace;
    private String endPlace;
    private String username;
    private String startDate;
    private String startTime;
    private String amount;
    private String endDate;
    private String endTime;

    public XiechengNorm(String code, String startPlace, String endPlace, String username,
                        String startDate, String startTime, String amount, String endDate,
                        String endTime, OnBVClickListener<XiechengNorm> listener) {
        super(listener);
        this.code = code;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.username = username;
        this.startDate = startDate;
        this.startTime = startTime;
        this.amount = amount;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
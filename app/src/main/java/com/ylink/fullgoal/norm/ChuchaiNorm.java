package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.ChuchaiControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ChuchaiNorm extends OnClickNorm<ChuchaiNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ChuchaiControllerApi.class;
    }

    private String code;
    private String amount;
    private String place;
    private String day;
    private String startDate;
    private String endDate;
    private String text;

    public ChuchaiNorm(String code, String amount, String place, String day, String startDate,
                       String endDate, String text, OnBVClickListener<ChuchaiNorm> listener) {
        super(listener);
        this.code = code;
        this.amount = amount;
        this.place = place;
        this.day = day;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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
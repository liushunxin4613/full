package com.ylink.fullgoal.vo;

/**
 * 出差申请单
 */
public class BusinessVo {

    //批次号
    private String serialNo;
    //天数
    private String days;
    //开始日期
    private String startDate;
    //开始日期
    private String endDate;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
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

}

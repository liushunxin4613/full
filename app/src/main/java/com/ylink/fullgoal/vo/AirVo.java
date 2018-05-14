package com.ylink.fullgoal.vo;

/**
 * 携程机票组
 */
public class AirVo {

    //用户
    private String user;
    //类型 单程、返程
    private String type;
    //金额
    private String money;
    //出发时间
    private String startTime;
    //到达时间
    private String endTime;
    //出发地
    private String startPlace;
    //到达地
    private String endPlace;

    public AirVo(String user, String type, String money, String startTime, String endTime, String startPlace, String endPlace) {
        this.user = user;
        this.type = type;
        this.money = money;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

}

package com.ylink.fullgoal.vo;

/**
 * 票据
 */
public class BillVo {

    //账单图像
    private Object photo;
    //金额
    private String money;

    public BillVo(Object photo) {
        this.photo = photo;
    }

    public Object getPhoto() {
        return photo;
    }

    public void setPhoto(Object photo) {
        this.photo = photo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}

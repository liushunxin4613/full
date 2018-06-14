package com.ylink.fullgoal.vo;

import com.ylink.fullgoal.hb.ImageHb;

/**
 * 票据
 */
public class BillVo {

    //id
    private String id;
    //账单图像
    private Object photo;
    //url
    private String url;
    //金额
    private String money;
    //类型
    private String type;
    //报销批次号
    private String serialNo;

    private transient boolean show;

    public BillVo(String id, Object photo, String url, String money, String type) {
        this.id = id;
        this.photo = photo;
        this.url = url;
        this.money = money;
        this.type = type;
    }

    public BillVo(Object photo, String money) {
        this.photo = photo;
        this.money = money;
    }

    public BillVo(Object photo, String type, String money) {
        this.photo = photo;
        this.type = type;
        this.money = money;
    }

    public BillVo(ImageHb hb) {
        if (hb != null) {
            setId(hb.getId());
            setType(hb.getType());
            setUrl(hb.getUrl());
            setPhoto(hb.getUrl());
        }
    }

    public Object getPhoto() {
        if (photo instanceof Double) {
            photo = ((Double) photo).intValue();
        }
        if(photo == null){
            return getUrl();
        }
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isShow() {
        return show;
    }

    public BillVo setShow(boolean show) {
        this.show = show;
        return this;
    }

}

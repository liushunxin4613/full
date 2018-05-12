package com.ylink.fullgoal.vo;

import java.util.List;

/**
 * 费用指标
 */
public class CastTargetVo {

    //费用指标
    private String castTarget;
    //金额
    private String money;
    //是否需要分摊
    private String apportionState;
    //税额
    private String taxMoney;
    //金额(含税)
    private String hasCastMoney;
    //已分摊比例
    private String yetApportionPercent;
    //费用分摊组
    private List<CastApportionVo> castApportionData;

    public String getCastTarget() {
        return castTarget;
    }

    public void setCastTarget(String castTarget) {
        this.castTarget = castTarget;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getApportionState() {
        return apportionState;
    }

    public void setApportionState(String apportionState) {
        this.apportionState = apportionState;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    public String getHasCastMoney() {
        return hasCastMoney;
    }

    public void setHasCastMoney(String hasCastMoney) {
        this.hasCastMoney = hasCastMoney;
    }

    public String getYetApportionPercent() {
        return yetApportionPercent;
    }

    public void setYetApportionPercent(String yetApportionPercent) {
        this.yetApportionPercent = yetApportionPercent;
    }

    public List<CastApportionVo> getCastApportionData() {
        return castApportionData;
    }

    public void setCastApportionData(List<CastApportionVo> castApportionData) {
        this.castApportionData = castApportionData;
    }

}

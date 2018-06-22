package com.ylink.fullgoal.fg;

/**
 * 渠道分摊信息
 */
public class DimenListFg {

    /**
     * code : 00001
     * name : 账单
     */

    private String code;
    private String name;
    private String dimen;
    private String dimenCode;

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

    public String getDimen() {
        return dimen;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public String getDimenCode() {
        return dimenCode;
    }

    public void setDimenCode(String dimenCode) {
        this.dimenCode = dimenCode;
    }
}

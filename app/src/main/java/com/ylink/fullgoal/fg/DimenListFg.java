package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.IApiCodeApi;

/**
 * 渠道分摊信息
 */
public class DimenListFg implements IApiCodeApi {

    @Override
    public String getApiCode() {
        return getCode();
    }

    /**
     * code : 00001
     * name : 账单
     */

    private String code;
    private String name;
    private String dimen;
    private String dimenCode;

    public DimenListFg() {
    }

    public DimenListFg(String code, String name) {
        this.code = code;
        this.name = name;
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

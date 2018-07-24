package com.ylink.fullgoal.fg;

/**
 * 申请单
 */
public class ApplyFgV2 {

    /**
     * applyType : 001
     * applyName : 培训费申请单
     */

    private String applyType;
    private String applyName;

    public ApplyFgV2() {
    }

    public ApplyFgV2(String applyType, String applyName) {
        this.applyType = applyType;
        this.applyName = applyName;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

}
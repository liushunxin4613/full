package com.ylink.fullgoal.vo;

import com.ylink.fullgoal.cr.surface.ApplyMapControllerV2;
import com.ylink.fullgoal.cr.surface.CostIndexValueController;

/**
 * 费用指标值
 */
public class DVoV1 extends DVo {

    private CostIndexValueController costIndexValue;//费用指标值
    private ApplyMapControllerV2 apply;//申请单

    public CostIndexValueController getCostIndexValue() {
        return costIndexValue;
    }

    public void setCostIndexValue(CostIndexValueController costIndexValue) {
        this.costIndexValue = costIndexValue;
    }

    public ApplyMapControllerV2 getApply() {
        return apply;
    }

    public void setApply(ApplyMapControllerV2 apply) {
        this.apply = apply;
    }

}
package com.ylink.fullgoal.vo;

import com.ylink.fullgoal.cr.surface.CostIndexValueController;

/**
 * 费用指标值
 */
public class DVoV1 extends DVo {

    private CostIndexValueController costIndexValue;//费用指标值

    public CostIndexValueController getCostIndexValue() {
        return costIndexValue;
    }

    public void setCostIndexValue(CostIndexValueController costIndexValue) {
        this.costIndexValue = costIndexValue;
    }

}
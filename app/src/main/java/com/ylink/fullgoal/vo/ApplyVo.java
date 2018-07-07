package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.ApplyMapController;

//申请单
public class ApplyVo extends NewFieldBean {

    private ApplyMapController apply;

    public ApplyVo() {
        initNewFields();
    }

    public ApplyMapController getApply() {
        return apply;
    }

    public void setApply(ApplyMapController apply) {
        this.apply = apply;
    }

}
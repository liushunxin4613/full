package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.ApplyMapControllerV2;

//申请单
public class ApplyVoV2 extends NewFieldBean {

    private ApplyMapControllerV2 apply;

    public ApplyVoV2() {
        initNewFields();
    }

    public ApplyMapControllerV2 getApply() {
        return apply;
    }

    public void setApply(ApplyMapControllerV2 apply) {
        this.apply = apply;
    }

}
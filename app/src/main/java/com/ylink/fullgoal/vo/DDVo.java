package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.DItemController;

public class DDVo extends NewFieldBean{

    private DItemController item;

    public DDVo() {
        initNewFields();
    }

    public DItemVo getItemValue(String Key){
        return getItem().getValue(Key);
    }

    public DItemController getItem() {
        return item;
    }

    public void setItem(DItemController item) {
        this.item = item;
    }

}
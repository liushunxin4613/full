package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.leo.core.iapi.inter.IObjAction;
import com.ylink.fullgoal.cr.surface.DItemController;

import java.util.Map;

public class DDVo extends NewFieldBean {

    private DItemController item;

    public DDVo() {
        initNewFields();
    }

    public DItemVo getItemValue(String Key) {
        return getItem().getValue(Key);
    }

    public void onAllDItemVo(IObjAction<DItemVo> action) {
        if (action != null) {
            Map<String, DItemVo> map = getItem().getMap();
            execute(map, (key, value) -> action.execute(value));
        }
    }

    public DItemController getItem() {
        return item;
    }

    public void setItem(DItemController item) {
        this.item = item;
    }

}
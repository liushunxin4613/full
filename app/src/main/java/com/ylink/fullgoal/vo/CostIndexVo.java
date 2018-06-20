package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.CostItemController;
import com.ylink.fullgoal.cr.surface.MoneyController;

public class CostIndexVo extends NewFieldBean {

    private MoneyController money;//已分摊金额
    private CostItemController item;

    public CostIndexVo() {
        initNewFields();
    }

    public MoneyController getMoney() {
        return money;
    }

    public void setMoney(MoneyController money) {
        this.money = money;
    }

    public CostItemController getItem() {
        return item;
    }

    public void setItem(CostItemController item) {
        this.item = item;
    }

}
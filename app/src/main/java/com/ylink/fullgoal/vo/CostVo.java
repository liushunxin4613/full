package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.CostController;
import com.ylink.fullgoal.cr.surface.CostPageController;
import com.ylink.fullgoal.cr.surface.DimenListController;
import com.ylink.fullgoal.cr.surface.MoneyController;

public class CostVo extends NewFieldBean {

    private CostController cost;//分摊属性
    private MoneyController money;//已分摊金额
    private DimenListController dimenData;//分摊维度信息
    private CostPageController pager;//分页信息

    public CostVo() {
        initNewFields();
    }

    public CostController getCost() {
        return cost;
    }

    public void setCost(CostController cost) {
        this.cost = cost;
    }

    public MoneyController getMoney() {
        return money;
    }

    public void setMoney(MoneyController money) {
        this.money = money;
    }

    public DimenListController getDimenData() {
        return dimenData;
    }

    public void setDimenData(DimenListController dimenData) {
        this.dimenData = dimenData;
    }

    public CostPageController getPager() {
        return pager;
    }

    public void setPager(CostPageController pager) {
        this.pager = pager;
    }

}
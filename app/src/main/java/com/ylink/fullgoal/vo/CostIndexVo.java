package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.CostItemController;
import com.ylink.fullgoal.cr.surface.MoneyController;
import com.ylink.fullgoal.cr.surface.RatioController;

import static com.leo.core.util.TextUtils.getPercentage;

public class CostIndexVo extends NewFieldBean {

    private MoneyController money;//分摊金额
    private RatioController ratio;//分摊比例
    private CostItemController item;//指标字段信息

    public CostIndexVo(double money, double allMoney) {
        initNewFields();
        init(money, allMoney);
    }

    public void init(double money, double allMoney) {
        getMoney().initDB(money);
        getRatio().initDB(getPercentage(money, allMoney));
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

    public RatioController getRatio() {
        return ratio;
    }

    public void setRatio(RatioController ratio) {
        this.ratio = ratio;
    }
}
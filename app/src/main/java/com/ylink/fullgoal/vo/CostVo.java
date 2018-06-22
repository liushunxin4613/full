package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.cr.surface.CostIndexController;
import com.ylink.fullgoal.cr.surface.CostPageController;
import com.ylink.fullgoal.cr.surface.DimenListController;
import com.ylink.fullgoal.cr.surface.MoneyController;
import com.ylink.fullgoal.cr.surface.RatioController;

import static com.leo.core.util.TextUtils.getPercentage;

public class CostVo extends NewFieldBean {

    private transient MoneyController allMoney;//已分摊金额
    private transient MoneyController money;//已分摊金额
    private transient RatioController ratio;//已分摊比例
    private transient DimenListController dimenData;//分摊维度信息
    private CostIndexController cost;//费用指标
    private CostPageController pager;//分页信息

    public CostVo() {
        initNewFields();
    }

    public void initAllMoney(double allMoney){
        getAllMoney().initDB(allMoney);
    }

    public void init(double money) {
        getMoney().initDB(money);
        double allMoney = getAllMoney().getdouble();
        getRatio().initDB(getPercentage(money, allMoney));
    }

    public double getRestMoney(IControllerApi... args){
        return getAllMoney().getdouble() - getPager().getFilterMoney(args);
    }

    public String getOtherRatio(){
        return getPercentage(getPager().getFilterMoney(), getAllMoney().getdouble());
    }

    public String getRatio(double money){
        return getPercentage(money, getAllMoney().getdouble());
    }

    public CostIndexController getCost() {
        return cost;
    }

    public void setCost(CostIndexController cost) {
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

    public RatioController getRatio() {
        return ratio;
    }

    public void setRatio(RatioController ratio) {
        this.ratio = ratio;
    }

    public MoneyController getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(MoneyController allMoney) {
        this.allMoney = allMoney;
    }
}
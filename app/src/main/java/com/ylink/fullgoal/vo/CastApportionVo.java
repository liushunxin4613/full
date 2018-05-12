package com.ylink.fullgoal.vo;

/**
 * 费用分摊
 */
public class CastApportionVo {

    //金额
    private String money;
    //员工
    private String user;
    //部门
    private String department;
    //分摊比例
    private String apportionPercent;
    //渠道维度
    private String placeDimension;
    //产品维度
    private String productDimension;
    //营销活动
    private String marketingActivity;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getApportionPercent() {
        return apportionPercent;
    }

    public void setApportionPercent(String apportionPercent) {
        this.apportionPercent = apportionPercent;
    }

    public String getPlaceDimension() {
        return placeDimension;
    }

    public void setPlaceDimension(String placeDimension) {
        this.placeDimension = placeDimension;
    }

    public String getProductDimension() {
        return productDimension;
    }

    public void setProductDimension(String productDimension) {
        this.productDimension = productDimension;
    }

    public String getMarketingActivity() {
        return marketingActivity;
    }

    public void setMarketingActivity(String marketingActivity) {
        this.marketingActivity = marketingActivity;
    }

}

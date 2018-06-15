package com.ylink.fullgoal.fg;

public class CostFg {

    private String costCode;//费用指标编号
    private String costIndex;//费用指标名称
    private String amount;//金额（含税）
    private String exTaxAmount;//金额（不含税）
    private String taxAmount;//税额
    private String share;//分摊
    private String explain;//分摊说明

    /**
     * 此处不可取消
     */
    public CostFg() {
    }

    public CostFg(String costCode, String costIndex) {
        this.costCode = costCode;
        this.costIndex = costIndex;
    }

    public String getCostIndex() {
        return costIndex;
    }

    public void setCostIndex(String costIndex) {
        this.costIndex = costIndex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExTaxAmount() {
        return exTaxAmount;
    }

    public void setExTaxAmount(String exTaxAmount) {
        this.exTaxAmount = exTaxAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }
}

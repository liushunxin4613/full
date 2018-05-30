package com.ylink.fullgoal.vo;

public class SearchVo<D> {

    /**
     * 报销人
     */
    public final static String REIMBURSEMENT = "报销人";

    /**
     * 预算归属部门
     */
    public final static String BUDGET_DEPARTMENT = "预算归属部门";

    /**
     * 项目
     */
    public final static String PROJECT = "项目";

    /**
     * 合同付款申请单
     */
    public final static String CONTRACT_BILL = "合同付款申请单";

    /**
     * 招待申请单
     */
    public final static String SERVE_BILL = "招待申请单";

    /**
     * 费用指标
     */
    public final static String COST_INDEX = "费用指标";

    /**
     * 出差申请单
     */
    public final static String BUSINESS = "出差申请单";

    /**
     * 携程机票
     */
    public final static String XC_AIR = "携程机票";

    /**
     * 搜索参数
     */
    public final static String[][] SEARCHS = {
            {REIMBURSEMENT, "reimbursementCompensation"},
            {BUDGET_DEPARTMENT, "BudgetDepartmentCompensation"},
            {PROJECT, "ProjectCompensation"},
            {CONTRACT_BILL, "PaymentRequestCompensation"},
            {SERVE_BILL, "ServeBillCompensation"},
            {COST_INDEX, ""},
            {BUSINESS, ""},
            {XC_AIR, ""},
    };

    private String search;
    private D obj;

    public SearchVo(String search, D obj) {
        this.search = search;
        this.obj = obj;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public D getObj() {
        return obj;
    }

    public void setObj(D obj) {
        this.obj = obj;
    }

}

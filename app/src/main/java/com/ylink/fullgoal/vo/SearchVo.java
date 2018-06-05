package com.ylink.fullgoal.vo;

public class SearchVo<D> {

    /**
     * 员工
     */
    public final static String REIMBURSEMENT = "员工";

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
     * 出差申请单
     */
    public final static String BUSINESS = "出差申请单";

    /**
     * 招待申请单
     */
    public final static String SERVE_BILL = "招待申请单";

    /**
     * 投研报告
     */
    public final static String REPORT = "投研报告";

    /**
     * 携程机票
     */
    public final static String XC_AIR = "携程机票";

    /**
     * 费用指标
     */
    public final static String COST_INDEX = "费用指标";

    /**
     * 搜索参数
     */
    public final static String[][] SEARCHS = {
            //员工
            {REIMBURSEMENT, "reimbursementCompensation"},
            //预算归属部门
            {BUDGET_DEPARTMENT, "BudgetDepartmentCompensation"},
            //项目
            {PROJECT, "ProjectCompensation"},
            //合同付款申请单
            {CONTRACT_BILL, "PaymentRequestCompensation"},
            //出差申请单
            {BUSINESS, "TraveFormCompensation"},
            //招待申请单
            {SERVE_BILL, "TraveFormCompensation"},
            //投研报告
            {REPORT, "ResearchReportCompensation"},
            //携程机票
            {XC_AIR, "CtripCompensation"},
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

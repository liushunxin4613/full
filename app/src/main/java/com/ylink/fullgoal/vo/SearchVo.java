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

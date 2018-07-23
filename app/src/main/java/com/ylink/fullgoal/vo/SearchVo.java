package com.ylink.fullgoal.vo;

import com.google.gson.reflect.TypeToken;
import com.ylink.fullgoal.fg.SerialVersionTag;

import java.lang.reflect.Type;
import java.util.List;

public class SearchVo<D> extends SerialVersionTag {

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
     * 费用指标
     */
    public final static String COST_INDEX = "费用指标";

    /**
     * 单据
     */
    public final static String APPLY = "单据";

    /**
     * 单据内容
     */
    public final static String APPLY_CONTENT = "单据内容";

    /**
     * 费用指标维度
     */
    public final static String COST_INDEX_DIMEN = "费用指标维度";

    /**
     * 投研报告
     */
    public final static String REPORT = "投研报告";

    /**
     * 携程机票
     */
    public final static String XC_AIR = "携程机票";

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
    private String value;
    private D obj;

    public SearchVo(String search, String value, D obj) {
        this.search = search;
        this.value = value;
        this.obj = obj;
        this.setSerialVersionTag(getType(obj, getClass()));
    }

    public SearchVo(String search, String value, D obj, TypeToken token) {
        this.search = search;
        this.value = value;
        this.obj = obj;
        this.setSerialVersionTag(token);
    }

    private Type getType(Object obj, Class clz) {
        if (obj != null && clz != null) {
            if (obj instanceof List) {
                List data = (List) obj;
                if (data.size() > 0) {
                    Type type = getType(data.get(0), List.class);
                    if (type != null) {
                        return TypeToken.getParameterized(clz, type).getType();
                    }
                }
                return TypeToken.getParameterized(clz, List.class).getType();
            } else {
                return TypeToken.getParameterized(clz, obj.getClass()).getType();
            }
        }
        return null;
    }

    public SearchVo(String search, D obj) {
        this(search, null, obj);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
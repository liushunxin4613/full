package com.ylink.fullgoal.vo;

import java.util.List;

/**
 * 报销
 * 25个字段
 * 7个data字段、2个引入字段、16个普通字段
 */
public class ReimburseVo {

    //修改状态

    /**
     * 经办人发起
     */
    public final static String STATE_INITIATE = "经办人发起";
    /**
     * 经办人确认
     */
    public final static String STATE_CONFIRM = "经办人确认";
    /**
     * 经办人修改
     */
    public final static String STATE_ALTER = "经办人修改";
    /**
     * 报销详情
     */
    public final static String STATE_DETAIL = "报销详情";

    //发票类型

    /**
     * 一般费用普票报销
     */
    public final static String REIMBURSE_TYPE_GENERAL_COMMON = "一般费用普票报销";
    /**
     * 一般费用专票报销
     */
    public final static String REIMBURSE_TYPE_GENERAL_DEDICATED = "一般费用专票报销";
    /**
     * 出差费用普票报销
     */
    public final static String REIMBURSE_TYPE_EVECTION_COMMON = "出差费用普票报销";
    /**
     * 出差费用专票报销
     */
    public final static String REIMBURSE_TYPE_EVECTION_DEDICATED = "出差费用专票报销";

    //隐式的

    //渠道
    private String channel = "android";
    //发起日期
    private String fillDate;

    //界面不显示的

    //修改状态 发起、确认、修改
    private transient String state;
    //报销状态 待处理、审核中、已完成、已取消四类
    private String reimbursementState;
    //报销单号
    private String orderNo;
    //报销类型(自用)
    private String reimburseType;
    //报销类别 1.一般费用报销;2.出差费用报销;
    private String billType;
    //是否专票 1.专票;2.普票;
    private String isTickets;
    //费用指标需不需要分摊
    private String apportion;
    //费用指标
    private CastTargetVo castTarget;
    //禁止规则组
    private List<InhibitionRuleVo> inhibitionRuleData;
    //提交标志
    private String sbumitFlag;

    //UI需用的

    //报销批次号
    private String serialNo;
    //经办人
    private String agent;
    //部门
    private String department;
    //报销人
    private String reimbursement;
    //预算归属部门
    private String budgetDepartment;
    //项目
    private String project;
    //金额
    private String totalAmountLower;
    //事由
    private String cause;
    //票据组
    private List<BillVo> billData;
    //流程处理组
    private List<ProcessVo> processData;

    //一般费用报销单有

    //合同付款申请单
    private String paymentRequest;
    //投研报告
    private String reportName;
    //招待申请单
    private String serveBill;

    //出差费用报销单有

    //出差申请单组
    private List<BusinessVo> businessData;
    //交通费报销票据组
    private List<BillVo> trafficBillData;
    //住宿费报销票据组
    private List<BillVo> stayBillData;
    //车船机票费报销
    private AirDataVo airDataVo;
    //其他费用报销票据组
    private List<BillVo> otherBillData;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getReimbursementState() {
        return reimbursementState;
    }

    public void setReimbursementState(String reimbursementState) {
        this.reimbursementState = reimbursementState;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getApportion() {
        return apportion;
    }

    public void setApportion(String apportion) {
        this.apportion = apportion;
    }

    public CastTargetVo getCastTarget() {
        return castTarget;
    }

    public void setCastTarget(CastTargetVo castTarget) {
        this.castTarget = castTarget;
    }

    public List<InhibitionRuleVo> getInhibitionRuleData() {
        return inhibitionRuleData;
    }

    public void setInhibitionRuleData(List<InhibitionRuleVo> inhibitionRuleData) {
        this.inhibitionRuleData = inhibitionRuleData;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getBudgetDepartment() {
        return budgetDepartment;
    }

    public void setBudgetDepartment(String budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTotalAmountLower() {
        return totalAmountLower;
    }

    public void setTotalAmountLower(String totalAmountLower) {
        this.totalAmountLower = totalAmountLower;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public List<BillVo> getBillData() {
        return billData;
    }

    public void setBillData(List<BillVo> billData) {
        this.billData = billData;
    }

    public List<ProcessVo> getProcessData() {
        return processData;
    }

    public void setProcessData(List<ProcessVo> processData) {
        this.processData = processData;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getIsTickets() {
        return isTickets;
    }

    public void setIsTickets(String isTickets) {
        this.isTickets = isTickets;
    }

    public String getSbumitFlag() {
        return sbumitFlag;
    }

    public void setSbumitFlag(String sbumitFlag) {
        this.sbumitFlag = sbumitFlag;
    }

    public String getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(String paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getServeBill() {
        return serveBill;
    }

    public void setServeBill(String serveBill) {
        this.serveBill = serveBill;
    }

    public List<BusinessVo> getBusinessData() {
        return businessData;
    }

    public void setBusinessData(List<BusinessVo> businessData) {
        this.businessData = businessData;
    }

    public List<BillVo> getTrafficBillData() {
        return trafficBillData;
    }

    public void setTrafficBillData(List<BillVo> trafficBillData) {
        this.trafficBillData = trafficBillData;
    }

    public List<BillVo> getStayBillData() {
        return stayBillData;
    }

    public void setStayBillData(List<BillVo> stayBillData) {
        this.stayBillData = stayBillData;
    }

    public AirDataVo getAirDataVo() {
        return airDataVo;
    }

    public void setAirDataVo(AirDataVo airDataVo) {
        this.airDataVo = airDataVo;
    }

    public List<BillVo> getOtherBillData() {
        return otherBillData;
    }

    public void setOtherBillData(List<BillVo> otherBillData) {
        this.otherBillData = otherBillData;
    }

    public String getReimburseType() {
        return reimburseType;
    }

    public void setReimburseType(String reimburseType) {
        this.reimburseType = reimburseType;
    }

}

package com.ylink.fullgoal.fg;

import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;
import java.util.Map;

/**
 * 报销确认请求消息
 */
public class MessageBackFg extends StatusCodeFg {

    //经办人
    private UserFg agent;
    //报销人
    private UserFg reimbursement;
    //预算归属部门
    private DepartmentFg budgetDepartment;
    //事由
    private String cause;
    //合同申请单
    private ContractPaymentFg paymentRequest;
    //项目
    private ProjectFg project;
    //招待申请单
    private ProcessFg process;
    //费用指标
    private CostFg costList;
    //图片
    private List<ImageVo> imageList;
    //携程机票
    private List<CtripTicketsFg> ctrip;
    //投研报告
    private List<ResearchReportFg> report;
    //出差申请单
    private List<TravelFormFg> travel;
    //金额
    private String totalAmountLower;
    //规则信息列表
    private List<RuleFg> ruleList;
    //分摊标志
    private String isShare;
    //申请单
    private List<Map<String, String>> apply;
    //报销类型
    private String billType;
    //报销状态
    private String taskType;
    private String fillDate;
    private String first;
    private String reportName;
    private String serialNo;
    private String sessionId;
    private String sbumitFlag;
    private String message;
    //审核节点
    private List<NodeFg> taskNode;

    public UserFg getAgent() {
        return agent;
    }

    public void setAgent(UserFg agent) {
        this.agent = agent;
    }

    public UserFg getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(UserFg reimbursement) {
        this.reimbursement = reimbursement;
    }

    public DepartmentFg getBudgetDepartment() {
        return budgetDepartment;
    }

    public void setBudgetDepartment(DepartmentFg budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public ContractPaymentFg getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(ContractPaymentFg paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public ProjectFg getProject() {
        return project;
    }

    public void setProject(ProjectFg project) {
        this.project = project;
    }

    public ProcessFg getProcess() {
        return process;
    }

    public void setProcess(ProcessFg process) {
        this.process = process;
    }

    public CostFg getCostList() {
        return costList;
    }

    public void setCostList(CostFg costList) {
        this.costList = costList;
    }

    public List<ImageVo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageVo> imageList) {
        this.imageList = imageList;
    }

    public List<CtripTicketsFg> getCtrip() {
        return ctrip;
    }

    public void setCtrip(List<CtripTicketsFg> ctrip) {
        this.ctrip = ctrip;
    }

    public List<ResearchReportFg> getReport() {
        return report;
    }

    public void setReport(List<ResearchReportFg> report) {
        this.report = report;
    }

    public List<TravelFormFg> getTravel() {
        return travel;
    }

    public void setTravel(List<TravelFormFg> travel) {
        this.travel = travel;
    }

    public String getTotalAmountLower() {
        return totalAmountLower;
    }

    public void setTotalAmountLower(String totalAmountLower) {
        this.totalAmountLower = totalAmountLower;
    }

    public List<RuleFg> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<RuleFg> ruleList) {
        this.ruleList = ruleList;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public List<Map<String, String>> getApply() {
        return apply;
    }

    public void setApply(List<Map<String, String>> apply) {
        this.apply = apply;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSbumitFlag() {
        return sbumitFlag;
    }

    public void setSbumitFlag(String sbumitFlag) {
        this.sbumitFlag = sbumitFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NodeFg> getTaskNode() {
        return taskNode;
    }

    public void setTaskNode(List<NodeFg> taskNode) {
        this.taskNode = taskNode;
    }

}
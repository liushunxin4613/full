package com.ylink.fullgoal.fg;

import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;

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

}
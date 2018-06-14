package com.ylink.fullgoal.fg;

import com.ylink.fullgoal.vo.ImageVo;

import java.util.List;

/**
 * 报销确认请求消息
 */
public class MessageBackFg extends StatusCodeFg {

    //经办人
    private UserList agent;
    //报销人
    private UserList reimbursement;
    //预算归属部门
    private String budgetDepartment;
    //事由
    private String cause;
    //合同申请单
    private List<ContractPaymentFg> paymentRequest;
    //项目
    private String project;
    //招待申请单
    private String process;
    //费用指标
    private List<CostFg> costIndextList;
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

    public UserList getAgent() {
        return agent;
    }

    public void setAgent(UserList agent) {
        this.agent = agent;
    }

    public UserList getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(UserList reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getBudgetDepartment() {
        return budgetDepartment;
    }

    public void setBudgetDepartment(String budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public List<ContractPaymentFg> getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(List<ContractPaymentFg> paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<CostFg> getCostIndextList() {
        return costIndextList;
    }

    public void setCostIndextList(List<CostFg> costIndextList) {
        this.costIndextList = costIndextList;
    }

}
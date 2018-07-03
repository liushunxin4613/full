package com.ylink.fullgoal.fg;

import java.util.List;

/**
 * 查询列表
 */
public class DataFg extends StatusCodeFg {

    //消息
    private String message;
    //用户列表
    private List<UserFg> userList;
    //部门列表
    private List<DepartmentFg> departtList;
    //项目列表
    private List<ProjectFg> projecttList;
    //合同申请单列表
    private List<ContractPaymentFg> compensationtList;
    //招待申请单列表
    private List<ProcessFg> processList;
    //费用指标列表
    private List<CostFg> costIndextList;
    //出差申请单列表
    private List<TravelFormFg> travelList;
    //投研报告列表
    private List<ResearchReportFg> reportList;
    //携程机票列表
    private List<CtripTicketsFg> ctriptList;
    //报销查询列表
    private List<ApplicationtFg> applicationtList;
    //银行卡查询列表
    private List<BankFg> bankCardtList2;
    //银行卡查询列表
    private List<DimenFg> dimen;
    //银行卡查询列表
    private List<DimenListFg> dimenList;

    public List<UserFg> getUserList() {
        return userList;
    }

    public void setUserList(List<UserFg> userList) {
        this.userList = userList;
    }

    public List<DepartmentFg> getDeparttList() {
        return departtList;
    }

    public void setDeparttList(List<DepartmentFg> departtList) {
        this.departtList = departtList;
    }

    public List<ProjectFg> getProjecttList() {
        return projecttList;
    }

    public void setProjecttList(List<ProjectFg> projecttList) {
        this.projecttList = projecttList;
    }

    public List<ProcessFg> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessFg> processList) {
        this.processList = processList;
    }

    public List<CostFg> getCostIndextList() {
        return costIndextList;
    }

    public void setCostIndextList(List<CostFg> costIndextList) {
        this.costIndextList = costIndextList;
    }

    public List<ResearchReportFg> getReportList() {
        return reportList;
    }

    public void setReportList(List<ResearchReportFg> reportList) {
        this.reportList = reportList;
    }

    public List<ContractPaymentFg> getCompensationtList() {
        return compensationtList;
    }

    public void setCompensationtList(List<ContractPaymentFg> compensationtList) {
        this.compensationtList = compensationtList;
    }

    public List<TravelFormFg> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<TravelFormFg> travelList) {
        this.travelList = travelList;
    }

    public List<CtripTicketsFg> getCtriptList() {
        return ctriptList;
    }

    public void setCtriptList(List<CtripTicketsFg> ctriptList) {
        this.ctriptList = ctriptList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApplicationtFg> getApplicationtList() {
        return applicationtList;
    }

    public void setApplicationtList(List<ApplicationtFg> applicationtList) {
        this.applicationtList = applicationtList;
    }

    public List<BankFg> getBankCardtList2() {
        return bankCardtList2;
    }

    public void setBankCardtList2(List<BankFg> bankCardtList2) {
        this.bankCardtList2 = bankCardtList2;
    }

    public List<DimenFg> getDimen() {
        return dimen;
    }

    public void setDimen(List<DimenFg> dimen) {
        this.dimen = dimen;
    }

    public List<DimenListFg> getDimenList() {
        return dimenList;
    }

    public void setDimenList(List<DimenListFg> dimenList) {
        this.dimenList = dimenList;
    }

}
package com.ylink.fullgoal.hb;

import com.leo.core.util.DateUtil;

import java.util.List;

/**
 * 报销提交
 */
public class ReimburseUpHb {

    //渠道
    private final static String CHANNEL = "android";
    //一般费用报销
    public final static String GENERAL_BILL_TYPE = "1";
    //出差费用报销
    public final static String EVECTION_BILL_TYPE = "2";

    //经办人
    private String agent;
    //渠道
    private String channel;
    //报销人
    private String reimbursement;
    //发起时间
    private String fillDate;
    //报销流水号
    private String serialNo;
    //报销类型
    private String billType;
    //预算归属部门
    private String budgetDepartment;
    //事由
    private String cause;
    //金额
    private String totalAmountLower;
    //项目
    private String project;
    //合同申请单
    private String paymentRequest;
    //招待申请单
    private String serveBill;
    //出差申请单编号集合
    private List<String> traveList;
    //投研报告编号集合
    private List<String> reportName;
    //携程机票编号集合
    private List<String> ticketList;
    //票据影像集合
    private List<ImageHb> imageList;

    /**
     * 初始化一般费用报销数据
     *
     * @param serialNo         报销批次号
     * @param agent            经办人
     * @param reimbursement    报销人
     * @param budgetDepartment 预算归属部门
     * @param project          项目
     * @param totalAmountLower 金额
     * @param cause            事由
     * @param paymentRequest   合同申请单
     * @param serveBill        招待申请单
     */
    public ReimburseUpHb(String serialNo, String agent, String reimbursement, String budgetDepartment, String project,
                         String totalAmountLower, String cause, String paymentRequest, String serveBill,
                         List<ImageHb> imageList) {
        this.billType = GENERAL_BILL_TYPE;
        this.serialNo = serialNo;
        this.agent = agent;
        this.reimbursement = reimbursement;
        this.budgetDepartment = budgetDepartment;
        this.project = project;
        this.totalAmountLower = totalAmountLower;
        this.cause = cause;
        this.paymentRequest = paymentRequest;
        this.serveBill = serveBill;
        this.imageList = imageList;
        init();
    }

    /**
     * 初始化出差费用报销数据
     *
     * @param serialNo         报销批次号
     * @param agent            经办人
     * @param reimbursement    报销人
     * @param budgetDepartment 预算归属部门
     * @param project          项目
     * @param totalAmountLower 金额
     * @param cause            事由
     * @param traveList        投研报告编号集合
     * @param reportName       出差申请单编号集合
     * @param ticketList       携程机票编号集合
     */
    public ReimburseUpHb(String serialNo, String agent, String reimbursement, String budgetDepartment, String project,
                         String totalAmountLower, String cause, List<String> traveList,
                         List<String> reportName, List<String> ticketList, List<ImageHb> imageList) {
        this.billType = EVECTION_BILL_TYPE;
        this.serialNo = serialNo;
        this.agent = agent;
        this.reimbursement = reimbursement;
        this.budgetDepartment = budgetDepartment;
        this.project = project;
        this.totalAmountLower = totalAmountLower;
        this.cause = cause;
        this.traveList = traveList;
        this.reportName = reportName;
        this.ticketList = ticketList;
        this.imageList = imageList;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.channel = CHANNEL;
        this.fillDate = DateUtil.getNowTimeString();
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getFillDate() {
        return fillDate;
    }

    public void setFillDate(String fillDate) {
        this.fillDate = fillDate;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
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

    public List<ImageHb> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageHb> imageList) {
        this.imageList = imageList;
    }

    public String getTotalAmountLower() {
        return totalAmountLower;
    }

    public void setTotalAmountLower(String totalAmountLower) {
        this.totalAmountLower = totalAmountLower;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(String paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getServeBill() {
        return serveBill;
    }

    public void setServeBill(String serveBill) {
        this.serveBill = serveBill;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getTraveList() {
        return traveList;
    }

    public void setTraveList(List<String> traveList) {
        this.traveList = traveList;
    }

    public List<String> getReportName() {
        return reportName;
    }

    public void setReportName(List<String> reportName) {
        this.reportName = reportName;
    }

    public List<String> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<String> ticketList) {
        this.ticketList = ticketList;
    }
}

package com.ylink.fullgoal.fg;

import com.leo.core.util.DateUtil;
import com.ylink.fullgoal.vo1.HelpVo;

import java.util.List;

/**
 * 报销提交
 */
public class ReimburseUpFg extends HelpVo {

    //渠道
    private final static String CHANNEL = "android";
    //一般费用报销
    private final static String GENERAL_BILL_TYPE = "1";
    //出差费用报销
    private final static String EVECTION_BILL_TYPE = "2";

    //首次上传:1.提交;2.确认;3.修改;
    private String first;
    //经办人
    private UserList agent;
    //渠道
    private String channel;
    //报销人
    private UserList reimbursement;
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
    //合同付款申请单
    private List<ContractPaymentFg> paymentRequest;
    //招待申请单
    private String entertainrequest;
    //费用指标
    private List<CostFg> costList;
    //出差申请单编号集合
    private List<String> traveList;
    //投研报告编号集合
    private List<String> reportName;
    //携程机票编号集合
    private List<String> ticketList;
    //提交标识 （1-新增影响提交   2-正常修改提交）
    private String sbumitFlag;
    //任务类型 （1-报销发起，2-报销确认，3-报销修改）
    private String taskType;

    /**
     * 初始化一般费用报销数据
     *
     * @param first            首次上传:1.提交;2.确认;3.修改;
     * @param serialNo         报销批次号
     * @param agent            经办人
     * @param reimbursement    报销人
     * @param budgetDepartment 预算归属部门
     * @param project          项目
     * @param totalAmountLower 金额
     * @param cause            事由
     * @param paymentRequest   合同付款申请单
     * @param entertainrequest 招待申请单
     * @param costList         费用指标
     */
    public ReimburseUpFg(String first, String serialNo, UserList agent,
                         UserList reimbursement, String budgetDepartment,
                         String project, String totalAmountLower,
                         String cause, List<ContractPaymentFg> paymentRequest,
                         String entertainrequest, List<CostFg> costList) {
        this.billType = GENERAL_BILL_TYPE;
        this.first = first;
        this.serialNo = serialNo;
        this.agent = agent;
        this.reimbursement = reimbursement;
        this.budgetDepartment = budgetDepartment;
        this.project = project;
        this.totalAmountLower = totalAmountLower;
        this.cause = cause;
        this.paymentRequest = paymentRequest;
        this.entertainrequest = entertainrequest;
        this.costList = costList;
        init();
    }

    /**
     * 初始化出差费用报销数据
     *
     * @param first            首次上传:1.提交;2.确认;3.修改;
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
    public ReimburseUpFg(String first, String serialNo, UserList agent,
                         UserList reimbursement, String budgetDepartment,
                         String project, String totalAmountLower, String cause, List<String> traveList,
                         List<String> reportName, List<String> ticketList) {
        this.billType = EVECTION_BILL_TYPE;
        this.first = first;
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
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.channel = CHANNEL;
        this.fillDate = DateUtil.getNowTimeString();
        //test
        this.sbumitFlag = "1";
        this.taskType = "1";
        //防止服务器端List报错
        tryData(paymentRequest, costList, traveList, reportName, ticketList);
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

    public List<ContractPaymentFg> getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(List<ContractPaymentFg> paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getEntertainrequest() {
        return entertainrequest;
    }

    public void setEntertainrequest(String entertainrequest) {
        this.entertainrequest = entertainrequest;
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSbumitFlag() {
        return sbumitFlag;
    }

    public void setSbumitFlag(String sbumitFlag) {
        this.sbumitFlag = sbumitFlag;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<CostFg> getCostList() {
        return costList;
    }

    public void setCostList(List<CostFg> costList) {
        this.costList = costList;
    }

}
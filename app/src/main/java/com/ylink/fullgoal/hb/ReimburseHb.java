package com.ylink.fullgoal.hb;

import java.util.List;

/**
 * 报销获取
 */
public class ReimburseHb {

    /**
     * id : 79
     * serialNo : 2018060509751
     * approvalStatus : 1
     * fkApprovalNum :
     * channel : android
     * agent : 张三
     * reimbursement : 曹勤莉
     * fillDate : 2018-06-05 09:11:51
     * budgetDepartment : 机构中台
     * totalAmountLower :
     * project : 项目查看
     * paymentRequest :
     * serveBill :
     * cause : 22
     * billType : 2
     * isTickets :
     * sbumitFlag :
     * traveList : [{"serialNo":"2018060509751","code":"201909023"}]
     * reportName : [{"serialNo":"2018060509751","reportCode":"2018091003"}]
     * ticketList : [{"serialNo":"2018060509751","flightNumber":"20180109"}]
     * imageList : [{"id":"244","url":"http://111.231.231.226/uploadfile/image/20180605/20180605091135N72K1.jpg","type":"1","serialNo":"2018060509751"},{"id":"245","url":"http://111.231.231.226/uploadfile/image/20180605/20180605091140KFQRD.jpg","type":"2","serialNo":"2018060509751"},{"id":"246","url":"http://111.231.231.226/uploadfile/image/20180605/20180605091145YFE18.jpg","type":"3","serialNo":"2018060509751"}]
     */

    private String id;
    private String serialNo;
    private String approvalStatus;
    private String fkApprovalNum;
    private String channel;
    private String agent;
    private String reimbursement;
    private String fillDate;
    private String budgetDepartment;
    private String totalAmountLower;
    private String project;
    private String paymentRequest;
    private String serveBill;
    private String cause;
    private String billType;
    private String isTickets;
    private String sbumitFlag;
    private List<TraveHb> traveList;
    private List<ReportHb> reportName;
    private List<CtripHb> ticketList;
    private List<ImageHb> imageList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getFkApprovalNum() {
        return fkApprovalNum;
    }

    public void setFkApprovalNum(String fkApprovalNum) {
        this.fkApprovalNum = fkApprovalNum;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getBudgetDepartment() {
        return budgetDepartment;
    }

    public void setBudgetDepartment(String budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
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

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
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

    public List<TraveHb> getTraveList() {
        return traveList;
    }

    public void setTraveList(List<TraveHb> traveList) {
        this.traveList = traveList;
    }

    public List<ReportHb> getReportName() {
        return reportName;
    }

    public void setReportName(List<ReportHb> reportName) {
        this.reportName = reportName;
    }

    public List<CtripHb> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<CtripHb> ticketList) {
        this.ticketList = ticketList;
    }

    public List<ImageHb> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageHb> imageList) {
        this.imageList = imageList;
    }

}

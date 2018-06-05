package com.ylink.fullgoal.hb;

/**
 * 出差申请单
 */
public class TraveHb extends SerialNoHb {

    /**
     * workName : CRE
     * code : 201909002
     * destination : 深圳
     * startDate : 2018-05-23 08:23:00
     * endDate : 2018-05-24 08:23:00
     * amount : 1200.00
     * dates : 1
     * reimbursement : 张三
     * departmentCode : 20180507001
     */

    private String workName;
    private String code;
    private String destination;
    private String startDate;
    private String endDate;
    private String amount;
    private String dates;
    private String reimbursement;
    private String departmentCode;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

}
package com.ylink.fullgoal.hb;

/**
 * 合同付款申请单
 */
public class PaymentRequestHb {

    /**
     * id : 2
     * code : 20180529002
     * fileNumber : 20180529002
     * leadDepartment : 计划财务部
     * name : 合同付款二
     * leader : 张三
     * applicationDate : 2018-05-29 14:36
     * status : 未
     */

    private String id;
    private String code;
    private String fileNumber;
    private String leadDepartment;
    private String name;
    private String leader;
    private String applicationDate;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getLeadDepartment() {
        return leadDepartment;
    }

    public void setLeadDepartment(String leadDepartment) {
        this.leadDepartment = leadDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

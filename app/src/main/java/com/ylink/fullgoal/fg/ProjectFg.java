package com.ylink.fullgoal.fg;

/**
 * 项目
 */
public class ProjectFg{

    /**
     * amount : 1000
     * applicationDate : 2018-06-04
     * judtification : 测试用
     * leadDepartment : 部门
     * leader : 丁杰
     * projectCode : 1
     * projectName : 1项目
     */

    private String amount;
    private String applicationDate;
    private String judtification;
    private String leadDepartment;
    private String leader;
    private String projectCode;
    private String projectName;
    private String status;

    /**
     * 此处不可取消
     */
    public ProjectFg() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getJudtification() {
        return judtification;
    }

    public void setJudtification(String judtification) {
        this.judtification = judtification;
    }

    public String getLeadDepartment() {
        return leadDepartment;
    }

    public void setLeadDepartment(String leadDepartment) {
        this.leadDepartment = leadDepartment;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

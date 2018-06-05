package com.ylink.fullgoal.hb;

import java.util.List;

/**
 * 公共数据源
 */
public class DataHb {

    //返回码
    private CodeHb returnCode;
    //图片
    private ImageHb image;
    //员工列表
    private List<UserHb> usertList;
    //部门列表
    private List<DepartHb> departtList;
    //项目列表
    private List<ProjectHb> projecttList;
    //合同付款申请单列表
    private List<CompensationHb> compensationtList;
    //出差申请单列表
    private List<TraveHb> travetList;
    //携程机票列表
    private List<CtripHb> ctriptList;
    //调研报告列表
    private List<ReportHb> reportList;
    //报销
    private ReimburseHb reimburseData;
    //报销列表
    private List<ReimburseHb> reimburseList;

    public CodeHb getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(CodeHb returnCode) {
        this.returnCode = returnCode;
    }

    public ImageHb getImage() {
        return image;
    }

    public void setImage(ImageHb image) {
        this.image = image;
    }

    public List<UserHb> getUsertList() {
        return usertList;
    }

    public void setUsertList(List<UserHb> usertList) {
        this.usertList = usertList;
    }

    public List<DepartHb> getDeparttList() {
        return departtList;
    }

    public void setDeparttList(List<DepartHb> departtList) {
        this.departtList = departtList;
    }

    public List<ProjectHb> getProjecttList() {
        return projecttList;
    }

    public void setProjecttList(List<ProjectHb> projecttList) {
        this.projecttList = projecttList;
    }

    public List<CompensationHb> getCompensationtList() {
        return compensationtList;
    }

    public void setCompensationtList(List<CompensationHb> compensationtList) {
        this.compensationtList = compensationtList;
    }

    public List<TraveHb> getTravetList() {
        return travetList;
    }

    public void setTravetList(List<TraveHb> travetList) {
        this.travetList = travetList;
    }

    public List<CtripHb> getCtriptList() {
        return ctriptList;
    }

    public void setCtriptList(List<CtripHb> ctriptList) {
        this.ctriptList = ctriptList;
    }

    public List<ReportHb> getReportList() {
        return reportList;
    }

    public void setReportList(List<ReportHb> reportList) {
        this.reportList = reportList;
    }

    public ReimburseHb getReimburseData() {
        return reimburseData;
    }

    public void setReimburseData(ReimburseHb reimburseData) {
        this.reimburseData = reimburseData;
    }

    public List<ReimburseHb> getReimburseList() {
        return reimburseList;
    }

    public void setReimburseList(List<ReimburseHb> reimburseList) {
        this.reimburseList = reimburseList;
    }

}

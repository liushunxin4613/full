package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.ProjectNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 项目
 */
public class ProjectFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new ProjectNorm(getProjectName(), getProjectCode(), getStatus(), getLeader(),
                    getLeadDepartment(), (bean, view) -> api.finishActivity(new SearchVo<>(
                    api.getSearch(), getThis())));
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getProjectName(), getProjectCode(), getStatus(), getLeader(),
                getLeadDepartment()};
    }

    @Override
    public String getApiCode() {
        return getProjectCode();
    }

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

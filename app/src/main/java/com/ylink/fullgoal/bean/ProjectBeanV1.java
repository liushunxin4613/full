package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.ProjectBiV1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ProjectBeanV1 extends OnClickBean<ProjectBeanV1> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, ProjectBeanV1> newDefApi() {
        return new ProjectBiV1();
    }

    private String projectName;
    private String projectCode;
    private String state;
    private String username;
    private String department;

    public ProjectBeanV1(String projectName, String projectCode, String state, String username,
                         String department, OnBVClickListener<ProjectBeanV1> listener) {
        super(listener);
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.state = state;
        this.username = username;
        this.department = department;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.ProjectControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class ProjectNorm extends OnClickNorm<ProjectNorm, SurfaceControllerApi> {

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return ProjectControllerApi.class;
    }

    private String projectName;
    private String projectCode;
    private String state;
    private String username;
    private String department;

    public ProjectNorm(String projectName, String projectCode, String state, String username,
                         String department, OnBVClickListener<ProjectNorm> listener) {
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
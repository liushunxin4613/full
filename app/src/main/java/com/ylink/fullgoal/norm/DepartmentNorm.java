package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.DepartmentControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class DepartmentNorm extends OnClickNorm<DepartmentNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return DepartmentControllerApi.class;
    }

    private String department;

    public DepartmentNorm(String department, OnBVClickListener<DepartmentNorm> listener) {
        super(listener);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    
}
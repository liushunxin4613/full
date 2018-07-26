package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.DepartmentBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class DepartmentBean extends OnClickBean<DepartmentBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, DepartmentBean> newDefApi() {
        return new DepartmentBi();
    }

    private String department;

    public DepartmentBean(String department, OnBVClickListener<DepartmentBean> listener) {
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

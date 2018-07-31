package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IBindControllerApi;
import com.ylink.fullgoal.bi.PersonBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class PersonBean extends OnClickBean<PersonBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, PersonBean> newDefApi() {
        return new PersonBi();
    }

    private String name;
    private String phone;
    private String department;

    public PersonBean(String name, String phone, String department,
                      OnBVClickListener<PersonBean> listener) {
        super(listener);
        this.name = name;
        this.phone = phone;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}

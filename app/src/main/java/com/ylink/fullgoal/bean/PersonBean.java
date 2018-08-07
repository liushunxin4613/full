package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.viewBean.OnClickBean;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class PersonBean extends OnClickBean<PersonBean, SurfaceControllerApi> {

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

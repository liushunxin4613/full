package com.ylink.fullgoal.bean;

import com.leo.core.core.bean.ControllerApiBean;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;

public class PersonBeanV1 extends ControllerApiBean<BaseSearchControllerApi> {

    @Override
    public void initControllerApi() {
        super.initControllerApi();

    }

    private String name;
    private String phone;
    private String department;

    public PersonBeanV1(String name, String phone, String department,
                        OnBVClickListener<PersonBeanV1> listener) {
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

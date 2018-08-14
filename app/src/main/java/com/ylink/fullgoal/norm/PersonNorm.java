package com.ylink.fullgoal.norm;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.api.item.PersonControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public class PersonNorm extends OnClickNorm<PersonNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return PersonControllerApi.class;
    }

    private String name;
    private String phone;
    private String department;

    public PersonNorm(String name, String phone, String department,
                      OnBVClickListener<PersonNorm> listener) {
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
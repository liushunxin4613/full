package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;

public class UserFg extends CoreModel {

    /*@Override
    protected IControllerApiBean createControllerApiBean(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new PersonBean(getUserName(),
                    getUserCode(), getUserDepartment(),
                    (bean, view) -> api.finishActivity(new SearchVo<>
                            (api.getSearch(), getThis())));
        }
        return null;
    }*/

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        return null;
    }

    @Override
    public String getApiCode() {
        return getUserCode();
    }

    private String userCode;
    private String userName;
    private String userlevel;
    private String userDepartment;
    private String userDepartmentCode;
    private transient String bank;

    /**
     * 此处不可去除
     */
    public UserFg() {
    }

    public UserFg(String userCode, String userName) {
        this.userCode = userCode;
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getUserDepartmentCode() {
        return userDepartmentCode;
    }

    public void setUserDepartmentCode(String userDepartmentCode) {
        this.userDepartmentCode = userDepartmentCode;
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

}

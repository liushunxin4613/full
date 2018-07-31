package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.IApiCodeApi;

/**
 * 员工
 */
public class UserFg implements IApiCodeApi{

    @Override
    public String getApiCode() {
        return getUserCode();
    }

    /**
     * userCode : 0
     * userName : 张0
     */

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
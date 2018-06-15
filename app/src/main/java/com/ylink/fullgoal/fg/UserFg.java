package com.ylink.fullgoal.fg;

/**
 * 员工
 */
public class UserFg {

    /**
     * userCode : 0
     * userName : 张0
     */

    private String userCode;
    private String userName;

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

}
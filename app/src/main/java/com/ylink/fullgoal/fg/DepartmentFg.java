package com.ylink.fullgoal.fg;

/**
 * 部门
 */
public class DepartmentFg {

    /**
     * departmentCode : 0
     * departmentName : 0部门
     */

    private String departmentCode;
    private String departmentName;

    /**
     * 此处不可取消
     */
    public DepartmentFg() {
    }

    public DepartmentFg(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
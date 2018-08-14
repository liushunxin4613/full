package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.DepartmentNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 部门
 */
public class DepartmentFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new DepartmentNorm(getDepartmentName(),
                    (bean, view) -> api.finishActivity(
                            new SearchVo<>(api.getSearch(), getThis())));
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getDepartmentName()};
    }

    @Override
    public String getApiCode() {
        return getDepartmentCode();
    }

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

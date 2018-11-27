package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.DepartmentFg;

/**
 * 部门控制器
 */
public class DepartmentController<T extends DepartmentController> extends BaseStringController<T, DepartmentFg> {

    @Override
    public T initDB(DepartmentFg departmentFg) {
        return super.initDB(departmentFg);
    }

    @Override
    public DepartmentFg getDB() {
        return super.getDB();
    }

    @Override
    public String getApiCode() {
        return vor(DepartmentFg::getDepartmentCode);
    }

    @Override
    protected String getOnUBKey(String key) {
        return toField(field -> {
            switch (field){
                case "budgetDepartment":
                    return "budgetDepartment";
            }
            return super.getOnUBKey(key);
        });
    }

    @Override
    protected DepartmentFg getNoneDB() {
        return new DepartmentFg();
    }

    @Override
    public String getViewBean() {
        return vor(DepartmentFg::getDepartmentName);
    }

    public String getDepartmentName(){
        return vor(DepartmentFg::getDepartmentName);
    }

}
package com.ylink.fullgoal.cr;

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
    public Class<DepartmentFg> getClz() {
        return DepartmentFg.class;
    }

    @Override
    public String getViewBean() {
        return no(DepartmentFg::getDepartmentName);
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected String getDefUB() {
        return no(DepartmentFg::getDepartmentCode);
    }

}
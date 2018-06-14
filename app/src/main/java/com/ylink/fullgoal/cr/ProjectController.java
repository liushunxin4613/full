package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ProjectFg;

/**
 * 项目控制器
 */
public class ProjectController<T extends ProjectController> extends BaseStringController<T, ProjectFg> {

    @Override
    public T initDB(ProjectFg departmentFg) {
        return super.initDB(departmentFg);
    }

    @Override
    public ProjectFg getDB() {
        return super.getDB();
    }

    @Override
    public Class<ProjectFg> getClz() {
        return ProjectFg.class;
    }

    @Override
    public String getViewBean() {
        return no(ProjectFg::getProjectName);
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected <UB> UB getDefUB() {
        return null;
    }

}

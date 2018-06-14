package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ProjectFg;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

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
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
            case CC:
                return "project";
        }
        return super.getOnUBKey(key);
    }

}

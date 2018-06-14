package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.ResearchReportFg;

/**
 * 调研报告控制器
 */
public class ResearchReportController<T extends ResearchReportController> extends AddController<T, ResearchReportFg> {

    @Override
    public T initDB(ResearchReportFg researchReportFg) {
        return super.initDB(researchReportFg);
    }

    @Override
    protected ResearchReportFg getFilterDB(String key, ResearchReportFg researchReportFg) {
        return null;
    }

    @Override
    public ResearchReportFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<ResearchReportFg> getClz() {
        return ResearchReportFg.class;
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

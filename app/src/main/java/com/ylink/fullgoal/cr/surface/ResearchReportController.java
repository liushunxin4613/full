package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IOnCom;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.ResearchReportFg;

import static com.ylink.fullgoal.config.ComConfig.CC;

/**
 * 调研报告控制器
 */
public class ResearchReportController<T extends ResearchReportController> extends AddController<T, ResearchReportFg> {

    @Override
    public T initDB(ResearchReportFg researchReportFg) {
        return super.initDB(researchReportFg);
    }

    @Override
    public T remove(ResearchReportFg researchReportFg, IOnCom action) {
        return super.remove(researchReportFg, action);
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
    protected String getOnUBKey(String key) {
        switch (key){
            case CC:
                return "reportName";
        }
        return super.getOnUBKey(key);
    }

}
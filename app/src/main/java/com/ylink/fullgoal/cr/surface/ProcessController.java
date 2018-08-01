package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ProcessFg;

import static com.ylink.fullgoal.config.ComConfig.YB;

/**
 * 招待申请单控制器
 */
public class ProcessController<T extends ProcessController> extends BaseStringController<T, ProcessFg> {

    @Override
    public T initDB(ProcessFg processFg) {
        return super.initDB(processFg);
    }

    @Override
    public ProcessFg getDB() {
        return super.getDB();
    }

    @Override
    public String getViewBean() {
        return vor(ProcessFg::getCause);
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
                return "entertainrequest";
        }
        return super.getOnUBKey(key);
    }

}

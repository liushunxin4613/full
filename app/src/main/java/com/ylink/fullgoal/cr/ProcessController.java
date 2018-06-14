package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ProcessFg;

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
    public Class<ProcessFg> getClz() {
        return ProcessFg.class;
    }

    @Override
    public String getViewBean() {
        return no(ProcessFg::getCause);
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

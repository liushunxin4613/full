package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseController;
import com.ylink.fullgoal.fg.CostFg;

public class CostController<T extends CostController> extends BaseController<T, CostFg, CostFg> {

    @Override
    public T initDB(CostFg costFg) {
        return super.initDB(costFg);
    }

    @Override
    public CostFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<CostFg> getClz() {
        return CostFg.class;
    }

    @Override
    protected Class getUBClz() {
        return null;
    }

    @Override
    public CostFg getViewBean() {
        return getDB();
    }

}

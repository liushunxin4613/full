package com.ylink.fullgoal.cr;

import com.leo.core.iapi.inter.IReturnAction;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.TravelFormFg;

/**
 * 出差申请单控制器
 */
public class TravelFormController<T extends TravelFormController> extends AddController<T, TravelFormFg> {

    @Override
    public T initDB(TravelFormFg fg) {
        return super.initDB(fg);
    }

    @Override
    protected TravelFormFg getFilterDB(String key, TravelFormFg fg) {
        return null;
    }

    @Override
    public TravelFormFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<TravelFormFg> getClz() {
        return TravelFormFg.class;
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

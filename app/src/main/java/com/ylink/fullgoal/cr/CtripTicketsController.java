package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.CtripTicketsFg;

/**
 * 携程机票控制器
 */
public class CtripTicketsController<T extends CtripTicketsController> extends AddController<T, CtripTicketsFg> {

    @Override
    public T initDB(CtripTicketsFg ctripTicketsFg) {
        return super.initDB(ctripTicketsFg);
    }

    @Override
    protected CtripTicketsFg getFilterDB(String key, CtripTicketsFg ctripTicketsFg) {
        return null;
    }

    @Override
    public CtripTicketsFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<CtripTicketsFg> getClz() {
        return CtripTicketsFg.class;
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

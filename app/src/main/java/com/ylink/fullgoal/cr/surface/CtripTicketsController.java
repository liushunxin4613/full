package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IOnCom;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.CtripTicketsFg;

import static com.ylink.fullgoal.config.ComConfig.CC;

/**
 * 携程机票控制器
 */
public class CtripTicketsController<T extends CtripTicketsController> extends AddController<T, CtripTicketsFg> {

    @Override
    public T initDB(CtripTicketsFg ctripTicketsFg) {
        return super.initDB(ctripTicketsFg);
    }

    @Override
    public T remove(CtripTicketsFg ctripTicketsFg, IOnCom action) {
        return super.remove(ctripTicketsFg, action);
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
    protected String getOnUBKey(String key) {
        switch (key){
            case CC:
                return "ctrip";
        }
        return super.getOnUBKey(key);
    }

}

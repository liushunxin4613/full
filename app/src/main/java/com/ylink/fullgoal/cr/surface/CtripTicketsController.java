package com.ylink.fullgoal.cr.surface;

import com.leo.core.iapi.main.IOnCom;
import com.leo.core.util.JavaTypeUtil;
import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.CtripTicketsFg;

import java.util.List;

/**
 * 携程机票控制器
 */
public class CtripTicketsController<T extends CtripTicketsController> extends AddController<T, CtripTicketsFg> {

    @Override
    public T initDB(CtripTicketsFg ctripTicketsFg) {
        return super.initDB(ctripTicketsFg);
    }

    @Override
    public List<CtripTicketsFg> getData() {
        return super.getData();
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
    protected String getOnUBKey(String key) {
        return "ctrip";
    }

    public double getSum(){
        double[] ds = new double[]{0};
        execute(getData(), obj -> ds[0] += JavaTypeUtil.getdouble(obj.getTicket(), 0));
        return ds[0];
    }

}

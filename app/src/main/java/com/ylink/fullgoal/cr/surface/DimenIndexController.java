package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.DimenListFg;

/**
 * 费用指标单项
 */
public class DimenIndexController<T extends DimenIndexController> extends BaseStringController<T, DimenListFg> {

    @Override
    public T initDB(DimenListFg dimenListFg) {
        return super.initDB(dimenListFg);
    }

    @Override
    public DimenListFg getDB() {
        return super.getDB();
    }

    @Override
    public String getViewBean() {
        return vor(DimenListFg::getName);
    }

    @Override
    protected Class<DimenListFg> getClz() {
        return DimenListFg.class;
    }

    @Override
    protected Class<DimenListFg> getUBClz() {
        return null;
    }

}
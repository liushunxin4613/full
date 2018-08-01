package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.DimenFg;

import java.util.List;

/**
 * 费用指标
 */
public class DimenListController<T extends DimenListController> extends AddController<T, DimenFg> {

    @Override
    public T initDB(DimenFg fg) {
        return super.initDB(fg);
    }

    @Override
    public T initDB(List<DimenFg> data) {
        return super.initDB(data);
    }

    @Override
    public List<DimenFg> getViewBean() {
        return super.getViewBean();
    }

    @Override
    protected DimenFg getFilterDB(String key, DimenFg fg) {
        return null;
    }

}
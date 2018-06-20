package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.fg.DimenListFg;

import java.util.Map;

public class CostItemController<T extends CostItemController> extends MapController<T, String, DimenListFg>{

    @Override
    public T initDB(String key, DimenListFg dimenListFg) {
        return super.initDB(key, dimenListFg);
    }

    @Override
    public T put(String key, DimenListFg dimenListFg) {
        return super.put(key, dimenListFg);
    }

    @Override
    public DimenListFg getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public DimenListFg getDB() {
        return super.getDB();
    }

    @Override
    protected Class<DimenListFg> getClz() {
        return DimenListFg.class;
    }

    @Override
    protected Class<DimenListFg> getUBClz() {
        return null;
    }

    @Override
    public Map<String, DimenListFg> getMap() {
        return super.getMap();
    }

    @Override
    public Map<String, DimenListFg> getViewBean() {
        return super.getViewBean();
    }

}

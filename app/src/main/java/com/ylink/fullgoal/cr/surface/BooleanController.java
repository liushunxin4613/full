package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseController;

public class BooleanController<T extends BooleanController> extends BaseController<T, Boolean, Boolean> {

    @Override
    public T initDB(Boolean is) {
        return super.initDB(is);
    }

    @Override
    public Boolean getDB() {
        return super.getDB();
    }

    public boolean is(){
        return no(getDB(), false);
    }

    @Override
    protected Class<Boolean> getClz() {
        return null;
    }

    @Override
    protected Class<Boolean> getUBClz() {
        return null;
    }

    @Override
    public <VB> VB getViewBean() {
        return null;
    }

}
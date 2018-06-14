package com.ylink.fullgoal.cr.core;

public abstract class BaseStringController<T extends BaseStringController, DB> extends BaseController<T, DB>{

    @Override
    public abstract String getViewBean();

}

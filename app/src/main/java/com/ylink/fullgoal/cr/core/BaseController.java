package com.ylink.fullgoal.cr.core;

import com.leo.core.api.inter.CoreController;

import java.lang.reflect.Type;

public abstract class BaseController<T extends BaseController, DB> extends CoreController<T, DB> {

    /**
     * 表基类
     * @return 类
     */
    protected abstract Class<DB> getClz();

    @Override
    public Type getType() {
        return getExecute(getClz(), c -> c);
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

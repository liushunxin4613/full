package com.ylink.fullgoal.cr.core;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.inter.CoreController;
import com.leo.core.iapi.inter.IBolAction;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseListController<T extends BaseListController, DB> extends CoreController<T, List<DB>> {

    private DB db;

    @Override
    public List<DB> getViewBean(){
        return getDB();
    }

    @Override
    public Type getType() {
        Class<DB> clz = getClz();
        if(clz != null){
            return TypeToken.getParameterized(List.class, clz).getType();
        }
        return null;
    }

    /**
     * 表item基类
     * @return 类
     */
    protected abstract Class<DB> getClz();

}

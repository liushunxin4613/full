package com.ylink.fullgoal.cr.core;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.inter.CoreController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseListController<T extends BaseListController, DB, UB> extends CoreController<T, List<DB>, List<UB>> {

    private DB db;

    @Override
    public List<DB> getViewBean() {
        return getDB();
    }

    @Override
    public Type getType() {
        Class<DB> clz = getClz();
        if (clz != null) {
            return TypeToken.getParameterized(List.class, clz).getType();
        }
        return null;
    }

    @NonNull
    @Override
    protected List<UB> getNoneUB() {
        return new ArrayList<>();
    }

    /**
     * 表item基类
     *
     * @return 类
     */
    protected abstract Class<DB> getClz();

}

package com.leo.core.api.core;

import com.leo.core.iapi.IRAction;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.util.RunUtil;

import java.util.List;

public class ThisApi<T extends ThisApi> implements IThisApi<T> {

    @Override
    public T getThis() {
        return (T) this;
    }

    protected <R> T executeNon(R obj, IRunApi<R> api){
        RunUtil.executeNon(obj, api);
        return getThis();
    }

    protected <R> T execute(List<R> data, IRunApi<R> runApi){
        RunUtil.execute(data, runApi);
        return getThis();
    }

    protected <I, R> R getExecute(I in, R def, IRAction<I, R> action){
        if(in != null && action != null){
            return action.action(in);
        }
        return def;
    }

}

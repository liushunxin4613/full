package com.leo.core.api.core;

import com.leo.core.iapi.IReturnApi;
import com.leo.core.iapi.IRunApi;
import com.leo.core.iapi.core.IThisApi;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

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

    protected <R> T execute(R[] args, IRunApi<R> runApi){
        RunUtil.execute(TextUtils.getListData(args), runApi);
        return getThis();
    }

    protected <R> T execute(IRunApi<R> runApi, R... args){
        RunUtil.execute(TextUtils.getListData(args), runApi);
        return getThis();
    }

    protected <A, B> B getExecute(A in, B def, IReturnApi<A, B> api){
        if(in != null && api != null){
            return api.execute(in);
        }
        return def;
    }

    protected <A, B> B getExecute(A in, IReturnApi<A, B> api){
        return getExecute(in, null, api);
    }

    protected <A, B> B no(A a, IReturnApi<A, B> api){
        return (a == null || api == null) ? null : api.execute(a);
    }

}

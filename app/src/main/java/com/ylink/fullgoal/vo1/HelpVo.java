package com.ylink.fullgoal.vo1;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.IReturnAction;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法帮助
 */
public class HelpVo {

    protected <B> void execute(List<B> data, IObjAction<B> action) {
        RunUtil.execute(data, action);
    }

    protected <A> List<A> getExecute(IObjAction<List<A>> action) {
        if (action != null) {
            List<A> data = new ArrayList<>();
            action.execute(data);
            return data;
        }
        return null;
    }

    protected <A, B> List<B> getExecute(List<A> data, IReturnAction<A, B> action) {
        if (!TextUtils.isEmpty(data) && action != null) {
            List<B> dat = new ArrayList<>();
            execute(data, item -> dat.add(action.execute(item)));
            return dat;
        }
        return null;
    }

    protected <A> A getExecute(A a, A b) {
        return a == null ? b : a;
    }

    protected <B> void execute(IObjAction<B> action, List<B>... args) {
        if (action != null && !TextUtils.isEmpty(args)) {
            for (List<B> data : args) {
                RunUtil.execute(data, action);
            }
        }
    }

    protected <A, B> B getExecute(A in, B def, IReturnAction<A, B> api) {
        if (in != null && api != null) {
            return api.execute(in);
        }
        return def;
    }

    protected <A, B> B getExecute(A in, IReturnAction<A, B> api) {
        return getExecute(in, null, api);
    }

    protected void tryData(List... args) {
        for (int i = 0; i < args.length; i++) {
            if(args[i] == null){
                args[i] = new ArrayList();
            }
        }
    }

}

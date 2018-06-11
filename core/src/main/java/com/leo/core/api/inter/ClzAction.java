package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.TextUtils;

public class ClzAction<A> {

    private transient Class<A> clz;
    private transient IObjAction<A> action;

    public ClzAction(Class<A> clz, IObjAction<A> action) {
        this.clz = clz;
        this.action = action;
    }

    public void execute(Object obj) {
        if (TextUtils.check(obj, clz, action)
                && clz.isInstance(obj)) {
            action.execute((A) obj);
        }
    }

}
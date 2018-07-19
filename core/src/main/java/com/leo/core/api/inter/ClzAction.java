package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IMapAction;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;

import java.util.Map;

public class ClzAction<A> {

    private transient Class<A> clz;
    private transient IMapAction<Map, A> action;

    public ClzAction(Class<A> clz, IMapAction<Map, A> action) {
        this.clz = clz;
        this.action = action;
    }

    public void execute(Map map, Object obj) {
        if (TextUtils.check(obj, clz, action)
                && clz.isInstance(obj)) {
            action.execute(map, (A) obj);
        }
    }

}
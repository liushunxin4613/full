package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IObj;
import com.leo.core.util.GsonDecodeUtil;

public class Obj<A> extends Show implements IObj<A> {

    private A obj;

    @Override
    public A getObj() {
        return obj;
    }

    @Override
    public String getShow() {
        String show = super.getShow();
        if (show != null) {
            return show;
        }
        return GsonDecodeUtil.encode(getObj());
    }

    public void setObj(A obj) {
        this.obj = obj;
    }

}

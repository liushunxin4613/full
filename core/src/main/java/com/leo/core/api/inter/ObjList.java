package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IObjList;
import com.leo.core.util.GsonDecodeUtil;

import java.util.List;

public class ObjList<O, D> extends Show implements IObjList<O, D> {

    private O obj;
    private List<D> data;

    @Override
    public List<D> getData() {
        return data;
    }

    @Override
    public O getObj() {
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

    public void setObj(O obj) {
        this.obj = obj;
    }

    public void setData(List<D> data) {
        this.data = data;
    }

}

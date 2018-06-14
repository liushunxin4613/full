package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IList;

import java.util.List;

public class ListData<A> extends Show implements IList<A> {

    private List<A> data;

    @Override
    public List<A> getData() {
        return data;
    }

    public void setData(List<A> data) {
        this.data = data;
    }

}

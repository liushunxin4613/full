package com.leo.core.api.inter;

import com.leo.core.iapi.inter.IShow;

public class Show extends Code implements IShow{

    private String show;

    @Override
    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

}

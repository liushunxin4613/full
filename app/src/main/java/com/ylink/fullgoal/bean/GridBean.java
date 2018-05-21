package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

import java.util.List;

public class GridBean extends BaseApiBean {

    public final static int API_TYPE = R.layout.l_grid;

    @Override
    public Integer getApiType() {
        return API_TYPE;
    }

    private List<? extends BaseApiBean> data;

    public GridBean() {
    }

    public GridBean(List<? extends BaseApiBean> data) {
        this.data = data;
    }

    public void setData(List<? extends BaseApiBean> data) {
        this.data = data;
    }

    public List<? extends BaseApiBean> getData() {
        return data;
    }

}

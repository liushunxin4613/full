package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.main.IApiBean;
import com.ylink.fullgoal.R;

import java.util.List;

public class GridBean extends BaseApiBean {

    public final static int API_TYPE = R.layout.l_grid;

    @Override
    public Integer getApiType() {
        return API_TYPE;
    }

    private List<? extends IApiBean> data;

    public GridBean() {
    }

    public GridBean(List<? extends IApiBean> data) {
        this.data = data;
    }

    public void setData(List<? extends IApiBean> data) {
        this.data = data;
    }

    public List<? extends IApiBean> getData() {
        return data;
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.main.IApiBean;
import com.ylink.fullgoal.R;

import java.util.List;

public class GridBean extends BaseApiBean {

    public final static int LAYOUT_RES_ID = R.layout.l_grid;

    @Override
    public Integer getApiType() {
        return LAYOUT_RES_ID;
    }

    private List<IApiBean> data;

    public GridBean(List<IApiBean> data) {
        this.data = data;
    }

    public void setData(List<IApiBean> data) {
        this.data = data;
    }

    public List<IApiBean> getData() {
        return data;
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

public class SelectedTvBean extends BaseApiBean{

    @Override
    public Integer getApiType() {
        return R.layout.l_selected_tv;
    }

    private String name;

    public SelectedTvBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

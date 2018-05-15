package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.R;

public class IndicatorBean extends BaseApiBean{

    @Override
    public Integer getApiType() {
        return R.layout.l_indicator_item;
    }

    private String name;

    public IndicatorBean(IControllerApi api, String name) {
        super(api);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

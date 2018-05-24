package com.ylink.fullgoal.bean;

import com.leo.core.iapi.IObjAction;
import com.ylink.fullgoal.R;

public class TvSBean extends ApiBean<TvSBean> {

    public TvSBean(String name) {
        super(name);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv_s;
    }

    private transient IObjAction<String> textApi;

    public IObjAction<String> getTextApi() {
        return textApi;
    }

    public void setTextApi(IObjAction<String> textApi) {
        this.textApi = textApi;
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.iapi.IRunApi;
import com.ylink.fullgoal.R;

public class TvSBean extends ApiBean<TvSBean> {

    public TvSBean(String name) {
        super(name);
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_tv_s;
    }

    private transient IRunApi<String> textApi;

    public IRunApi<String> getTextApi() {
        return textApi;
    }

    public void setTextApi(IRunApi<String> textApi) {
        this.textApi = textApi;
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvH2SBean extends ApiBean<TvH2SBean> {

    private transient boolean selected;

    @Override
    public Integer getApiType() {
        return R.layout.l_h_tv2_more_s;
    }

    public TvH2SBean(String name, String detail) {
        super(name, detail);
    }

    public TvH2SBean(String name, String detail, OnBVClickListener<TvH2SBean> listener) {
        super(name, detail, listener);
    }

    public TvH2SBean(String name, String detail, boolean selected, OnBVClickListener<TvH2SBean> listener) {
        super(name, detail, listener);
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

}
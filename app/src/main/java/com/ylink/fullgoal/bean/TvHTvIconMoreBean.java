package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvHTvIconMoreBean extends ApiBean<TvHTvIconMoreBean> {

    private transient IObjAction<String> action;

    public TvHTvIconMoreBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        this.action = action;
    }

    public TvHTvIconMoreBean(Integer iconResId, String name, String detail, String hint,
                             OnBVClickListener<TvHTvIconMoreBean> listener, IObjAction<String> action) {
        super(iconResId, name, detail, hint, listener);
        this.action = action;
    }

    @Override
    public void setDetail(String detail) {
        super.setDetail(detail);
        if (action != null) {
            action.execute(getDetail());
        }
    }

    @Override
    public Integer getApiType() {
        return getEnableLayoutResId(R.layout.l_h_tv_tv_more);
    }

}

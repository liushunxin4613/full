package com.ylink.fullgoal.bean;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.inter.OnBVClickListener;
import com.ylink.fullgoal.R;

public class TvHEtIconMoreBean extends ApiBean<TvHEtIconMoreBean> {

    private transient IObjAction<String> action;

    public TvHEtIconMoreBean(String name, String detail, String hint, IObjAction<String> action) {
        super(name, detail, hint);
        this.action = action;
    }

    public TvHEtIconMoreBean(Integer iconResId, String name, String detail, String hint,
                             OnBVClickListener<TvHEtIconMoreBean> listener, IObjAction<String> action) {
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
        return getEnableLayoutResId(R.layout.l_h_tv_et_more);
    }

}

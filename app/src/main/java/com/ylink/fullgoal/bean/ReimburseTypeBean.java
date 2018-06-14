package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.RunUtil;
import com.ylink.fullgoal.R;

public class ReimburseTypeBean extends ApiBean<ReimburseTypeBean> {

    private transient IObjAction<String> action;
    private transient View selectedView;

    public ReimburseTypeBean(String name, String detail, IObjAction<String> action) {
        super(name, detail);
        this.action = action;
    }

    @Override
    public Integer getApiType() {
        return R.layout.l_sx_bottom;
    }

    public void execute(View view, String text) {
        this.selectedView = view;
        RunUtil.executeNon(text, action);
    }

    public void clean() {
        if (selectedView != null) {
            selectedView.setSelected(false);
            selectedView = null;
        }
        if (action != null) {
            action.execute(null);
        }
    }

}

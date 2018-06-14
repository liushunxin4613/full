package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.RunUtil;
import com.ylink.fullgoal.R;

public class SelectedTvBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_selected_tv;
    }

    private String name;
    private transient IObjAction<String> action;
    private transient IObjAction<View> selectedAction;

    public SelectedTvBean(String name, IObjAction<String> action, IObjAction<View> selectedAction) {
        this.name = name;
        this.action = action;
        this.selectedAction = selectedAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void execute(View selected, String text) {
        RunUtil.executeNon(selected, selectedAction);
        RunUtil.executeNon(text, action);
    }

}

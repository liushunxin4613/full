package com.ylink.fullgoal.bean;

import android.view.View;
import android.widget.TextView;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import static com.leo.core.util.TextUtils.check;

public class SelectedTvBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_selected_tv;
    }

    private String name;
    private transient TextView textView;
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
        if(action != null){
            action.execute(text);
        }
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void updateSelected(boolean selected) {
        if(check(getTextView())){
            getTextView().setSelected(selected);
        }
    }

}
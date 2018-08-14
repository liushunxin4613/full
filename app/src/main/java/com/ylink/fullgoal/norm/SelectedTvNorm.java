package com.ylink.fullgoal.norm;

import android.view.View;
import android.widget.TextView;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.RunUtil;
import com.ylink.fullgoal.api.item.SelectedTvControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

import static com.leo.core.util.TextUtils.check;

public class SelectedTvNorm extends SurfaceNorm<SelectedTvNorm, SurfaceControllerApi>{

    @Override
    public Class<? extends IControllerApi> getControllerApiClass() {
        return SelectedTvControllerApi.class;
    }

    private String name;
    private transient TextView textView;
    private transient IObjAction<String> action;
    private transient IObjAction<View> selectedAction;

    public SelectedTvNorm(String name, IObjAction<String> action, IObjAction<View> selectedAction) {
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
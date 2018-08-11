package com.ylink.fullgoal.norm;

import android.view.View;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.api.item.DateArrayControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceNorm;

import java.util.ArrayList;
import java.util.List;

public class DateArrayNorm extends SurfaceNorm<DateArrayNorm, SurfaceControllerApi> {

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new DateArrayControllerApi(controller);
    }

    private String name;
    private transient int iconResId;
    private List<SelectedTvNorm> data;
    private transient View selectedView;
    private transient IObjAction<String> action;

    public DateArrayNorm(String name, List<String> data, IObjAction<String> action) {
        this.name = name;
        this.action = action;
        setData(data);
    }

    public void update(String selected) {
        RunUtil.execute(getData(), obj -> obj.updateSelected(TextUtils.equals(obj.getName(),
                selected)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectedTvNorm> getData() {
        return data;
    }

    public void setData(List<String> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new ArrayList<>();
            for (String item : data) {
                this.data.add(new SelectedTvNorm(item, action, this::setSelectedView));
            }
        }
    }

    private void setSelectedView(View view) {
        this.selectedView = view;
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

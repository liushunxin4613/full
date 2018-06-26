package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.inter.IObjAction;
import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bi.DateArrayBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.SurfaceBiBean;

import java.util.ArrayList;
import java.util.List;

public class DateArrayBean extends SurfaceBiBean<DateArrayBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, DateArrayBean> newDefApi() {
        return new DateArrayBi();
    }

    private String name;
    private List<SelectedTvBean> data;
    private transient IObjAction<String> action;
    private transient View selectedView;

    public DateArrayBean(String name, List<String> data, IObjAction<String> action) {
        this.name = name;
        this.action = action;
        setData(data);
    }

    public void update(String selected) {
        RunUtil.execute(getData(), obj -> obj.updateSelected(TextUtils.equals(obj.getName(), selected)));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectedTvBean> getData() {
        return data;
    }

    public void setData(List<String> data) {
        if (data == null) {
            this.data = null;
        } else {
            this.data = new ArrayList<>();
            for (String item : data) {
                this.data.add(new SelectedTvBean(item, action, this::setSelectedView));
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

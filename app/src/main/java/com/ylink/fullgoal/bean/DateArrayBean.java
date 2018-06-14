package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.iapi.inter.IObjAction;
import com.ylink.fullgoal.R;

import java.util.ArrayList;
import java.util.List;

public class DateArrayBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_date_array;
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
        if(selectedView != null){
            selectedView.setSelected(false);
            selectedView = null;
        }
        if (action != null) {
            action.execute(null);
        }
    }

}

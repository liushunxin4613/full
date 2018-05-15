package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
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

    public DateArrayBean(String name, List<String> data) {
        this.name = name;
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
                this.data.add(new SelectedTvBean(item));
            }
        }
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

public class TvH4Bean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return isTitle ? R.layout.l_process_title : R.layout.l_process;
    }

    private String name;
    private String detail;
    private String type;
    private String time;
    private transient boolean isTitle;

    public TvH4Bean() {
        this.isTitle = true;
    }

    public TvH4Bean(String name, String detail, String type, String time) {
        this.isTitle = false;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

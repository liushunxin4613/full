package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

/**
 * 出差申请单
 */
public class CCSQDBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_ccsqd;
    }

    private String name;
    private String detail;
    private String start;
    private String end;

    public CCSQDBean(String name, String detail, String start, String end) {
        this.name = name;
        this.detail = detail;
        this.start = start;
        this.end = end;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}

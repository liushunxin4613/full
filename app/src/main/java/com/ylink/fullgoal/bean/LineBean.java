package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

public class LineBean extends BaseApiBean {

    public final static int NORMAL = R.layout.v_line;
    public final static int SMALL = R.layout.v_line2;

    private int apiType = NORMAL;

    @Override
    public Integer getApiType() {
        return apiType;
    }

    public LineBean() {
    }

    public LineBean(int type) {
        switch (type){
            default:
            case NORMAL:
                apiType = NORMAL;
                break;
            case SMALL:
                apiType = SMALL;
                break;
        }
    }

}

package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.ylink.fullgoal.R;

public class ImageBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_ps_image;
    }

    private Object path;

    public ImageBean(Object path) {
        this.path = path;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

}

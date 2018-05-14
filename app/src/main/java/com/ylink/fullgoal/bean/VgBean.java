package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.util.LogUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import java.util.ArrayList;
import java.util.List;

public class VgBean extends BaseApiBean{

    @Override
    public Integer getApiType() {
        return R.layout.l_vg;
    }

    private List<BaseApiBean> data;

    public VgBean(List<BaseApiBean> data) {
        setData(data, 0);
    }

    public VgBean(List<BaseApiBean> data, int type) {
        setData(data, type);
    }

    public List<BaseApiBean> getData() {
        return data;
    }

    public void setData(List<BaseApiBean> data, int type) {
        int count = TextUtils.count(data);
        if(count > 0){
            this.data = new ArrayList<>();
            for (int i = 0; i < count - 1; i++) {
                this.data.add(data.get(i));
                this.data.add(new LineBean(type));
            }
            this.data.add(data.get(count - 1));
        } else {
            this.data = data;
        }
    }

}

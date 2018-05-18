package com.ylink.fullgoal.bean;

import com.leo.core.bean.BaseApiBean;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;

import java.util.ArrayList;
import java.util.List;

public class VgBean extends BaseApiBean {

    @Override
    public Integer getApiType() {
        return R.layout.l_vg;
    }

    private int type;
    private List<BaseApiBean> data;

    public VgBean(List<BaseApiBean> data) {
        this.data = data;
    }

    public VgBean(List<BaseApiBean> data, int type) {
        this.data = data;
        this.type = type;
    }

    public List<BaseApiBean> getLineData() {
        if (!TextUtils.isEmpty(getData())) {
            List<BaseApiBean> dat = new ArrayList<>();
            int count = TextUtils.count(getData());
            for (int i = 0; i < count - 1; i++) {
                dat.add(getData().get(i));
                dat.add(new LineBean(type));
            }
            dat.add(getData().get(count - 1));
            return dat;
        }
        return null;
    }

    public List<BaseApiBean> getData() {
        return data;
    }

    public void setData(List<BaseApiBean> data, int type) {
        this.data = data;
        this.type = type;
    }

    public void add(int index, BaseApiBean bean) {
        if (index >= 0 && bean != null && data != null) {
            data.add(index, bean);
        }
    }

    public void add(BaseApiBean bean) {
        if (bean != null && data != null) {
            data.add(bean);
        }
    }

    public int indexOf(BaseApiBean bean) {
        if (bean != null && !TextUtils.isEmpty(getData())) {
            return getData().indexOf(bean);
        }
        return -1;
    }

}

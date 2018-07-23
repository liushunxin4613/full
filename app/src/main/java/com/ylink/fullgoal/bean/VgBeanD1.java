package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bi.VgBiD1;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.core.SurfaceBiBean;

import java.util.ArrayList;
import java.util.List;

public class VgBeanD1 extends SurfaceBiBean<VgBeanD1> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, VgBeanD1> newDefApi() {
        return new VgBiD1();
    }

    private int type;
    private List<BaseBiBean> data;
    private transient View.OnClickListener onClickListener;

    public VgBeanD1(List<BaseBiBean> data) {
        this.data = data;
    }

    public VgBeanD1(List<BaseBiBean> data, int type) {
        this.data = data;
        this.type = type;
    }

    public List<BaseBiBean> getLineData() {
        if (!TextUtils.isEmpty(getData())) {
            List<BaseBiBean> dat = new ArrayList<>();
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

    public List<BaseBiBean> getData() {
        return data;
    }

    public void setData(List<BaseBiBean> data, int type) {
        this.data = data;
        this.type = type;
    }

    public void add(int index, BaseBiBean bean) {
        if (index >= 0 && bean != null && data != null) {
            data.add(index, bean);
        }
    }

    public void add(BaseBiBean bean) {
        if (bean != null && data != null) {
            data.add(bean);
        }
    }

    public void remove(BaseBiBean bean) {
        if (bean != null && data != null) {
            data.remove(bean);
        }
    }

    public int indexOf(BaseBiBean bean) {
        if (bean != null && !TextUtils.isEmpty(getData())) {
            return getData().indexOf(bean);
        }
        return -1;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public VgBeanD1 setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

}
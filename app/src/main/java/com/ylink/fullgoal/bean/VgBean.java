package com.ylink.fullgoal.bean;

import android.view.View;

import com.leo.core.iapi.main.IBindControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bi.VgBi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;
import com.ylink.fullgoal.core.BaseBiBean;
import com.ylink.fullgoal.core.SurfaceBiBean;

import java.util.ArrayList;
import java.util.List;

public class VgBean extends SurfaceBiBean<VgBean> {

    @Override
    protected IBindControllerApi<SurfaceControllerApi, VgBean> newDefApi() {
        return new VgBi();
    }

    private int type;
    private boolean noLine;
    private List<BaseBiBean> data;
    private transient View.OnClickListener onClickListener;

    public VgBean(List<BaseBiBean> data) {
        this.data = data;
    }

    public VgBean(List<BaseBiBean> data, int type) {
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

    public VgBean setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public boolean isNoLine() {
        return noLine;
    }

    public void setNoLine(boolean noLine) {
        this.noLine = noLine;
    }

}
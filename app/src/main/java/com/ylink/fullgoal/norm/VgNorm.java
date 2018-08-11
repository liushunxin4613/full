package com.ylink.fullgoal.norm;

import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.item.VgControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

import java.util.List;

public class VgNorm extends OnClickNorm<VgNorm, SurfaceControllerApi> {

    public final static int LAYOUT_LINE_RES_ID = R.layout.l_vg;
    private final static int LAYOUT_NONE_RES_ID = R.layout.l_vg_d1;
    public final static int LINE_NORMAL = R.layout.v_line;
    public final static int LINE_SMALL = R.layout.v_line2;

    @Override
    protected IControllerApi createControllerApi(SurfaceControllerApi controllerApi, Object controller) {
        return new VgControllerApi(controller);
    }

    private List<INorm> data;
    private Integer lineLayoutResId;

    public VgNorm(List<INorm> data) {
        this.setData(data, LAYOUT_NONE_RES_ID);
    }

    public VgNorm(List<INorm> data, int type) {
        this.setData(data, type);
    }

    public List<INorm> getData() {
        return data;
    }

    public int getCount(){
        return TextUtils.count(getData());
    }

    public INorm getChildAt(int position){
        return (position >= 0 && position < getCount()) ? getData().get(position) : null;
    }

    private void setData(List<INorm> data, int layoutResId) {
        this.data = data;
        this.setApiType(layoutResId);
    }

    public void add(int index, INorm bean) {
        if (index >= 0 && bean != null && getData() != null) {
            getData().add(index, bean);
        }
    }

    public void add(INorm bean) {
        if (bean != null && getData() != null) {
            getData().add(bean);
        }
    }

    public void remove(INorm bean) {
        if (bean != null && getData() != null) {
            getData().remove(bean);
        }
    }

    public int indexOf(INorm bean) {
        if (bean != null && !TextUtils.isEmpty(getData())) {
            return getData().indexOf(bean);
        }
        return -1;
    }

    public Integer getLineLayoutResId() {
        return lineLayoutResId;
    }

    public VgNorm setLineLayoutResId(Integer lineLayoutResId) {
        this.lineLayoutResId = lineLayoutResId;
        return getThis();
    }

}
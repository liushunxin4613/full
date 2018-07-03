package com.ylink.fullgoal.bi;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.core.SurfaceBi;

public class IndicatorBi extends SurfaceBi<IndicatorBi, IndicatorBean> {

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_indicator_item;
    }

}
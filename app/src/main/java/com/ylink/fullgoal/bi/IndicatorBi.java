package com.ylink.fullgoal.bi;

import android.widget.TextView;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.IndicatorBean;
import com.ylink.fullgoal.core.SurfaceBi;

import butterknife.Bind;

public class IndicatorBi extends SurfaceBi<IndicatorBi, IndicatorBean> {

    @Bind(R.id.name_tv)
    TextView nameTv;

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_indicator_item;
    }

}
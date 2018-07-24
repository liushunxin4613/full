package com.ylink.fullgoal.bi;

import android.support.annotation.NonNull;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.api.surface.GridRecycleControllerApi;
import com.ylink.fullgoal.bean.GridBean;
import com.ylink.fullgoal.bean.GridPhotoBean;
import com.ylink.fullgoal.core.BaseBi;

public class GridBi extends BaseBi<GridBi, GridBean, GridRecycleControllerApi> {

    @Override
    public Integer getDefLayoutResId() {
        return R.layout.l_grid;
    }

    @Override
    public void updateBind(@NonNull GridRecycleControllerApi api, @NonNull GridBean bean) {
        super.updateBind(api, bean);
        api.replaceAll(bean.getData(), obj -> {
            if (obj instanceof GridPhotoBean) {
                ((GridPhotoBean) obj).setUnit(api.getUnit());
            }
        });
    }

}
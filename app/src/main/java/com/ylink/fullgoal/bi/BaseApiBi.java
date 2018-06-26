package com.ylink.fullgoal.bi;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.ApiBean;
import com.ylink.fullgoal.core.SurfaceBi;

public abstract class BaseApiBi<T extends BaseApiBi, A extends ApiBean> extends SurfaceBi<T, A> {

    @Override
    public Integer getDefLayoutResId() {
        ApiBean bean = bean();
        if(bean != null){
            if(bean.isEnable()){
                if(bean.isEtEnable()){
                    return getEtEnableDefLayoutResId();
                }
                return getEnableDefLayoutResId();
            }
        }
        return getNoneDefLayoutResId();
    }

    protected Integer getEnableDefLayoutResId(){
        return null;
    }

    protected Integer getEtEnableDefLayoutResId(){
        return null;
    }

    protected Integer getNoneDefLayoutResId(){
        return R.layout.l_h_tv2_more_s;
    }

}
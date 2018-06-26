package com.ylink.fullgoal.core;

import com.leo.core.core.bean.CoreBiBean;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.core.SurfaceControllerApi;

public abstract class BaseBiBean<T extends BaseBiBean, A extends IControllerApi> extends CoreBiBean
        <T, A, SurfaceControllerApi> {
}
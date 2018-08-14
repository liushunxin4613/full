package com.leo.core.api.core;

import android.support.annotation.NonNull;

import com.leo.core.core.Model;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;

public abstract class CoreModel extends Model {

    /**
     * 创建INorm
     *
     * @param controllerApi controllerApi
     */
    protected abstract INorm createNorm(@NonNull IControllerApi controllerApi);

    @Override
    public void initNorm() {
        super.initNorm();
        IControllerApi api = parentControllerApi();
        if(api != null){
            INorm norm = createNorm(api);
            if (norm != null) {
                norm.setParentControllerApi(api);
                norm.setController(getController());
                norm.setSelectedApiCode(getSelectedApiCode());
                norm.setApiCode(getApiCode());
                setNorm(norm);
            }
        }
    }

}
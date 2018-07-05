package com.leo.core.api.main;

import com.leo.core.api.api.VsApi;
import com.leo.core.iapi.main.IControllerApi;
import com.leo.core.iapi.main.IHasControllerApi;

public class HasControllerApi<T extends HasControllerApi> extends VsApi<T> implements IHasControllerApi<T> {

    private IControllerApi controllerApi;

    public HasControllerApi(IControllerApi controllerApi) {
        this.controllerApi = controllerApi;
    }

    @Override
    public IControllerApi controllerApi() {
        return controllerApi;
    }

}

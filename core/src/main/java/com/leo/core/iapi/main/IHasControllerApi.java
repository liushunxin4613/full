package com.leo.core.iapi.main;

import com.leo.core.iapi.core.IApi;

public interface IHasControllerApi<T extends IHasControllerApi> extends IApi {

    IControllerApi controllerApi();

}

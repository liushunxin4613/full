package com.leo.core.iapi.main;

/**
 * AFV Api
 * @param <T> 本身
 */
public interface IAFVApi<T extends IAFVApi, C extends IControllerApi> {

    /**
     * 控制器Api
     * @return 控制器Api
     */
    IControllerApi<C, T> controllerApi();

    /**
     * 新建控制器Api
     * @return 控制器Api
     */
    IControllerApi<C, T> newControllerApi();

}

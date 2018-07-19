package com.leo.core.iapi.main;

/**
 * AFV Api
 *
 * @param <T> 本身
 */
public interface IAFVApi<T extends IAFVApi, C extends IControllerApi> {

    /**
     * 控制器Api
     *
     * @return 控制器Api
     */
    IControllerApi<C, T> controllerApi();

    /**
     * 设置新建控制器Api
     */
    void setControllerApi(IControllerApi<C, T> api);

    /**
     * 新建控制器Api
     *
     * @return 控制器Api
     */
    IControllerApi<C, T> newControllerApi();

}

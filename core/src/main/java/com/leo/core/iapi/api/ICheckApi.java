package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface ICheckApi<T extends ICheckApi, D> extends IApi {

    /**
     * 数据比较
     *
     * @param obj 数据
     * @return true为相同, false为不同
     */
    boolean check(D obj);

}

package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface IApiCodeApi extends IApi {

    /**
     * 编号,数据对比使用[可用于标记和过滤]
     */
    String getApiCode();

}

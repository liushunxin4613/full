package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

/**
 * 搜索关键字接口
 */
public interface IKeywordApi extends IApi {

    /**
     * 搜索时关键字生效参数
     *
     * @return 生效参数
     */
    String getKeyword();

    /**
     * 搜索时过滤参数
     *
     * @return 过滤参数
     */
    String getFilter();

}

package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface IGalleryApi<T extends IGalleryApi> extends IApi {

    /**
     * 打开选择器
     */
    T openImageSelector();

}

package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface INotifyDataSetChangedApi<T extends INotifyDataSetChangedApi> extends IApi {

    /**
     * 刷新
     */
    void notifyDataSetChanged();

}

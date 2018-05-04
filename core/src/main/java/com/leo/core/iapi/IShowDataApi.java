package com.leo.core.iapi;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.main.IApiBean;

public interface IShowDataApi {

    DataApi<DataApi, IApiBean> adapterDataApi();

    DataApi<DataApi, IApiBean> newAdapterDataApi();

}

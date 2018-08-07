package com.leo.core.iapi.api;

import com.leo.core.api.main.DataApi;
import com.leo.core.iapi.core.IMNApi;
import com.leo.core.iapi.core.IModel;
import com.leo.core.iapi.main.IApiBean;

public interface IShowDataApi {

    DataApi<DataApi, IMNApi> adapterDataApi();

    DataApi<DataApi, IMNApi> newAdapterDataApi();

}

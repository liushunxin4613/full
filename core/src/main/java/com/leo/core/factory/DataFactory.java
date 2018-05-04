package com.leo.core.factory;

import com.leo.core.api.DataApi;
import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.IDataApi;
import com.leo.core.util.ClassBindUtil;

public class DataFactory<T extends DataFactory> extends ThisApi<T> {

    private static DataFactory instance;

    public static DataFactory getInstance() {
        if (instance == null) {
            synchronized (DataFactory.class) {
                if (instance == null) {
                    instance = new DataFactory();
                }
            }
        }
        return instance;
    }

    private IDataApi api;

    private DataFactory() {
        api = ClassBindUtil.getApi(DataApi.class);
        if(api != null){
            api.switchDefault();
        }
    }

    public static IDataApi getApi() {
        return getInstance().api;
    }

}

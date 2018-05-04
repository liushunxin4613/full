package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IDataTypeApi<T extends IDataTypeApi> extends IApi {

    /**
     * string数据格式化
     * @param format format
     * @param args args
     * @return string
     */
    String formatString(String format, Object... args);

}

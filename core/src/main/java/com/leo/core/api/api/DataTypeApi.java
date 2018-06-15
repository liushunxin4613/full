package com.leo.core.api.api;

import com.leo.core.api.core.ThisApi;
import com.leo.core.iapi.api.IDataTypeApi;
import com.leo.core.util.TextUtils;

public class DataTypeApi<T extends DataTypeApi> extends ThisApi<T> implements IDataTypeApi<T> {

    @Override
    public String formatString(String format, Object... args) {
        if(TextUtils.isEmits(format, args)){
            return String.format(format, args);
        }
        return format;
    }

}

package com.leo.core.api.core;

import android.support.annotation.NonNull;
import com.leo.core.iapi.core.IGetBind;
import java.util.List;

/**
 * 绑定对象
 * @param <T> 本身
 * @param <B> 绑定对象
 */
public abstract class GetBindApi<T extends GetBindApi, B> extends ThisApi<T> implements IGetBind<T, B> {
    @NonNull
    @Override
    public abstract List<B> getBindData();
}

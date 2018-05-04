package com.leo.core.iapi;

import android.content.Context;
import android.support.annotation.NonNull;

import com.leo.core.iapi.core.IThisApi;

/**
 * 所有绑定Context类母接口
 */
public interface IBindContextApi<T extends IBindContextApi> extends IThisApi<T> {

    /**
     * 绑定
     * <br>
     *     此方法应当在所有方法前
     * @param context 上下文
     * @return 本身
     */
    T bind(@NonNull Context context);

    /**
     * 上下文对象
     */
    Context getContext();

}

package com.leo.core.iapi.core;

import android.support.annotation.NonNull;

/**
 * 绑定类接口,一个绑定到多个,一对多
 * <br>
 * bind和attach是相对的,前者是一对多,可以绑定到多个,后这是多对一,有且只能附加到一个
 * 类比Activity和Fragment时为Activity
 *
 * @param <T> 本身
 * @param <B> 被绑定数据
 */
public interface IBindApi<T extends IBindApi, B> extends IGetBind<T, B>, IThisApi<T> {

    /**
     * 绑定数据
     *
     * @param bind 被绑定数据
     * @return 本身
     */
    T bind(@NonNull B bind);

    /**
     * 解除绑定数据
     *
     * @param bind 被绑定数据
     * @return 本身
     */
    T unBind(B bind);

    /**
     * 添加bind回调
     */
    void onBind();

}

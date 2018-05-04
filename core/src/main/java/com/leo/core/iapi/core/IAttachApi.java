package com.leo.core.iapi.core;

import android.support.annotation.NonNull;

/**
 * 附加接口,多个附加到一个被附加对象上,多对一
 * <br>
 * bind和attach是相对的,前者是一对多,可以绑定到多个,后这是多对一,有且只能附加到一个
 * 类比Activity和Fragment时为Fragment
 *
 * @param <T> 本身
 * @param <A> 被附加对象
 */
public interface IAttachApi<T extends IAttachApi, A> extends IGetAttachApi<T, A> {

    /**
     * 附加到对象
     *
     * @param obj 被附加对象
     * @return 本身
     */
    T attach(@NonNull A obj);

    /**
     * 解除附加
     *
     * @return 本身
     */
    T unAttach();

}

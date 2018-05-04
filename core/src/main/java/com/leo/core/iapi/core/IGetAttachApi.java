package com.leo.core.iapi.core;

/**
 * 获取附加接口,多以一,有且只有一个
 *
 * @param <T>
 * @param <A>
 */
public interface IGetAttachApi<T extends IGetAttachApi, A> extends IApi {

    /**
     * 获取被附加对象
     *
     * @return 被附加对象
     */
    A getAttach();

}

package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

/**
 * class 接口
 */
public interface IObjectApi<C extends IObjectApi> extends IApi {

    /**
     * 获取class
     *
     * @param name class名称
     * @return class
     */
    Class getClass(String name);

    /**
     * 获取无构造参数对象
     *
     * @param name 类名称
     * @return 对象
     */
    Object getObject(String name);

    /**
     * 获取无构造参数对象
     *
     * @param clz 类
     * @param <T> 类返现
     * @return 对象
     */
    <T> T getObject(Class<T> clz);

    /**
     * 获取无构造参数对象
     *
     * @param name 类名
     * @param pClz 父类
     * @param <P>  父类泛型
     * @return
     */
    <P> P getObject(String name, Class<P> pClz);

    /**
     * 获取有参构造参数对象
     *
     * @param clz 类
     * @param cs  参数类
     * @param os  参数对象
     * @param <T> 类泛型
     * @return 对象
     */
    <T> T getObject(Class<T> clz, Class[] cs, Object[] os);

    /**
     * 获取有参构造参数对象
     *
     * @param clz 类
     * @param cs  参数类
     * @param os  参数对象
     * @param <T> 类泛型
     * @return 对象
     */
    <T> T getObject(Class<T> clz, Class cs, Object os);

    /**
     * 获取有参构造参数对象
     *
     * @param name 类名称
     * @param cs   参数类
     * @param os   参数对象
     * @return 对象
     */
    Object getObject(String name, Class[] cs, Object[] os);

    /**
     * 获取有参构造参数对象
     *
     * @param name 类名称
     * @param cs   参数类
     * @param os   参数对象
     * @return 对象
     */
    Object getObject(String name, Class cs, Object os);

    /**
     * 获取有参构造参数对象
     *
     * @param name 类名称
     * @param pClz 父类
     * @param cs   参数类
     * @param os   参数对象
     * @param <P>  父类泛型
     * @return 对象
     */
    <P> P getObject(String name, Class<P> pClz, Class[] cs, Object[] os);

    /**
     * 获取有参构造参数对象
     *
     * @param name 类名称
     * @param pClz 父类
     * @param cs   参数类
     * @param os   参数对象
     * @param <P>  父类泛型
     * @return 对象
     */
    <P> P getObject(String name, Class<P> pClz, Class cs, Object os);

}

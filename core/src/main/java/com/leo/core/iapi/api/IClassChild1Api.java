package com.leo.core.iapi.api;

public interface IClassChild1Api<T extends IClassChild1Api> extends IClassApi<T, Object, String, String> {

    /**
     * 获取数据类名
     *
     * @param obj     对象
     * @param exclude 排除
     * @return 类名
     */
    @Override
    String getClassName(Object obj, String exclude);

    /**
     * 获取数据类名
     *
     * @param clz     类
     * @param exclude 排除
     * @return 类名
     */
    String getClassName(Class clz, String exclude);

    /**
     * 获取数据类名
     *
     * @param obj 对象
     * @return 类名
     */
    String getClassName(Object obj);

    /**
     * 获取数据类名
     *
     * @param clz 类
     * @return 类名
     */
    String getClassName(Class clz);

}

package cn.com.fullgoal.pt0001.test;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IObjAction;

import java.util.Map;

/**
 * 流程
 */
public interface IProcess<T extends IProcess> extends IApi {

    /**
     * 添入数据
     *
     * @param key key
     * @param obj obj
     * @return 本身
     */
    T put(String key, Object obj);

    /**
     * 获取数据
     *
     * @param key key
     * @return 数据
     */
    Object get(String key);

    /**
     * 获取数据
     *
     * @param key key
     * @param clz clz
     * @param <V> V
     * @return V
     */
    <V> V get(String key, Class<V> clz);

    /**
     * 执行
     *
     * @param key    key
     * @param clz    clz
     * @param action action
     * @param <V>    V
     * @return V
     */
    <V> T execute(String key, Class<V> clz, IObjAction<V> action);

    /**
     * 添加结束action
     *
     * @param action action
     */
    T addCompleteAction(IObjAction<Map<String, Object>> action);

    /**
     * 添加action
     *
     * @param action action
     */
    T push(IProcessAction action);

    /**
     * 添加action
     *
     * @param action action
     */
    T pushFooter(IProcessAction action);

    /**
     * 下一个
     */
    T next();

    /**
     * 完成
     */
    T complete();

}
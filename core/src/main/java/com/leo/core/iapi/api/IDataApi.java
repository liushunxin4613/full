package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

import java.util.List;
import java.util.Map;

/**
 * 单键值表数据读取接口
 * <br>
 *     仅有增(改),删,查三项功能,仅支持单键查询
 * @param <T> 本身
 */
public interface IDataApi<T extends IDataApi> extends IApi {

    /**
     * 获取表名称
     */
    String getTable();

    /**
     * 切换默认表结构
     * @return 本身
     */
    T switchDefault();

    /**
     * 切换表结构
     * <br>
     *     此方法应当在bind后,在其他所有方法前
     * @param key 表名
     * @return 本身
     */
    T switchTable(String key);

    /**
     * 切换表结构
     * <br>
     *     此方法应当在bind后,在其他所有方法前
     * @param key 表名
     * @param mode 数据存储类型
     * @return 本身
     */
    T switchTable(String key, int mode);

    /**
     * 存储单个数据
     * @param key 键
     * @param value 数据
     * @param <V> 数据类型
     * @return 本身
    */
    <V> T saveData(String key, V value);

    /**
     * 存储单个数据
     * @param value value
     * @param <V> V
     * @return 本身
     */
    <V> T saveData(V value);

    /**
     * 存储多个数据
     * @param key 键
     * @param value List数据
     * @param <V> List 数据类型
     * @return 本身
     */
    <V> T saveData(String key, List<V> value);

    /**
     * 存储多个数据
     * @param value List数据
     * @param <V> List 数据类型
     * @return 本身
     */
    <V> T saveData(List<V> value);

    /**
     * 按类名获取单个数据
     * @param key 键
     * @return String类型数据
     */
    String getString(String key);

    /**
     * 按key获取单个数据
     * @param <B> 类泛型型
     * @return 类数据
     */
    <B> B getBean(Class<B> clz);

    /**
     * 按key获取单个数据
     * @param key 键
     * @param <B> 类泛型型
     * @return 类数据
     */
    <B> B getBean(String key, Class<B> clz);

    /**
     * 按key和类名获取多个数据
     * @param <B> 数据类泛型
     * @return 类集合数据
     */
    <B> List<B> getBeanData(Class<B> clz);

    /**
     * 按key和类名获取多个数据
     * @param key 键
     * @param <B> 数据类泛型
     * @return 类集合数据
     */
    <B> List<B> getBeanData(String key, Class<B> clz);

    /**
     * 按key和类名获取多个数据
     * @param key 键
     * @param <B> 数据类泛型
     * @return 类集合数据
     */
    <B> List<B> getBeanData(String key, Class<? extends List> lClz, Class<B> clz);

    /**
     * 按key和类名获取多个数据
     * @param lCls List子类类名
     * @param cls 数据类名
     * @param <B> 数据类泛型
     * @return 类集合数据
     */
    <B> List<B> getBeanData(Class<? extends List> lCls, Class<B> cls);

    /**
     * 根据key获取data
     * @param key key
     * @return data
     */
    List<String> getStringData(String key);

    /**
     * 获取某表的所有数据
     * @return 表数据
     */
    Map<String, ?> getAllBean();

    /**
     * 将所有data变成json字符串
     * @return json字符串
     */
    String getAllBeanJsonString();

    /**
     * 按key删除数据
     * @param key 键
     * @return 本身
     */
    T remove(String key);

    /**
     * 删除该表
     * @return 本身
     */
    T removeDataAll();

    /**
     * 删除clz
     * @param clz clz
     * @return 本身
     */
    T remove(Class clz);

    /**
     * 删除key
     * @param key key
     * @return 本身
     */
    T removeData(String key);

    /**
     * 删除clz
     * @param clz clz
     * @return 本身
     */
    T removeData(Class clz);

}

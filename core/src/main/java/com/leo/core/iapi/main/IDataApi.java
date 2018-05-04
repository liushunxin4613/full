package com.leo.core.iapi.main;

import com.leo.core.iapi.ICheckApi;

import java.util.List;
import java.util.Set;

/**
 * data api
 *
 * @param <T> 本身
 * @param <D> 数据
 */
public interface IDataApi<T extends IDataApi, D> extends ICheckApi<T, D> {

    /**
     * 数据比较
     *
     * @param obj 数据
     * @return true为相同, false为不同
     */
    boolean check(D obj);

    /**
     * 是否包含此对象
     *
     * @param obj 对象
     * @return true为包含, false为不包含
     */
    boolean contains(D obj);

    /**
     * 获取对象的类型
     *
     * @param obj 对象
     * @return 类型
     */
    Integer getType(D obj);

    /**
     * 获取对象的类型
     *
     * @param position 位置
     * @return 类型
     */
    Integer getType(int position);

    /**
     * 获取所有对象
     *
     * @return 数据流
     */
    List<D> getData();

    /**
     * 复制数据流
     *
     * @return 数据流
     */
    List<D> copy();

    /**
     * 获取所有对象
     *
     * @return 数据流
     */
    Set<Integer> getTypeData();

    /**
     * 获取所有对象
     *
     * @return 数据流
     */
    Set<Class<D>> getClassData();

    /**
     * 数据数量
     *
     * @return 数量
     */
    int getCount();

    /**
     * Type 数量
     *
     * @return 数量
     */
    int getTypeCount();

    /**
     * Class 数量
     *
     * @return 数量
     */
    int getClassCount();

    /**
     * 添加数据
     *
     * @param obj
     * @return 本身
     */
    T add(D obj);

    /**
     * 添加数据到头部
     *
     * @param obj 数据
     * @return 本身
     */
    T addHead(D obj);

    /**
     * 添加数据到某处
     *
     * @param position 位置
     * @param obj      数据
     * @return 本身
     */
    T add(int position, D obj);

    /**
     * 添加数据流
     *
     * @param data 数据流
     * @return 本身
     */
    T addAll(List<D> data);

    /**
     * 添加数据流到头部
     *
     * @param data 数据流
     * @return 本身
     */
    T addAllHead(List<D> data);

    /**
     * 添加数据流到某处
     *
     * @param position 位置
     * @param data     数据流
     * @return 本身
     */
    T addAll(int position, List<D> data);

    /**
     * 删除某处数据
     *
     * @param obj 对象
     * @return 本身
     */
    T remove(D obj);

    /**
     * 删除某处数据
     *
     * @param position 位置
     * @return 本身
     */
    T remove(int position);

    /**
     * 删除某处数据
     *
     * @param position 位置
     * @return 本身
     */
    T removeLast(int position);

    /**
     * 删除type的倒数值
     *
     * @param type     类型
     * @param position 位置
     * @return 本身
     */
    T remove(int type, int position);

    /**
     * 删除type的倒数值
     *
     * @param type     类型
     * @param position 位置
     * @return 本身
     */
    T removeLast(int type, int position);

    /**
     * 删除clz的值
     *
     * @param clz      class
     * @param position 位置
     * @return 本身
     */
    T remove(Class<? extends D> clz, int position);

    /**
     * 删除clz的倒数值
     *
     * @param clz      class
     * @param position 位置
     * @return 本身
     */
    T removeLast(Class<? extends D> clz, int position);

    /**
     * 删除所有数据
     *
     * @return 本身
     */
    T removeAll();

    /**
     * 删除data包含的值
     *
     * @param data 数据流
     * @return 本身
     */
    T removeAll(List<D> data);

    /**
     * 删除某处到某处的值
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 删除位置
     */
    Integer removeAll(int start, int end);

    /**
     * 删除所有clz数据
     *
     * @param clz class
     * @return 本身
     */
    T removeAll(Class<? extends D> clz);

    /**
     * 删除所有type数据
     *
     * @param type 类型
     * @return 本身
     */
    T removeAll(int type);

    /**
     * 替换某对象
     *
     * @param old 被替换对象
     * @param obj 对象
     * @return 本身
     */
    T replace(D old, D obj);

    /**
     * 替换某位置对象
     *
     * @param position 位置
     * @param obj      对象
     * @return 本身
     */
    T replace(int position, D obj);

    /**
     * 替换某倒数位置对象
     *
     * @param position 位置
     * @param obj      对象
     * @return 本身
     */
    T replaceLast(int position, D obj);

    /**
     * 替换某位置对象
     *
     * @param position 位置
     * @param data     对象
     * @return 本身
     */
    T replace(int position, List<D> data);

    /**
     * 替换某一段位置的数据
     *
     * @param start 开始位置
     * @param end   结束位置
     * @param obj   对象
     * @return 本身
     */
    T replace(int start, int end, D obj);

    /**
     * 替换某一段位置的数据
     *
     * @param start 开始位置
     * @param end   结束位置
     * @param data  数据流
     * @return 本身
     */
    T replace(int start, int end, List<D> data);

    /**
     * 替换所有对象
     *
     * @param data 数据流
     * @return 本身
     */
    T replaceAll(List<D> data);

    /**
     * 获取对象的位置
     *
     * @param obj 对象
     * @return 位置
     */
    int getPosition(D obj);

    /**
     * 获取某类型数据第一个位置
     *
     * @param type 类型
     * @return 位置
     */
    int getPosition(int type);

    /**
     * 获取某类型数据第几个数据的位置
     *
     * @param type 类型
     * @return 位置
     */
    int getPosition(int type, int position);

    /**
     * 获取某数据类型的最后一个位置
     *
     * @param type 类型
     * @return 位置
     */
    int getPositionLast(int type);

    /**
     * 获取某类的第一个对象
     *
     * @param clz 类
     * @return 位置
     */
    int getPosition(Class<? extends D> clz);

    /**
     * 获取某类的第几个对象的位置
     *
     * @param clz 类
     * @return 位置
     */
    int getPosition(Class<? extends D> clz, int position);

    /**
     * 获取某类的最后一个对象
     *
     * @param clz 类
     * @return 位置
     */
    int getPositionLast(Class<? extends D> clz);

    /**
     * 获取某位置的对象
     *
     * @param position 位置
     * @return 对象
     */
    D getItem(int position);

    /**
     * 获取某最后位置的对象
     *
     * @param position 位置
     * @return 对象
     */
    D getLastItem(int position);

    /**
     * 获取某类型某位置的对象
     *
     * @param type     类型
     * @param position 位置
     * @return 对象
     */
    D getItem(int type, int position);

    /**
     * 获取某类型某最后位置的对象
     *
     * @param type     类型
     * @param position 位置
     * @return 对象
     */
    D getLastItem(int type, int position);

    /**
     * 获取某clz某位置的对象
     *
     * @param clz      类
     * @param position 位置
     * @return 对象
     */
    D getItem(Class<? extends D> clz, int position);

    /**
     * 获取某clz某最后位置的对象
     *
     * @param clz      类
     * @param position 位置
     * @return 对象
     */
    D getLastItem(Class<? extends D> clz, int position);

    /**
     * 获取某类型数据
     *
     * @param type 类型
     * @return 数据流
     */
    List<D> getData(int type);

    /**
     * 获取某clz数据
     *
     * @param clz 类型
     * @return 数据流
     */
    List<D> getData(Class<? extends D> clz);

    /**
     * 获取某一段数据
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 数据流
     */
    List<D> getData(int start, int end);

}

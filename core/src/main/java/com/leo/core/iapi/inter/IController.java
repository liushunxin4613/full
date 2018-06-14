package com.leo.core.iapi.inter;

import com.leo.core.iapi.core.IApi;

import java.lang.reflect.Type;

/**
 * 业务逻辑处理层
 * <br/>
 * <blockquote/>
 * 不能有构造方法
 */
public interface IController<T extends IController, DB> extends IApi {

    /**
     * 初始化db数据
     *
     * @param db db
     * @return 本身
     */
    T initDB(DB db);

    /**
     * 基本数据对象,驱动源
     *
     * @return DB
     */
    DB getDB();

    /**
     * 过滤数据,可用于搜索处理
     *
     * @param args args
     * @return DB
     */
    DB getFilterDB(IBolAction<DB>... args);

    /**
     * 视图绑定对象
     *
     * @param <VB> VB
     * @return VB
     */
    <VB> VB getViewBean();

    /**
     * 上传数据参数名称
     * @param args 参数队列
     * @return 参数名称
     */
    String getUBKey(String... args);

    /**
     * 数据提交,上传数据参数
     *
     * @param <UB> UB
     * @return UB
     */
    <UB> UB getUB(String... args);

    /**
     * 获取DB的class
     * @return clz
     */
    Type getType();

}

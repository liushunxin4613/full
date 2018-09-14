package com.leo.core.iapi.inter;

import android.support.annotation.NonNull;

import com.leo.core.iapi.api.IApiCodeApi;

/**
 * 业务逻辑处理层
 * <br/>
 * <blockquote/>
 * 不能有构造方法
 */
public interface IController<T extends IController, DB, UB> extends IApiCodeApi {

    /**
     * 初始化field
     * @param field field
     * @return 本身
     */
    T initField(String field);

    /**
     * 初始化db数据
     *
     * @param db db
     * @return 本身
     */
    T initDB(DB db);

    /**
     * 数据是否为空
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 清理数据
     * @return 本身
     */
    T clear();

    /**
     * filed
     * @param action action
     * @return 本身
     */
    <A> A toField(IReturnAction<String, A> action);

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
    DB getFilterDB(IbolAction<DB>... args);

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
     * @return UB
     */
    UB getUB(String... args);

    /**
     * 安全上传参数
     * @return 上传参数
     */
    @NonNull
    UB getSafeUB(String... args);

}

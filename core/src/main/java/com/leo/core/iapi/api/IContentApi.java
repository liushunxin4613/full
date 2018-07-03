package com.leo.core.iapi.api;

import android.view.View;

import com.leo.core.iapi.core.IApi;

public interface IContentApi<T extends IContentApi> extends IApi{

    /**
     * 初始化
     * @return 本身
     */
    T init();

    /**
     * 内容控件
     * @param <R> R
     * @return 控件
     */
    <R extends View> R getContentView();

    /**
     * 数据为空视图
     * @return view
     */
    View getNullView();

    /**
     * 请求异常view
     * @return view
     */
    View getErrorView();

    /**
     * 开始刷新
     * @return 本身
     */
    T startLoad(boolean refresh);

    /**
     * 结束刷新
     * @return 本身
     */
    T stopLoad(boolean refresh);

    /**
     * 隐藏所有视图
     */
    T hideViews();

    /**
     * 隐藏主视图
     */
    T hideContentView();

    /**
     * 显示内容视图
     * @return 本身
     */
    T showContentView();

    /**
     * 显示空视图
     * @return 本身
     */
    T showNullView(boolean refresh);

    /**
     * 显示错误视图
     * @return 本身
     */
    T showErrorView();

}

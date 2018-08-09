package com.leo.core.iapi.api;

import android.widget.ImageView;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.ImageAction;

import java.util.Map;

public interface ILoadImageApi<T extends ILoadImageApi> extends IApi {

    /**
     * 驱动
     *
     * @param <R> R
     * @return 驱动
     */
    <R> R getDrive();

    /**
     * 默认图片
     *
     * @return 图片资源
     */
    Integer getDefaultRes();

    /**
     * 异常图片
     *
     * @return 图片资源
     */
    Integer getErrorRes();

    /**
     * 是否缓存
     *
     * @param enable true为是,false为否
     * @return 本身
     */
    T setCacheEnable(boolean enable);

    /**
     * 初始化
     *
     * @param map map
     * @return 本身
     */
    T init(Map<String, ?> map);

    /**
     * 负载
     *
     * @param path path
     * @param iv   iv
     * @return 本身
     */
    T load(Object path, ImageView iv);

    /**
     * 负载
     *
     * @param path   path
     * @param iv     iv
     * @param action action
     * @return 本身
     */
    T load(Object path, ImageView iv, ImageAction action);

    /**
     * 负载
     *
     * @param path   path
     * @param iv     iv
     * @param rotate rotate
     * @return 本身
     */
    T load(Object path, ImageView iv, float rotate);

    /**
     * 负载
     *
     * @param path   path
     * @param iv     iv
     * @param rotate rotate
     * @param action action
     * @return 本身
     */
    T load(Object path, ImageView iv, float rotate, ImageAction action);

}

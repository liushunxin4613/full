package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

import java.io.File;

/**
 * 文件夹
 */
public interface IDirApi extends IApi {

    /**
     * 是否有sdCard
     */
    boolean hasSdCard();

    /**
     * 根文件夹
     */
    File getRootDir(String dir);

    /**
     * 根文件夹
     */
    File getRootDir();

}
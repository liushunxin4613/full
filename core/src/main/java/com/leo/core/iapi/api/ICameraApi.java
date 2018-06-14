package com.leo.core.iapi.api;

import android.content.Intent;

import com.leo.core.iapi.core.IApi;
import com.leo.core.iapi.inter.IMsgAction;
import com.leo.core.iapi.inter.IObjAction;

import java.io.File;

/**
 * 拍照api
 */
public interface ICameraApi extends IApi {

    /**
     * 是否有sdCard
     */
    boolean hasSdCard();

    /**
     * 根文件夹
     */
    File getRootDir();

    /**
     * 打开相机
     *
     * @param type   type
     * @param action action
     */
    void openCamera(int type, IMsgAction<File> action);

    /**
     * 图片拍照返回
     *
     * @param requestCode requestCode
     * @param resultCode  resultCode
     * @param data        data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

}
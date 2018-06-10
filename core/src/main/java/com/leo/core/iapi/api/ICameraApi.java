package com.leo.core.iapi.api;

import android.content.Intent;

import com.leo.core.iapi.inter.IMsgAction;

import java.io.File;

/**
 * 拍照api
 */
public interface ICameraApi extends IDirApi {

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
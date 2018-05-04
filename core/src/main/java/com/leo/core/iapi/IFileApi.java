package com.leo.core.iapi;

import com.leo.core.iapi.core.IApi;

public interface IFileApi extends IApi{

    /**
     * 获取assets的数据
     * @param file 文件名
     * @return string数据
     */
    String getAssetsString(String file);

}

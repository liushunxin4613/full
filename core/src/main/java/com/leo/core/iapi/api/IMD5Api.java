package com.leo.core.iapi.api;

import com.leo.core.iapi.core.IApi;

public interface IMD5Api extends IApi {

    /**
     * md5
     * @param text text
     * @return md5密文
     */
    String md5(String text);

    /**
     * mmd5
     * @param text text
     * @return mmd5密文
     */
    String mmd5(String text);

}

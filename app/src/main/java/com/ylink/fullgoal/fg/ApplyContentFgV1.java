package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.IApiCodeApi;

/**
 * 申请单内容
 */
public class ApplyContentFgV1 implements IApiCodeApi {

    @Override
    public String getApiCode() {
        return getCode();
    }

    /**
     * applyCode : 001
     * code : 01
     * name : 招待一
     */

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
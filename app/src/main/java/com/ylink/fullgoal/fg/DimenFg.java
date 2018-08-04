package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.IApiCodeApi;

/**
 * 分摊渠道
 */
public class DimenFg implements IApiCodeApi{

    @Override
    public String getApiCode() {
        return getCode();
    }

    /**
     * code : 001
     * name : 员工
     */

    private String code;
    private String name;

    public boolean isEmpty(){
        return code == null && name == null;
    }

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

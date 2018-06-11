package com.ylink.fullgoal.config.vo;

import com.leo.core.iapi.api.IIsSuccessApi;
import com.leo.core.util.TextUtils;

public class ReturnCodeVo implements IIsSuccessApi{

    @Override
    public boolean isSuccess() {
        return TextUtils.equals("AAAAAA", getCode());
    }

    /**
     * type : E
     * code : AAAAAA
     * message : 消息
     */

    private String type;
    private String code;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
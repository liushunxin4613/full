package com.ylink.fullgoal.ht;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.config.Config;

public class BaseHt {

    /**
     * type : E
     * code : AAAAAA
     * message : 数据更新成功
     */

    private String type;
    private String code;
    private String message;

    public boolean isSuccess() {
        return TextUtils.equals(getCode(), Config.HTTP_SUCCESS);
    }

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

package com.ylink.fullgoal.fg;

import com.leo.core.bean.NewFieldBean;
import com.leo.core.iapi.api.IIsSuccessApi;
import com.leo.core.util.TextUtils;

public class StatusCodeFg extends NewFieldBean implements IIsSuccessApi {

    @Override
    public boolean isSuccess() {
        return TextUtils.equals(getStatusCode(), "SUCCESS");
    }

    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
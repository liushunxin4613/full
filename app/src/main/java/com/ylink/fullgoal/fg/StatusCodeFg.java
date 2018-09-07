package com.ylink.fullgoal.fg;

import com.leo.core.bean.NewFieldBean;
import com.leo.core.iapi.api.IIsSuccessApi;
import com.leo.core.util.TextUtils;

public class StatusCodeFg extends NewFieldBean implements IIsSuccessApi {

    @Override
    public boolean isSuccess() {
        return TextUtils.equals(getStatusCode(), "SUCCESS")
                || TextUtils.equals(getStatus(), "SUCC");
    }

    private String statusCode;
    private String status;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
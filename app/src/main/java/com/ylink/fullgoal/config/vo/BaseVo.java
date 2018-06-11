package com.ylink.fullgoal.config.vo;

import com.leo.core.iapi.api.IIsSuccessApi;

public class BaseVo implements IIsSuccessApi {

    @Override
    public boolean isSuccess() {
        return getReturnCode() != null && getReturnCode().isSuccess();
    }

    private ReturnCodeVo returnCode;

    public ReturnCodeVo getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(ReturnCodeVo returnCode) {
        this.returnCode = returnCode;
    }

}
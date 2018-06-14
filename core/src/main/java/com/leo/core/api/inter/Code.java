package com.leo.core.api.inter;

import com.leo.core.iapi.inter.ICode;

public class Code implements ICode {

    private String code;

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

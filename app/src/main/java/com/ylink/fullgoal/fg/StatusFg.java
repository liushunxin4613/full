package com.ylink.fullgoal.fg;

import com.leo.core.util.TextUtils;

public class StatusFg {

    /**
     * status : SUCC
     * message : SUCC
     */

    private String status;
    private String message;

    public boolean isSuccess(){
        return TextUtils.equals(getStatus(), "SUCC");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
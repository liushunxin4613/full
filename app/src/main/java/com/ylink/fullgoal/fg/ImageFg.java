package com.ylink.fullgoal.fg;

import com.leo.core.util.TextUtils;

/**
 * 图片回传
 */
public class ImageFg extends StatusCodeFg {

    /**
     * imageId : 100013
     * message : 上传成功
     * serialNo : 8122018060612136000061
     */

    public boolean isUpload() {
        return isSuccess() && !TextUtils.isEmpty(getMessage()) && getMessage().contains("上传");
    }

    private String imageId;
    private String message;
    private String serialNo;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

}
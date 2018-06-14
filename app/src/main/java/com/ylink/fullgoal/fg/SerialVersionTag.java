package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.ISerialVersionTagApi;
import com.leo.core.util.TextUtils;

/**
 * 标志服关联
 */
public class SerialVersionTag implements ISerialVersionTagApi<String> {

    private String serialVersionTag;

    private String getSerialVersionTag() {
        return serialVersionTag;
    }

    protected void setSerialVersionTag(String serialVersionTag) {
        this.serialVersionTag = serialVersionTag;
    }

    @Override
    public boolean isSerialVersionTag(String text) {
        return TextUtils.equals(text, getSerialVersionTag());
    }
}

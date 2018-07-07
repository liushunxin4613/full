package com.ylink.fullgoal.fg;

import com.leo.core.iapi.api.ISerialVersionTagApi;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Type;

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

    protected void setSerialVersionTag(Type type) {
        this.serialVersionTag = type == null ? null : type.toString();
    }

    @Override
    public boolean isSerialVersionTag(String text) {
        return TextUtils.equals(text, getSerialVersionTag());
    }
}

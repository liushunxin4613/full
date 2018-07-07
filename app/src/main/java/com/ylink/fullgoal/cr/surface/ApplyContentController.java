package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ApplyContentFgV1;

public class ApplyContentController<T extends ApplyContentController> extends BaseStringController<T, ApplyContentFgV1> {

    @Override
    public ApplyContentFgV1 getDB() {
        return super.getDB();
    }

    @Override
    public String getViewBean() {
        return null;
    }

    @Override
    protected Class<ApplyContentFgV1> getClz() {
        return null;
    }

    @Override
    protected Class<ApplyContentFgV1> getUBClz() {
        return null;
    }

}

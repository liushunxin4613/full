package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.AddController;
import com.ylink.fullgoal.fg.ShareDimenListFg;

import java.util.List;

public class ShareListController<T extends ShareListController> extends AddController<T, ShareDimenListFg> {

    @Override
    protected ShareDimenListFg getFilterDB(String key, ShareDimenListFg shareDimenListFg) {
        return null;
    }

    @Override
    public T initDB(List<ShareDimenListFg> data) {
        return super.initDB(data);
    }

    @Override
    protected String getOnUBKey(String key) {
        return "share";
    }

}
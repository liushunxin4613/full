package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseStringController;

import java.util.Map;

public class SameController<T extends SameController> extends BaseStringController<T, Map<String, Object>> {

    @Override
    public T initDB(Map<String, Object> map) {
        return super.initDB(map);
    }

    public boolean equals(Map<String, Object> map){
        return TextUtils.equals(getDB(), map);
    }

}

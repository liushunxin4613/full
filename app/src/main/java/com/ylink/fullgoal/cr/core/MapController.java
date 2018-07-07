package com.ylink.fullgoal.cr.core;

import java.util.Map;

public abstract class MapController<T extends MapController, K, DB, UB> extends BaseMapController<T, K, DB, UB> {

    @Override
    public Map<K, DB> getViewBean() {
        return getMap();
    }

}
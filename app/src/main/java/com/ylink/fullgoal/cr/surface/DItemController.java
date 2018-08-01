package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.MapController;
import com.ylink.fullgoal.vo.DItemVo;

import java.util.Map;

public class DItemController<T extends DItemController> extends MapController<T, String,
        DItemVo, Map<String, Object>> {

    @Override
    public T initDB(String key, DItemVo dItemVo) {
        return super.initDB(key, dItemVo);
    }

    @Override
    public DItemVo getValue(String key) {
        return super.getValue(key);
    }

    @Override
    protected Map<String, Object> getOnUB(String key) {
        return vr(getMap().get(key), vo -> vo.getCheckMap());
    }

}
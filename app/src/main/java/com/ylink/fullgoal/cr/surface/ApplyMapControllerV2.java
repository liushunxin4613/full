package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseMapController;
import com.ylink.fullgoal.fg.ApplyDataFgV2;
import com.ylink.fullgoal.fg.ApplyFgV2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;
import static com.leo.core.util.TextUtils.checkNull;
import static com.ylink.fullgoal.config.ComConfig.DQ;

public class ApplyMapControllerV2<T extends ApplyMapControllerV2> extends BaseMapController<T,
        String, ApplyDataFgV2, List<ApplyDataFgV2>> {

    @Override
    protected Map<String, ApplyDataFgV2> newMap() {
        return new LinkedHashMap<>();
    }

    public T initKey(ApplyFgV2 key) {
        if (check(key) && check(key.getApplyType(), key.getApplyName())) {
            getMap().put(key.getApplyType(), new ApplyDataFgV2(key));
        }
        return getThis();
    }

    public T initDB(List<ApplyDataFgV2> data) {
        if (!TextUtils.isEmpty(data)) {
            execute(data, item -> {
                if (check(item.getKey(), item.getMap()) && check(getMap().get(item.getKey()
                        .getApplyType()))) {
                    getMap().get(item.getKey().getApplyType()).setMap(item.getMap());
                }
            });
        }
        return getThis();
    }

    public T initDB(String key, Map<String, String> map) {
        if (checkNull(key, map)) {
            vs(getMap().get(key), obj -> obj.setMap(map));
        }
        return getThis();
    }

    @Override
    public Map<String, ApplyDataFgV2> getMap() {
        return super.getMap();
    }

    @Override
    public T initDB(String key, ApplyDataFgV2 fg) {
        return super.initDB(key, fg);
    }

    @Override
    protected Class<ApplyDataFgV2> getClz() {
        return null;
    }

    @Override
    protected Class<List<ApplyDataFgV2>> getUBClz() {
        return null;
    }

    @Override
    public <VB> VB getViewBean() {
        return null;
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case DQ:
                return key;
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected List<ApplyDataFgV2> getOnUB(String key) {
        switch (key) {
            case DQ:
                List<ApplyDataFgV2> data = new ArrayList<>();
                execute(getMap(), (kk, vv) -> data.add(vv));
                return data;
        }
        return super.getOnUB(key);
    }

}
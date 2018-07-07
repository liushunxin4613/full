package com.ylink.fullgoal.cr.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.core.BaseMapController;
import com.ylink.fullgoal.fg.ApplyContentFgV1;
import com.ylink.fullgoal.fg.ApplyDataFgV1;
import com.ylink.fullgoal.fg.ApplyFgV1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.check;
import static com.ylink.fullgoal.config.ComConfig.DQ;

public class ApplyMapController<T extends ApplyMapController> extends BaseMapController<T,
        String, ApplyDataFgV1, List<ApplyDataFgV1>> {

    @Override
    protected Map<String, ApplyDataFgV1> newMap() {
        return new LinkedHashMap<>();
    }

    public T initKey(ApplyFgV1 key) {
        if (check(key) && check(key.getName(), key.getCode())) {
            getMap().put(key.getCode(), new ApplyDataFgV1(key));
        }
        return getThis();
    }

    public T initDB(List<ApplyDataFgV1> data) {
        if (!TextUtils.isEmpty(data)) {
            execute(data, item -> {
                if (check(item.getKey(), item.getValue()) && check(getMap().get(item.getKey().getCode()))) {
                    getMap().get(item.getKey().getCode()).setValue(item.getValue());
                }
            });
        }
        return getThis();
    }

    public T initDB(String key, ApplyContentFgV1 value) {
        if (check(key, value)) {
            vs(getMap().get(key), obj -> obj.setValue(value));
        }
        return getThis();
    }

    @Override
    public Map<String, ApplyDataFgV1> getMap() {
        return super.getMap();
    }

    @Override
    public T initDB(String key, ApplyDataFgV1 fg) {
        return super.initDB(key, fg);
    }

    @Override
    protected Class<ApplyDataFgV1> getClz() {
        return null;
    }

    @Override
    protected Class<List<ApplyDataFgV1>> getUBClz() {
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
    protected List<ApplyDataFgV1> getOnUB(String key) {
        switch (key) {
            case DQ:
                List<ApplyDataFgV1> data = new ArrayList<>();
                execute(getMap(), (kk, vv) -> data.add(vv));
                return data;
        }
        return super.getOnUB(key);
    }

}
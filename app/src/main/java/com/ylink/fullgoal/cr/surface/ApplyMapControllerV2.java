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
import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.DQ;
import static com.ylink.fullgoal.config.ComConfig.QR;
import static com.ylink.fullgoal.config.ComConfig.YB;

public class ApplyMapControllerV2<T extends ApplyMapControllerV2> extends BaseMapController<T,
        String, ApplyDataFgV2, List<Map<String, String>>> {

    @Override
    public ApplyDataFgV2 getDB() {
        return super.getDB();
    }

    @Override
    protected Map<String, ApplyDataFgV2> newMap() {
        return new LinkedHashMap<>();
    }

    public T init(List<Map<String, String>> data) {
        clear();
        execute(data, item -> {
            String applyType = item.get("extension1");
            String applyName = item.get("applyName");
            ApplyDataFgV2 dataFgV2 = new ApplyDataFgV2(
                    new ApplyFgV2(applyType, applyName));
            dataFgV2.setMap(item);
            initDB(applyType, dataFgV2);
        });
        return getThis();
    }

    public T insert(ApplyMapControllerV2 controller) {
        Map<String, ApplyDataFgV2> map = vr(controller, ApplyMapControllerV2::getMap);
        execute(map, (key, value) -> initDB(key, vr(value, ApplyDataFgV2::getMap)));
        return getThis();
    }

    public T initKey(ApplyFgV2 key) {
        if (check(key) && check(key.getApplyType(), key.getApplyName())
                && !TextUtils.check(getValue(key.getApplyType()))) {
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
    public String getViewBean() {
        StringBuilder builder = new StringBuilder();
        execute(getMap(), (key, value) -> {
            String name = vr(value, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName);
            if (TextUtils.check(name, vr(value, ApplyDataFgV2::getMap))) {
                builder.append(String.format("【%s】", name));
            }
        });
        return builder.toString();
    }

    @Override
    protected String getOnUBKey(String key) {
        switch (key) {
            case YB:
            case CC:
            case QR:
                return "apply";
            case DQ:
                return key;
        }
        return super.getOnUBKey(key);
    }

    @Override
    protected List<Map<String, String>> getOnUB(String key) {
        switch (key) {
            case YB:
            case CC:
            case QR:
            case DQ:
                List<Map<String, String>> data = new ArrayList<>();
                execute(getMap(), (kk, vv) -> {
                    String name = vr(vv, ApplyDataFgV2::getKey, ApplyFgV2::getApplyName);
                    if (TextUtils.check(vv.getMap(), name)) {
                        data.add(map(vv.getMap(), map -> map.put("applyName", name)));
                    }
                });
                return data;
        }
        return super.getOnUB(key);
    }

    public List<Map<String, String>> getListMap(){
        List<Map<String, String>> data = new ArrayList<>();
        execute(getMap(), (key, value) -> data.add(value.getMap()));
        return data;
    }

    public boolean contains(String key){
        return getMap().containsKey(key);
    }

}
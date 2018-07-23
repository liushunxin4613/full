package com.ylink.fullgoal.fg;

import java.util.Map;

import static com.leo.core.util.TextUtils.check;

public class ApplyDataFgV2 {

    private ApplyFgV2 key;
    private Map<String, String> map;

    public ApplyDataFgV2(ApplyFgV2 key) {
        this.key = key;
    }

    public String getViewValue() {
        if (check(getMap(), getKey()) && check(getKey().getApplyType())) {
            switch (getKey().getApplyType()) {
                case "001"://
                    return getMap().get("extension3");
                case "002"://
                    return getMap().get("extension2");
                case "003"://
                    return getMap().get("extension5");
                case "004"://
                    return getMap().get("extension2");
            }
        }
        return null;
    }

    public ApplyFgV2 getKey() {
        return key;
    }

    public void setKey(ApplyFgV2 key) {
        this.key = key;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}
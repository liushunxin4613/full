package com.ylink.fullgoal.fg;

public class ApplyDataFgV2 {

    private ApplyFgV1 key;
    private ApplyContentFgV1 value;

    public ApplyDataFgV2(ApplyFgV1 key) {
        this.key = key;
    }

    public ApplyFgV1 getKey() {
        return key;
    }

    public void setKey(ApplyFgV1 key) {
        this.key = key;
    }

    public ApplyContentFgV1 getValue() {
        return value;
    }

    public void setValue(ApplyContentFgV1 value) {
        this.value = value;
    }

}
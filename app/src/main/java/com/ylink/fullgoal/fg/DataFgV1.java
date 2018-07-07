package com.ylink.fullgoal.fg;

import java.util.List;

public class DataFgV1 extends DataFg{

    private List<ApplyFgV1> applyList;//申请单
    private List<ApplyContentFgV1> applyContentList;//申请单内容

    public List<ApplyFgV1> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyFgV1> applyList) {
        this.applyList = applyList;
    }

    public List<ApplyContentFgV1> getApplyContentList() {
        return applyContentList;
    }

    public void setApplyContentList(List<ApplyContentFgV1> applyContentList) {
        this.applyContentList = applyContentList;
    }

}
package com.ylink.fullgoal.fg;

import java.util.List;

public class DataFgV2 extends DataFg{

    private List<ApplyFgV2> applyList;//申请单
    private List<ApplyContentFgV2> applyCodeResult;//申请单内容

    public List<ApplyFgV2> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyFgV2> applyList) {
        this.applyList = applyList;
    }

    public List<ApplyContentFgV2> getApplyCodeResult() {
        return applyCodeResult;
    }

    public void setApplyCodeResult(List<ApplyContentFgV2> applyCodeResult) {
        this.applyCodeResult = applyCodeResult;
    }

}
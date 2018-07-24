package com.ylink.fullgoal.fg;

import java.util.List;

public class DataFgV2 extends DataFg{
    //test
    private List<ApplyFgV2> applyList;//申请单
    private List<ApplyContentFgV2> applyCodeResult;//申请单内容
    //app
    private List<ApplyFgV2> appList2;//申请单
    private List<ApplyContentFgV2> applyCodeResult2;//申请单内容

    //test
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

    //app

    public List<ApplyFgV2> getAppList2() {
        return appList2;
    }

    public void setAppList2(List<ApplyFgV2> appList2) {
        this.appList2 = appList2;
    }

    public List<ApplyContentFgV2> getApplyCodeResult2() {
        return applyCodeResult2;
    }

    public void setApplyCodeResult2(List<ApplyContentFgV2> applyCodeResult2) {
        this.applyCodeResult2 = applyCodeResult2;
    }

}
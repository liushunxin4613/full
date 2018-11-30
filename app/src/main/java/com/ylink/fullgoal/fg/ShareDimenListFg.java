package com.ylink.fullgoal.fg;

import java.util.List;

public class ShareDimenListFg {

    private String shareAmount;
    private String shareRatio;
    private List<ShareDimensionItemFg> dimenList;

    public String getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(String shareAmount) {
        this.shareAmount = shareAmount;
    }

    public String getShareRatio() {
        return shareRatio;
    }

    public void setShareRatio(String shareRatio) {
        this.shareRatio = shareRatio;
    }

    public List<ShareDimensionItemFg> getDimenList() {
        return dimenList;
    }

    public void setDimenList(List<ShareDimensionItemFg> dimenList) {
        this.dimenList = dimenList;
    }

}
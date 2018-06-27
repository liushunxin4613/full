package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.BooleanController;
import com.ylink.fullgoal.cr.surface.DAgentController;
import com.ylink.fullgoal.cr.surface.DBillTypeController;
import com.ylink.fullgoal.cr.surface.DDateController;
import com.ylink.fullgoal.cr.surface.DStatusController;

/**
 * 报销列表
 */
public class DItemVo extends NewFieldBean {

    private DDateController date;
    private DAgentController agent;
    private DStatusController status;
    private DBillTypeController billType;
    private transient BooleanController once;

    public DItemVo() {
        initNewFields();
    }

    public DDateController getDate() {
        return date;
    }

    public void setDate(DDateController date) {
        this.date = date;
    }

    public DAgentController getAgent() {
        return agent;
    }

    public void setAgent(DAgentController agent) {
        this.agent = agent;
    }

    public DStatusController getStatus() {
        return status;
    }

    public void setStatus(DStatusController status) {
        this.status = status;
    }

    public DBillTypeController getBillType() {
        return billType;
    }

    public void setBillType(DBillTypeController billType) {
        this.billType = billType;
    }

    public BooleanController getOnce() {
        return once;
    }

    public void setOnce(BooleanController once) {
        this.once = once;
    }

}
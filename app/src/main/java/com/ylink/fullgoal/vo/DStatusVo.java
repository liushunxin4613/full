package com.ylink.fullgoal.vo;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.surface.DAgentController;
import com.ylink.fullgoal.cr.surface.DBillTypeController;
import com.ylink.fullgoal.cr.surface.DDateController;

public class DStatusVo extends NewFieldBean{

    private DDateController date;
    private DAgentController agent;
    private DBillTypeController billType;

    public DStatusVo() {
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

    public DBillTypeController getBillType() {
        return billType;
    }

    public void setBillType(DBillTypeController billType) {
        this.billType = billType;
    }

}

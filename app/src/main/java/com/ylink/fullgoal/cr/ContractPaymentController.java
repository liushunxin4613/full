package com.ylink.fullgoal.cr;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ContractPaymentFg;

public class ContractPaymentController<T extends ContractPaymentController> extends BaseStringController<T, ContractPaymentFg> {

    @Override
    public T initDB(ContractPaymentFg contractPaymentFg) {
        return super.initDB(contractPaymentFg);
    }

    @Override
    public ContractPaymentFg getDB() {
        return super.getDB();
    }

    @Override
    public String getViewBean() {
        return no(ContractPaymentFg::getName);
    }

    @Override
    public Class<ContractPaymentFg> getClz() {
        return ContractPaymentFg.class;
    }

    @Override
    protected String getDefUBKey() {
        return null;
    }

    @Override
    protected <UB> UB getDefUB() {
        return null;
    }
}

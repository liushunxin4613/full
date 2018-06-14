package com.ylink.fullgoal.cr.surface;

import com.ylink.fullgoal.cr.core.BaseStringController;
import com.ylink.fullgoal.fg.ContractPaymentFg;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.config.ComConfig.YB;

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
    protected String getOnUBKey(String key) {
        switch (key){
            case YB:
                return "paymentRequest";
        }
        return super.getOnUBKey(key);
    }

}

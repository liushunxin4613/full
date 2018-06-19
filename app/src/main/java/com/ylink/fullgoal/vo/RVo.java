package com.ylink.fullgoal.vo;

import com.leo.core.iapi.inter.IGet;
import com.ylink.fullgoal.fg.MessageBackFg;

/**
 * 返回数据核心
 */
public class RVo extends MessageBackFg implements IGet<DVo> {

    @Override
    public DVo get(DVo vo) {
        if(vo != null){
            vo.getAgent().initDB(getAgent());
            vo.getReimbursement().initDB(getReimbursement());
            vo.getBudgetDepartment().initDB(getBudgetDepartment());
            vo.getCause().initDB(getCause());
            vo.getContractPayment().initDB(getPaymentRequest());
            vo.getProject().initDB(getProject());
            vo.getProcess().initDB(getProcess());
            vo.getCostIndex().initDB(getCostList());
            vo.getImageList().initDB(getImageList());
            vo.getCtrip().initDB(getCtrip());
            vo.getReport().initDB(getReport());
            vo.getTrave().initDB(getTravel());
            vo.getRuleList().initDB(getRuleList());
        }
        return vo;
    }

}

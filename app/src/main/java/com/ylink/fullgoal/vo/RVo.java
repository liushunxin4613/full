package com.ylink.fullgoal.vo;

import com.leo.core.iapi.inter.IGet;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.fg.MessageBackFg;

/**
 * 返回数据核心
 */
public class RVo extends MessageBackFg implements IGet<DVo> {

    @Override
    public DVo get(DVo vo) {
        if (vo != null) {
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
            vo.getTaskNode().initDB(getTaskNode());
            //新加 2018-07-24 20:51
            vo.getIsShare().initDB(TextUtils.equals(getIsShare(), "需要分摊"));
            vo.getApply().init(getApply());
            vo.getSame().initDB(vo.getInfoMap());//添加相似初始化数据
        }
        return vo;
    }

}

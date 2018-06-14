package com.ylink.fullgoal.api.full;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.cr.CauseController;
import com.ylink.fullgoal.cr.ImageListController;
import com.ylink.fullgoal.cr.UserController;
import com.ylink.fullgoal.cr.main.DVo;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 一般费用报销
 */
public class FullGeneralControllerApi<T extends FullGeneralControllerApi, C> extends FullReimburseControllerApi<T, C> {

    public FullGeneralControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected void onData() {
        super.onData();
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(gt(DVo::getAgent), gt(DVo::getDepartment)));
            checkAdd(data, gt(DVo::getReimbursement), new TvHEtIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", gt(DVo::getReimbursement), "请输入报销人", (bean, view)
                    -> startSearch(SearchVo.REIMBURSEMENT), text
                    -> iso(DVo::getReimbursement, UserController::getDB, db -> db.setName(text))));
            checkAdd(data, gt(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属部门",
                    gt(DVo::getBudgetDepartment), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            checkAdd(data, gt(DVo::getProject), new TvH2MoreBean("项目", gt(DVo::getProject), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
            checkAdd(data, gt(DVo::getContractPayment), new TvH2MoreBean("合同付款申请单",
                    gt(DVo::getContractPayment), "请选择合同付款申请单",
                    (bean, view) -> startSearch(SearchVo.CONTRACT_BILL)));
            checkAdd(data, gt(DVo::getProcess), new TvH2MoreBean("招待申请单",
                    gt(DVo::getProcess), "请选择招待申请单",
                    (bean, view) -> startSearch(SearchVo.SERVE_BILL)));
            checkAdd(data, gt(DVo::getCostIndex),
                    new TvH2MoreBean("费用指标", gt(DVo::getCostIndex), "请选择费                                                                   8用指标",
                            (bean, view) -> startSearch(SearchVo.COST_INDEX)));
            //经办人确认、经办人修改
            /*if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                checkAdd(data, vo.getTotalAmountLower(), new MoneyBean("金额",
                        vo.getTotalAmountLower(), "请输入金额", vo::setTotalAmountLower));
            }*/
            checkAdd(data, gt(DVo::getCause), new TvHEt3Bean("事由", gt(DVo::getCause),
                    "请输入事由", text -> iso(DVo::getCause, CauseController::getDB, obj -> obj = text)));
        });
        /*//禁止规则
        if (isAlterEnable() && !TextUtils.isEmpty(vo.getInhibitionRuleData())) {
            execute(vo.getInhibitionRuleData(), obj -> add(new InhibitionRuleBean(obj.getState(),
                    obj.getName(), obj.getDetail())));
        }*/
        //GridBean 添加票据
        addVgBean(null, newGridBean(TYPE_NONE, gt(DVo::getImageList, ImageListController::getYbData)));
        /*//添加流程
        if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }*/
    }

    @Override
    protected void submit() {
        super.submit();
        ee("checkMap", getVo().getCheckMap());
        /*uApi().submitReimburse(getCheckMap(getReimburseUpFg(vo -> new ReimburseUpFg(getFirst(),
                        getSerialNo(), vo.getAgent(), vo.getReimbursement(), vo.getBudgetDepartment(),
                        vo.getProject(), vo.getTotalAmountLower(), getNull(vo.getCause()),
                        vo.getPaymentRequestList(), vo.getServeBill(), vo.getCostIndexData())),
                getSetData("报销流水号", "报销类型", "经办人", "报销人", "预算归属部门", "事由")));*/
    }

}

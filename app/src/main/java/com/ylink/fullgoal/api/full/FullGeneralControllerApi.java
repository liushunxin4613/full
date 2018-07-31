package com.ylink.fullgoal.api.full;

import com.leo.core.api.inter.CoreController;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.YB;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_YB;

/**
 * 一般费用报销
 */
public class FullGeneralControllerApi<T extends FullGeneralControllerApi, C> extends FullReimburseControllerApi<T, C> {

    public FullGeneralControllerApi(C controller) {
        super(controller);
    }

    @Override
    public DVo getVo() {
        return super.getVo();
    }

    @Override
    protected String getBType() {
        return YB;
    }

    @Override
    protected String getBTitle() {
        return "一般费用报销";
    }

    @Override
    protected void onData() {
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(vorv(DVo::getAgent), vorv(DVo::getDepartment)));
            /*checkAdd(data, vorv(DVo::getReimbursement), new TvHTvIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", vorv(DVo::getReimbursement), "请输入报销人", (bean, view)
                    -> routeApi().search(SearchVo.REIMBURSEMENT), text
                    -> vos(DVo::getReimbursement, UserController::getDB, db -> db.setUserName(text))));*/
            checkAdd(data, vorv(DVo::getReimbursement), new TvH2MoreBean("报销人",
                    vorv(DVo::getReimbursement), "请选择报销人",
                    (bean, view) -> routeApi().search(SearchVo.REIMBURSEMENT), (bean, view)
                    -> vos(DVo::getReimbursement, CoreController::clear)));
            checkAdd(data, vorv(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属部门",
                    vorv(DVo::getBudgetDepartment), "请选择预算归属部门",
                    (bean, view) -> routeApi().search(SearchVo.BUDGET_DEPARTMENT), (bean, view)
                    -> vos(DVo::getBudgetDepartment, CoreController::clear)));
            checkAdd(data, vorv(DVo::getProject), new TvH2MoreBean("项目", vorv(DVo::getProject), "请选择项目",
                    (bean, view) -> routeApi().search(SearchVo.PROJECT, vorc(DVo::getBudgetDepartment)),
                    (bean, view) -> vos(DVo::getProject, CoreController::clear)));
            checkAdd(data, vorv(DVo::getContractPayment), new TvH2MoreBean("合同付款申请单",
                    vorv(DVo::getContractPayment), "请选择合同付款申请单",
                    (bean, view) -> routeApi().search(SearchVo.CONTRACT_BILL), (bean, view)
                    -> vos(DVo::getContractPayment, CoreController::clear)));
            checkAdd(data, vorv(DVo::getProcess), new TvH2MoreBean("招待申请单",
                    vorv(DVo::getProcess), "请选择招待申请单",
                    (bean, view) -> routeApi().search(SearchVo.SERVE_BILL), (bean, view)
                    -> vos(DVo::getProcess, CoreController::clear)));
            checkAdd(data, vorv(DVo::getCostIndex),
                    new TvH2MoreBean("费用指标", vorv(DVo::getCostIndex), "请选择费用指标",
                            (bean, view) -> routeApi().search(SearchVo.COST_INDEX), (bean, view)
                            -> vos(DVo::getCostIndex, CoreController::clear)));
            //经办人确认、经办人修改
            if (isNoneInitiateEnable()) {
                checkAdd(data, vorv(DVo::getMoney), new TvH2Bean("金额", vorv(DVo::getMoney)));
            }
            checkAdd(data, vorv(DVo::getCause), new TvHEt3Bean("事由", vorv(DVo::getCause),
                    "请输入事由", text -> vos(DVo::getCause, obj -> obj.initDB(text))));
        });
        //禁止规则
        if (isAlterEnable()) {
            List<RuleFg> data = vor(DVo::getRuleList, RuleController::getViewBean);
            execute(data, item -> add(new InhibitionRuleBean(item.getTriLevel(), item.getRuleName(),
                    item.getRuleRemark())));
        }
        //GridBean 添加票据
        addVgBean(null, newGridBean(FILTER_YB));
        /*//添加流程
        if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }*/
    }

}
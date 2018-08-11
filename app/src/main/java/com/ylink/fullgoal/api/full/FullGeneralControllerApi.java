package com.ylink.fullgoal.api.full;

import com.leo.core.api.inter.CoreController;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.surface.NodeController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.fg.NodeFg;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.norm.InhibitionRuleNorm;
import com.ylink.fullgoal.norm.TvH2MoreNorm;
import com.ylink.fullgoal.norm.TvH2Norm;
import com.ylink.fullgoal.norm.TvH4Norm;
import com.ylink.fullgoal.norm.TvHEt3Norm;
import com.ylink.fullgoal.norm.TvHintNorm;
import com.ylink.fullgoal.vo.DVo;
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
        addVgNorm(data -> {
            //经办人、部门
            data.add(new TvH2Norm("经办人", vorv(DVo::getAgent)));
            checkAdd(data, vorv(DVo::getReimbursement), new TvH2MoreNorm("报销人",
                    vorv(DVo::getReimbursement), "请选择报销人",
                    (bean, view) -> routeApi().searchValue(SearchVo.REIMBURSEMENT,
                            vorc(DVo::getReimbursement)),
                    (bean, view) -> vos(DVo::getReimbursement, CoreController::clear)));
            checkAdd(data, vorv(DVo::getBudgetDepartment), new TvH2MoreNorm("预算归属",
                    vorv(DVo::getBudgetDepartment), "请选择预算归属",
                    (bean, view) -> routeApi().searchValue(SearchVo.BUDGET_DEPARTMENT,
                            vorc(DVo::getBudgetDepartment)),
                    (bean, view) -> vos(DVo::getBudgetDepartment, CoreController::clear)));
            checkAdd(data, vorv(DVo::getProject), new TvH2MoreNorm("项目",
                    vorv(DVo::getProject), "请选择项目",
                    (bean, view) -> routeApi().search(SearchVo.PROJECT, vorc(DVo::getBudgetDepartment),
                            vorc(DVo::getProject)),
                    (bean, view) -> vos(DVo::getProject, CoreController::clear)));
            checkAdd(data, vorv(DVo::getCostIndex), new TvH2MoreNorm("费用指标",
                    vorv(DVo::getCostIndex), "请选择费用指标",
                    (bean, view) -> routeApi().search(SearchVo.COST_INDEX, getParams(),
                            vorc(DVo::getCostIndex)),
                    (bean, view) -> vos(DVo::getCostIndex, CoreController::clear)));
            checkAdd(data, vorv(DVo::getApply), new TvH2MoreNorm("申请单",
                    vorv(DVo::getApply), "请选择申请单", (bean, view)
                    -> routeApi().searchApply(SearchVo.APPLY, getParams(),
                    encode(getVo().getApply())), (bean, view) -> vos(DVo::getApply, CoreController::clear)));
            //经办人确认、经办人修改
            if (isNoneInitiateEnable()) {
                checkAdd(data, vorv(DVo::getMoney), new TvH2Norm("金额", vorv(DVo::getMoney)));
            }
            checkAdd(data, vorv(DVo::getCause), new TvHEt3Norm("事由", vorv(DVo::getCause),
                    "请输入事由", text -> vos(DVo::getCause, obj -> obj.initDB(text))));
        });
        //禁止规则
        if (isAlterEnable()) {
            List<RuleFg> fgData = vor(DVo::getRuleList, RuleController::getViewBean);
            addVgNorm(data -> {
                if (!TextUtils.isEmpty(fgData)) {
                    data.add(new TvHintNorm("禁止规则", false));
                    execute(fgData, item -> data.add(new InhibitionRuleNorm(
                            item.getTriLevel(), item.getRuleName(), item.getRuleRemark())));
                }
            });
        }
        //GridBean 添加票据
        addVgNorm("票据", newGridNorm(FILTER_YB));
        //添加流程
        List<NodeFg> nodeData = vor(DVo::getTaskNode, NodeController::getViewBean);
        if (!isEnable() && !TextUtils.isEmpty(nodeData)) {
            addVgNorm(data -> {
                data.add(new TvH4Norm());
                execute(nodeData, item -> data.add(new TvH4Norm(item.getName(),
                        item.getNode(), item.getOpinion(), item.getProcessTime())));
            });
        }
    }

}
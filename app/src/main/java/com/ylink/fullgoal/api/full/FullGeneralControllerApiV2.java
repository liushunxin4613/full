package com.ylink.fullgoal.api.full;

import com.google.gson.reflect.TypeToken;
import com.leo.core.api.inter.CoreController;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.cr.surface.NodeController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.fg.ApplyDataFgV1;
import com.ylink.fullgoal.fg.NodeFg;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.vo.DVoV1;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.YB;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_YB;

/**
 * 一般费用报销
 */
public class FullGeneralControllerApiV2<T extends FullGeneralControllerApiV2, C> extends FullReimburseControllerApi<T, C> {

    public FullGeneralControllerApiV2(C controller) {
        super(controller);
    }

    @Override
    public DVoV1 getVo() {
        return (DVoV1) super.getVo();
    }

    @Override
    public DVoV1 newVo() {
        return new DVoV1();
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
    public void onResume() {
        execute(getFinish(), new TypeToken<SearchVo<List<ApplyDataFgV1>>>() {
        }, vo -> vor(DVoV1::getCostIndexValue, obj -> obj.initDB(vo.getObj())));
        super.onResume();
    }

    @Override
    protected void onData() {
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean("经办人", vorv(DVo::getAgent)));
            checkAdd(data, vorv(DVo::getReimbursement), new TvH2MoreBean("报销人",
                    vorv(DVo::getReimbursement), "请选择报销人",
                    (bean, view) -> routeApi().searchValue(SearchVo.REIMBURSEMENT,
                            vorc(DVo::getReimbursement)),
                    (bean, view) -> vos(DVo::getReimbursement, CoreController::clear)));
            checkAdd(data, vorv(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属",
                    vorv(DVo::getBudgetDepartment), "请选择预算归属",
                    (bean, view) -> routeApi().searchValue(SearchVo.BUDGET_DEPARTMENT,
                            vorc(DVo::getBudgetDepartment)),
                    (bean, view) -> vos(DVo::getBudgetDepartment, CoreController::clear)));
            checkAdd(data, vorv(DVo::getProject), new TvH2MoreBean("项目",
                    vorv(DVo::getProject), "请选择项目",
                    (bean, view) -> routeApi().search(SearchVo.PROJECT, vorc(DVo::getBudgetDepartment),
                            vorc(DVo::getProject)),
                    (bean, view) -> vos(DVo::getProject, CoreController::clear)));
            checkAdd(data, vorv(DVo::getCostIndex), new TvH2MoreBean("费用指标",
                    vorv(DVo::getCostIndex), "请选择费用指标",
                    (bean, view) -> routeApi().search(SearchVo.COST_INDEX, getParams(),
                            vorc(DVo::getCostIndex)),
                    (bean, view) -> vos(DVo::getCostIndex, CoreController::clear)));
            checkAdd(data, vorv(DVoV1::getApply), new TvH2MoreBean("申请单",
                    vorv(DVoV1::getApply), "请选择申请单", (bean, view)
                    -> routeApi().searchApply(SearchVo.APPLY, getParams(),
                    encode(getVo().getApply())), (bean, view) -> vos(DVoV1::getApply, CoreController::clear)));
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
            execute(data, item -> add(new InhibitionRuleBean(item.getTriLevel(),
                    item.getRuleName(), item.getRuleRemark())));
        }
        //GridBean 添加票据
        addVgBean("票据", newGridBean(FILTER_YB));
        //添加流程
        List<NodeFg> nodeData = vor(DVo::getTaskNode, NodeController::getViewBean);
        if (!isEnable() && !TextUtils.isEmpty(nodeData)) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(nodeData, item -> data.add(new TvH4Bean(item.getName(),
                        item.getNode(), item.getOpinion(), item.getProcessTime())));
            });
        }
    }

}
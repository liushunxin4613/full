package com.ylink.fullgoal.api.full;

import com.google.gson.reflect.TypeToken;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHTvIconMoreBean;
import com.ylink.fullgoal.cr.surface.CostIndexController;
import com.ylink.fullgoal.cr.surface.DepartmentController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.UserController;
import com.ylink.fullgoal.fg.ApplyDataFgV1;
import com.ylink.fullgoal.fg.CostFg;
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
    public void initView() {
        super.initView();
        vos(DVoV1::getCostIndex, obj -> obj.initDB(new CostFg("005", "指标5")));
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
            data.add(new TvH2Bean(vorv(DVo::getAgent), vorv(DVo::getDepartment)));
            checkAdd(data, vorv(DVo::getReimbursement), new TvHTvIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", vorv(DVo::getReimbursement), "请输入报销人", (bean, view)
                    -> routeApi().search(SearchVo.REIMBURSEMENT), text
                    -> vos(DVo::getReimbursement, UserController::getDB, db -> db.setUserName(text))));
            checkAdd(data, vorv(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属部门",
                    vorv(DVo::getBudgetDepartment), "请选择预算归属部门",
                    (bean, view) -> routeApi().search(SearchVo.BUDGET_DEPARTMENT)));
            checkAdd(data, vorv(DVo::getProject), new TvH2MoreBean("项目", vorv(DVo::getProject),
                    "请选择项目", (bean, view) -> routeApi().search(SearchVo.PROJECT)));
            checkAdd(data, vorv(DVo::getCostIndex), new TvH2MoreBean("费用指标",
                    vorv(DVo::getCostIndex), "请选择费用指标", (bean, view)
                    -> routeApi().search(SearchVo.COST_INDEX)));
            /*checkAdd(data, vorv(DVoV1::getCostIndexValue), new TvH2MoreBean("单据内容",
                    vorv(DVoV1::getCostIndexValue), "请选择单据内容", (bean, view)
                    -> startSearch(FullSearchControllerApiV2.class, SearchVo.APPLY,
                    vor(DVo::getCostIndex, CostIndexController::getDB, CostFg::getCostCode),
                    encode(vor(DVoV1::getCostIndexValue, CostIndexValueController::getDB)))));*/
            checkAdd(data, vorv(DVoV1::getCostIndexValue), new TvH2MoreBean("单据内容",
                    vorv(DVoV1::getCostIndexValue), "请选择单据内容", (bean, view)
                    -> routeApi().searchApply(SearchVo.APPLY,
                    encode(map(map -> map.put("departmentCode", vor(DVo::getBudgetDepartment, DepartmentController::getDepartmentCode))
                            .put("reimbursement", vor(DVo::getReimbursement, UserController::getUserCode))
                            .put("costIndexCode", vor(DVo::getCostIndex, CostIndexController::getCostCode)))))));
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
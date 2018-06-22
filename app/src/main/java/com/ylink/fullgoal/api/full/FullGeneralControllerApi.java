package com.ylink.fullgoal.api.full;

import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHTvIconMoreBean;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.UserController;
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
            data.add(new TvH2Bean(gtv(DVo::getAgent), gtv(DVo::getDepartment)));
            checkAdd(data, gtv(DVo::getReimbursement), new TvHTvIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", gtv(DVo::getReimbursement), "请输入报销人", (bean, view)
                    -> startSearch(SearchVo.REIMBURSEMENT), text
                    -> iso(DVo::getReimbursement, UserController::getDB, db -> db.setUserName(text))));
            checkAdd(data, gtv(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属部门",
                    gtv(DVo::getBudgetDepartment), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            checkAdd(data, gtv(DVo::getProject), new TvH2MoreBean("项目", gtv(DVo::getProject), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
            checkAdd(data, gtv(DVo::getContractPayment), new TvH2MoreBean("合同付款申请单",
                    gtv(DVo::getContractPayment), "请选择合同付款申请单",
                    (bean, view) -> startSearch(SearchVo.CONTRACT_BILL)));
            checkAdd(data, gtv(DVo::getProcess), new TvH2MoreBean("招待申请单",
                    gtv(DVo::getProcess), "请选择招待申请单",
                    (bean, view) -> startSearch(SearchVo.SERVE_BILL)));
            checkAdd(data, gtv(DVo::getCostIndex),
                    new TvH2MoreBean("费用指标", gtv(DVo::getCostIndex), "请选择费用指标",
                            (bean, view) -> startSearch(SearchVo.COST_INDEX)));
            //经办人确认、经办人修改
            if (isNoneInitiateEnable()) {
                checkAdd(data, gtv(DVo::getMoney), new TvH2Bean("金额", gtv(DVo::getMoney)));
            }
            checkAdd(data, gtv(DVo::getCause), new TvHEt3Bean("事由", gtv(DVo::getCause),
                    "请输入事由", text -> iso(DVo::getCause, obj -> obj.initDB(text))));
        });
        //禁止规则
        if (isAlterEnable()) {
            List<RuleFg> data = gt(DVo::getRuleList, RuleController::getViewBean);
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
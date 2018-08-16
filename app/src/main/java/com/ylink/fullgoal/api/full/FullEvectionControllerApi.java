package com.ylink.fullgoal.api.full;

import com.leo.core.api.inter.CoreController;
import com.leo.core.iapi.inter.IAction;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.cr.surface.CtripTicketsController;
import com.ylink.fullgoal.cr.surface.DepartmentController;
import com.ylink.fullgoal.cr.surface.NodeController;
import com.ylink.fullgoal.cr.surface.ResearchReportController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.TravelFormController;
import com.ylink.fullgoal.factory.CoreJsonFactory;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.NodeFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.norm.ChuchaiNorm;
import com.ylink.fullgoal.norm.DiaoyanNorm;
import com.ylink.fullgoal.norm.IconTvHNorm;
import com.ylink.fullgoal.norm.InhibitionRuleNorm;
import com.ylink.fullgoal.norm.TvH2MoreNorm;
import com.ylink.fullgoal.norm.TvH2Norm;
import com.ylink.fullgoal.norm.TvH4Norm;
import com.ylink.fullgoal.norm.TvHEt3Norm;
import com.ylink.fullgoal.norm.TvHintNorm;
import com.ylink.fullgoal.norm.TvNorm;
import com.ylink.fullgoal.norm.XiechengNorm;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.vo.ImageVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_CCJPF;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_JTF;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_ZSF;

/**
 * 一般费用报销
 */
public class FullEvectionControllerApi<T extends FullEvectionControllerApi, C> extends FullReimburseControllerApi<T, C> {

    public FullEvectionControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected String getBType() {
        return CC;
    }

    @Override
    protected String getBTitle() {
        return "出差费用报销";
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
                    (bean, view) -> routeApi().searchEvection(SearchVo.COST_INDEX,
                            getParams(), vorc(DVo::getCostIndex)),
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
        //VgBean 出差申请单
        addVgNorm(data -> {
            List<TravelFormFg> list = vor(DVo::getTrave, TravelFormController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvNorm("出差申请单添加"));
            }
            List<String> filterData = new ArrayList<>();
            execute(list, fg -> data.add(retExecute(getExecute(fg, item -> new ChuchaiNorm(
                    item.getCode(), item.getAmount(), item.getDestination(), item.getDates(),
                    item.getStartDate(), item.getEndDate(), item.getWorkName(), (bean, view) -> {
                if (isEnable()) {
                    initVgApiBean("出差申请单", ()
                            -> vos(DVo::getTrave, obj -> obj.remove(item, this)));
                }
            })), bean -> filterData.add(bean.getCode()))));
            if (isEnable()) {
                data.add(new IconTvHNorm("添加出差申请单", (bean, view)
                        -> routeApi().search(SearchVo.BUSINESS, getParams(), filterData)));
            }
        });
        //VgBean 投研报告
        checkReport(() -> addVgNorm(data -> {
            List<ResearchReportFg> list = vor(DVo::getReport, ResearchReportController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvNorm("投研报告添加"));
            }
            List<String> filterData = new ArrayList<>();
            execute(list, fg -> data.add(retExecute(getExecute(fg, item -> new DiaoyanNorm(
                    item.getStockCode(), item.getStockName(), item.getType(), item.getStatus(),
                    item.getUploadTime(), item.getEndTime(), item.getReportInfo(), (bean, view) -> {
                if (isEnable()) {
                    initVgApiBean("调研报告",
                            () -> vos(DVo::getReport, obj -> obj.remove(item, this)));
                }
            })), bean -> filterData.add(bean.getText()))));
            if (isEnable()) {
                data.add(new IconTvHNorm("添加投研报告", (bean, view)
                        -> routeApi().search(SearchVo.REPORT, getParams(), filterData)));
            }
        }));
        //GridBean 交通费报销
        addVgNorm("交通费报销", newGridNorm(FILTER_JTF));
        //GridBean 住宿费报销
        addVgNorm("住宿费报销", newGridNorm(FILTER_ZSF));
        //GridBean 车船机票费报销
        addVgNorm(data -> {
            List<CtripTicketsFg> ctripData = vor(DVo::getCtrip, CtripTicketsController::getViewBean);
            List<ImageVo> imageData = vor(DVo::getImageList, obj -> obj.getFilterDBData(FILTER_CCJPF));
            if (!(!isEnable() && TextUtils.isEmpty(ctripData) && TextUtils.isEmpty(imageData))) {
                data.add(new TvHintNorm("车船机票费报销", isEnable()));
            }
            List<String> filterData = new ArrayList<>();
            execute(ctripData, fg -> data.add(retExecute(getExecute(fg, item -> new XiechengNorm(
                    item.getFlightNumber(), item.getDeparture(), item.getDestination(),
                    item.getCrew(), item.getTakeOffDate(), item.getTakeOffTime(), item.getTicket(),
                    item.getArrivelDate(), item.getArrivelTime(),
                    (bean, view) -> {
                        if (isEnable()) {
                            initVgApiBean("携程机票",
                                    () -> vos(DVo::getCtrip, obj -> obj.remove(item, this)));
                        }
                    })), bean -> filterData.add(bean.getCode()))));
            if (isEnable()) {
                data.add(new IconTvHNorm("添加携程机票", (bean, view)
                        -> routeApi().search(SearchVo.XC_AIR, vorc(DVo::getReimbursement), filterData)));
            }
            if (!(!isEnable() && TextUtils.isEmpty(imageData))) {
                data.add(newGridNorm(FILTER_CCJPF, imageData));
            }
        });
        //添加流程
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

    private void checkReport(IAction action) {
        if (CoreJsonFactory.getInstance().reportContains(vor(DVo::getBudgetDepartment,
                DepartmentController::getViewBean))) {
            execute(action);
        }
    }

}
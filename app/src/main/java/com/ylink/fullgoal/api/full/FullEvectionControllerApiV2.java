package com.ylink.fullgoal.api.full;

import com.leo.core.api.inter.CoreController;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.bean.ChuchaiBean;
import com.ylink.fullgoal.bean.DiaoyanBean;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHintBean;
import com.ylink.fullgoal.bean.XiechengBean;
import com.ylink.fullgoal.cr.surface.CtripTicketsController;
import com.ylink.fullgoal.cr.surface.NodeController;
import com.ylink.fullgoal.cr.surface.ResearchReportController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.TravelFormController;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.NodeFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.vo.DVoV1;
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
public class FullEvectionControllerApiV2<T extends FullEvectionControllerApiV2, C> extends FullReimburseControllerApi<T, C> {

    public FullEvectionControllerApiV2(C controller) {
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
        return CC;
    }

    @Override
    protected String getBTitle() {
        return "出差费用报销";
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
                    (bean, view) -> routeApi().searchEvection(SearchVo.COST_INDEX,
                            getParams(), vorc(DVo::getCostIndex)),
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
            List<RuleFg> fgData = vor(DVo::getRuleList, RuleController::getViewBean);
            addVgBean(data -> {
                if(!TextUtils.isEmpty(fgData)){
                    data.add(new TvHintBean("禁止规则", false));
                    execute(fgData, item -> data.add(new InhibitionRuleBean(
                            item.getTriLevel(), item.getRuleName(), item.getRuleRemark())));
                }
            });
        }
        //VgBean 出差申请单
        addVgBean(data -> {
            List<TravelFormFg> list = vor(DVo::getTrave, TravelFormController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvBean("出差申请单添加"));
            }
            List<String> filterData = new ArrayList<>();
            execute(list, fg -> data.add(retExecute(getExecute(fg, item -> new ChuchaiBean(
                    item.getCode(), item.getAmount(), item.getDestination(), item.getDates(),
                    item.getStartDate(), item.getEndDate(), item.getWorkName(), (bean, view) -> {
                if (isEnable()) {
                    initVgApiBean("出差申请单", ()
                            -> vos(DVo::getTrave, obj -> obj.remove(item, this)));
                }
            })), bean -> filterData.add(bean.getFilter()))));
            if (isEnable()) {
                data.add(new IconTvHBean("添加出差申请单", (bean, view)
                        -> routeApi().search(SearchVo.BUSINESS, getParams(), filterData)));
            }
        });
        //VgBean 投研报告
        addVgBean(data -> {
            List<ResearchReportFg> list = vor(DVo::getReport, ResearchReportController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvBean("投研报告添加"));
            }
            List<String> filterData = new ArrayList<>();
            execute(list, fg -> data.add(retExecute(getExecute(fg, item -> new DiaoyanBean(
                    item.getStockCode(), item.getStockName(), item.getType(), item.getStatus(),
                    item.getUploadTime(), item.getEndTime(), item.getReportInfo(), (bean, view) -> {
                if (isEnable()) {
                    initVgApiBean("调研报告",
                            () -> vos(DVo::getReport, obj -> obj.remove(item, this)));
                }
            })), bean -> filterData.add(bean.getFilter()))));
            if (isEnable()) {
                data.add(new IconTvHBean("添加投研报告", (bean, view)
                        -> routeApi().search(SearchVo.REPORT, getParams(), filterData)));
            }
        });
        //GridBean 交通费报销
        addVgBean("交通费报销", newGridBean(FILTER_JTF));
        //GridBean 住宿费报销
        addVgBean("住宿费报销", newGridBean(FILTER_ZSF));
        //GridBean 车船机票费报销
        addVgBean(data -> {
            List<CtripTicketsFg> ctripData = vor(DVo::getCtrip, CtripTicketsController::getViewBean);
            List<ImageVo> imageData = vor(DVo::getImageList, obj -> obj.getFilterDBData(FILTER_CCJPF));
            if (!(!isEnable() && TextUtils.isEmpty(ctripData) && TextUtils.isEmpty(imageData))) {
                data.add(new TvHintBean("车船机票费报销", isEnable()));
            }
            List<String> filterData = new ArrayList<>();
            execute(ctripData, fg -> data.add(retExecute(getExecute(fg, item -> new XiechengBean(
                    item.getFlightNumber(), item.getDeparture(), item.getDestination(),
                    item.getCrew(), item.getTakeOffDate(), item.getTakeOffTime(), item.getTicket(),
                    item.getArrivelDate(), item.getArrivelTime(),
                    (bean, view) -> {
                        if (isEnable()) {
                            initVgApiBean("携程机票",
                                    () -> vos(DVo::getCtrip, obj -> obj.remove(item, this)));
                        }
                    })), bean -> filterData.add(bean.getFilter()))));
            if (isEnable()) {
                data.add(new IconTvHBean("添加携程机票", (bean, view)
                        -> routeApi().search(SearchVo.XC_AIR, vorc(DVo::getReimbursement), filterData)));
            }
            if (!(!isEnable() && TextUtils.isEmpty(imageData))) {
                data.add(newGridBean(FILTER_CCJPF, imageData));
            }
        });
        //添加流程
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
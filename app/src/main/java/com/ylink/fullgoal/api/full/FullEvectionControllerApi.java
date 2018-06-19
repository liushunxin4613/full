package com.ylink.fullgoal.api.full;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.vo.DVo;
import com.ylink.fullgoal.cr.surface.CtripTicketsController;
import com.ylink.fullgoal.cr.surface.ResearchReportController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.TravelFormController;
import com.ylink.fullgoal.cr.surface.UserController;
import com.ylink.fullgoal.fg.CtripTicketsFg;
import com.ylink.fullgoal.fg.ResearchReportFg;
import com.ylink.fullgoal.fg.RuleFg;
import com.ylink.fullgoal.fg.TravelFormFg;
import com.ylink.fullgoal.vo.ImageVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;

import static com.ylink.fullgoal.config.ComConfig.CC;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_CCJPF;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_JTF;
import static com.ylink.fullgoal.vo.ImageVo.FILTER_ZSF;

/**
 * 出差费用报销
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
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(gtv(DVo::getAgent), gtv(DVo::getDepartment)));
            checkAdd(data, gtv(DVo::getReimbursement), new TvHEtIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", gtv(DVo::getReimbursement), "请输入报销人", (bean, view)
                    -> startSearch(SearchVo.REIMBURSEMENT), text
                    -> iso(DVo::getReimbursement, UserController::getDB, db -> db.setUserName(text))));
            checkAdd(data, gtv(DVo::getBudgetDepartment), new TvH2MoreBean("预算归属部门",
                    gtv(DVo::getBudgetDepartment), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            checkAdd(data, gtv(DVo::getProject), new TvH2MoreBean("项目", gtv(DVo::getProject), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
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
        //VgBean 出差申请单
        addVgBean(data -> {
            List<TravelFormFg> list = gt(DVo::getTrave, TravelFormController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvBean("出差申请单添加"));
            }
            ArrayList<String> filterData = new ArrayList<>();
            execute(list, fg -> {
                data.add(getExecute(fg, item -> new CCSQDBean(item.getCode(), String.format("%s天",
                        item.getDates()), item.getStartDate(), String.format("至%s", item.getEndDate()),
                        (bean, view) -> initVgApiBean("出差申请单",
                                () -> iso(DVo::getTrave, obj -> obj.remove(item, this))))));
                filterData.add(fg.getAmount());
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加出差申请单", (bean, view) -> startSearch(
                        SearchVo.BUSINESS, filterData)));
            }
        });
        //VgBean 投研报告
        addVgBean(data -> {
            List<ResearchReportFg> list = gt(DVo::getReport, ResearchReportController::getViewBean);
            if (!(!isEnable() && TextUtils.isEmpty(list))) {
                data.add(new TvBean("投研报告添加"));
            }
            ArrayList<String> filterData = new ArrayList<>();
            execute(list, fg -> {
                filterData.add(fg.getProjectCode());
                data.add(getExecute(fg, item -> (new TvH2SBean(item.getReportInfo(), item.getStockName(),
                        (bean, view) -> initVgApiBean("调研报告",
                                () -> iso(DVo::getReport, obj -> obj.remove(item, this)))))));
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加投研报告", (bean, view) -> startSearch(SearchVo.REPORT,
                        filterData)));
            }
        });
        //GridBean 交通费报销
        addVgBean("交通费报销", newGridBean(FILTER_JTF));
        //GridBean 住宿费报销
        addVgBean("住宿费报销", newGridBean(FILTER_ZSF));
        //GridBean 车船机票费报销
        addVgBean(data -> {
            List<CtripTicketsFg> ctripData = gt(DVo::getCtrip, CtripTicketsController::getViewBean);
            List<ImageVo> imageData = gt(DVo::getImageList, obj -> obj.getFilterDBData(FILTER_CCJPF));
            if (!(!isEnable() && TextUtils.isEmpty(ctripData) && TextUtils.isEmpty(imageData))) {
                data.add(new TvBean("车船机票费报销"));
            }
            ArrayList<String> filterData = new ArrayList<>();
            execute(ctripData, fg -> {
                filterData.add(fg.getFlightNumber());
                data.add(getExecute(fg, item -> new XCJPBean(item.getCrew(), item.getTicket(),
                        item.getFlightNumber(), String.format("%s 开", item.getArrivelTime()),
                        String.format("%s 到", item.getArrivelTime()),
                        String.format("%s - %s", item.getDeparture(), item.getDestination()),
                        (bean, view) -> initVgApiBean("携程机票",
                                () -> iso(DVo::getCtrip, obj -> obj.remove(item, this))))));
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加携程机票", (bean, view) -> startSearch(SearchVo.XC_AIR,
                        filterData)));
            }
            if (!(!isEnable() && TextUtils.isEmpty(imageData))) {
                data.add(newGridBean(FILTER_CCJPF, imageData));
            }
        });
        //添加流程
        /*if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }*/
    }

}
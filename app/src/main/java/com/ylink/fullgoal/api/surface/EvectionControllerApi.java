package com.ylink.fullgoal.api.surface;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.R;
import com.ylink.fullgoal.bean.CCSQDBean;
import com.ylink.fullgoal.bean.IconTvHBean;
import com.ylink.fullgoal.bean.InhibitionRuleBean;
import com.ylink.fullgoal.bean.TvBean;
import com.ylink.fullgoal.bean.TvH2Bean;
import com.ylink.fullgoal.bean.TvH2MoreBean;
import com.ylink.fullgoal.bean.TvH2SBean;
import com.ylink.fullgoal.bean.TvH4Bean;
import com.ylink.fullgoal.bean.TvHEt3Bean;
import com.ylink.fullgoal.bean.TvHEtIconMoreBean;
import com.ylink.fullgoal.bean.XCJPBean;
import com.ylink.fullgoal.hb.CtripHb;
import com.ylink.fullgoal.hb.ReportHb;
import com.ylink.fullgoal.hb.TraveHb;
import com.ylink.fullgoal.vo.AirDataVo;
import com.ylink.fullgoal.vo.BillVo;
import com.ylink.fullgoal.vo.ReimburseVo;
import com.ylink.fullgoal.vo.SearchVo;

import java.util.List;
import java.util.Map;

import static com.leo.core.util.TextUtils.getSetData;

/**
 * 出差费用报销
 */
public class EvectionControllerApi<T extends EvectionControllerApi, C> extends ReimburseControllerApi<T, C> {

    public EvectionControllerApi(C controller) {
        super(controller);
    }

    @Override
    protected void onReimburseVo(ReimburseVo vo) {
        super.onReimburseVo(vo);
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(vo.getAgent(), vo.getDepartment()));
            data.add(new TvHEtIconMoreBean(R.mipmap.test_icon_user, "报销人", vo.getReimbursement(),
                    "请输入报销人", (bean, view) -> startSearch(SearchVo.REIMBURSEMENT), vo::setReimbursement));
            data.add(new TvH2MoreBean("预算归属部门", vo.getBudgetDepartment(), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            data.add(new TvH2MoreBean("项目", vo.getProject(), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
            //经办人确认、经办人修改
            if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                data.add(new TvHEtIconMoreBean("金额", vo.getTotalAmountLower(), "请输入金额"));
            }
            data.add(new TvHEt3Bean("事由", vo.getCause(), "请输入事由", vo::setCause));
        });
        //禁止规则
        if (isAlterEnable() && !TextUtils.isEmpty(vo.getInhibitionRuleData())) {
            execute(vo.getInhibitionRuleData(), obj -> add(new InhibitionRuleBean(obj.getState(),
                    obj.getName(), obj.getDetail())));
        }
        //VgBean 出差申请单
        addVgBean(data -> {
            if (!(!isEnable() && TextUtils.isEmpty(vo.getTraveData()))) {
                data.add(new TvBean("出差申请单添加"));
            }
            execute(vo.getTraveData(), item -> data.add(getCCSQDBean(item)));
            if (isEnable()) {
                data.add(new IconTvHBean("添加出差申请单", (bean, view) -> startSearch(SearchVo.BUSINESS)));
            }
        });
        //VgBean 投研报告
        addVgBean(data -> {
            if (!(!isEnable() && TextUtils.isEmpty(vo.getReportData()))) {
                data.add(new TvBean("投研报告添加"));
            }
            execute(vo.getReportData(), item -> data.add(getTvH2SBean(item)));
            if (isEnable()) {
                data.add(new IconTvHBean("添加投研报告", (bean, view) -> startSearch(SearchVo.REPORT)));
            }
        });
        //GridBean 交通费报销
        addVgBean("交通费报销", newGridBean(TYPE_JT, vo.getTrafficBillData()));
        //GridBean 住宿费报销
        addVgBean("住宿费报销", newGridBean(TYPE_ZS, vo.getStayBillData()));
        //GridBean 车船机票费报销
        addVgBean(data -> {
            AirDataVo airDataVo = vo.getAirDataVo();
            List<CtripHb> airData = no(airDataVo, AirDataVo::getCtripData);
            List<BillVo> airBillData = no(airDataVo, AirDataVo::getAirBillData);
            if (!(!isEnable() && airDataVo.isEmpty())) {
                data.add(new TvBean("车船机票费报销"));
            }
            execute(airData, item -> data.add(getXCJPBean(item)));
            if (isEnable()) {
                data.add(new IconTvHBean("添加携程机票", (bean, view) -> startSearch(SearchVo.XC_AIR)));
            }
            if (!(!isEnable() && TextUtils.isEmpty(airBillData))) {
                data.add(newGridBean(TYPE_CCJP, airBillData));
            }
        });
        //添加流程
        if (!isEnable() && !TextUtils.isEmpty(vo.getProcessData())) {
            addVgBean(data -> {
                data.add(new TvH4Bean());
                execute(vo.getProcessData(), item -> data.add(new TvH4Bean(item.getUser(),
                        item.getNode(), item.getApprovalOpinion(), item.getTime())));
            });
        }
    }

    @Override
    protected void submit() {
        super.submit();
        Map<String, String> checkMap = getCheck(getVo(), getSetData("报销类型",
                "经办人", "报销人", "预算归属部门", "事由"), getSetData("项目",
                "出差申请单", "交通费", "住宿费", "车船机票费"));
        ee("vo", getVo());
        ee("checkMap", checkMap);
        /*if (!TextUtils.isEmpty(checkMap)) {
            post("FkSbumitCompensation", map -> map.putAll(checkMap));
        }*/
    }

    //私有的

    private CCSQDBean getCCSQDBean(TraveHb hb) {
        return getExecute(hb, item -> new CCSQDBean(item.getCode(), String.format("%s天", item.getDates()),
                item.getStartDate(), String.format("至%s", item.getEndDate()),
                (bean, view) -> initVgApiBean("出差申请单", () -> {
                    if (!TextUtils.isEmpty(getVo().getTraveData())) {
                        getVo().getTraveData().remove(hb);
                        notifyReimburseVoChanged();
                    }
                })));
    }

    private XCJPBean getXCJPBean(CtripHb hb) {
        return getExecute(hb, item -> new XCJPBean(item.getCrew(), item.getTicket(), item.getFlightNumber(),
                String.format("%s 开", item.getTakeoffTime()), String.format("%s 到", item.getArrivelTime()),
                String.format("%s - %s", item.getDeparture(), item.getDestination()),
                (bean, view) -> initVgApiBean("携程机票", () -> {
                    if (getVo().getAirDataVo() != null && !TextUtils.isEmpty(getVo().getAirDataVo()
                            .getCtripData())) {
                        getVo().getAirDataVo().getCtripData().remove(hb);
                        notifyReimburseVoChanged();
                    }
                })));
    }

    private TvH2SBean getTvH2SBean(ReportHb hb) {
        return getExecute(hb, item -> (new TvH2SBean(item.getReportInfo(), item.getStockName(),
                (bean, view) -> initVgApiBean("调研报告", () -> {
                    if (!TextUtils.isEmpty(getVo().getReportData())) {
                        getVo().getReportData().remove(hb);
                        notifyReimburseVoChanged();
                    }
                }))));
    }

}
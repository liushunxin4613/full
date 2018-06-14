package com.ylink.fullgoal.api.full;

/**
 * 出差费用报销
 */
public class FullEvectionControllerApi<T extends FullEvectionControllerApi, C> extends FullReimburseControllerApi<T, C> {

    public FullEvectionControllerApi(C controller) {
        super(controller);
    }

    /*@Override
    protected void onReimburseVo(ReimburseVo vo) {
        super.onReimburseVo(vo);
        //VgBean 基本信息组
        addVgBean(data -> {
            //经办人、部门
            data.add(new TvH2Bean(getExecute(vo.getAgent(), UserList::getName), vo.getDepartmentShow()));
            checkAdd(data, getExecute(vo.getReimbursement(), UserList::getName), new TvHEtIconMoreBean(R.mipmap.test_icon_user,
                    "报销人", getExecute(vo.getReimbursement(), UserList::getName), "请输入报销人", (bean, view)
                    -> startSearch(SearchVo.REIMBURSEMENT), text -> {
                if(getVo().getReimbursement() != null){
                    getVo().getReimbursement().setName(text);
                }
            }));
            checkAdd(data, vo.getBudgetDepartmentShow(), new TvH2MoreBean("预算归属部门",
                    vo.getBudgetDepartmentShow(), "请选择预算归属部门",
                    (bean, view) -> startSearch(SearchVo.BUDGET_DEPARTMENT)));
            checkAdd(data, vo.getProjectShow(), new TvH2MoreBean("项目", vo.getProjectShow(), "请选择项目",
                    (bean, view) -> startSearch(SearchVo.PROJECT)));
            //经办人确认、经办人修改
            if (!TextUtils.equals(vo.getState(), ReimburseVo.STATE_INITIATE)) {
                checkAdd(data, vo.getTotalAmountLower(), new MoneyBean("金额",
                        vo.getTotalAmountLower(), "请输入金额", vo::setTotalAmountLower));
            }
            checkAdd(data, vo.getCause(), new TvHEt3Bean("事由", vo.getCause(), "请输入事由", vo::setCause));
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
            ArrayList<String> filterData = new ArrayList<>();
            execute(vo.getTraveData(), item -> {
                data.add(getCCSQDBean(item));
                filterData.add(item.getAmount());
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加出差申请单", (bean, view) -> startSearch(
                        SearchVo.BUSINESS, filterData)));
            }
        });
        //VgBean 投研报告
        addVgBean(data -> {
            if (!(!isEnable() && TextUtils.isEmpty(vo.getReportData()))) {
                data.add(new TvBean("投研报告添加"));
            }
            ArrayList<String> filterData = new ArrayList<>();
            execute(vo.getReportData(), item -> {
                data.add(getTvH2SBean(item));
                filterData.add(item.getProjectCode());
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加投研报告", (bean, view) -> startSearch(SearchVo.REPORT,
                        filterData)));
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
            if (!(!isEnable() && (airDataVo == null || airDataVo.isEmpty()))) {
                data.add(new TvBean("车船机票费报销"));
            }
            ArrayList<String> filterData = new ArrayList<>();
            execute(airData, item -> {
                data.add(getXCJPBean(item));
                filterData.add(item.getFlightNumber());
            });
            if (isEnable()) {
                data.add(new IconTvHBean("添加携程机票", (bean, view) -> startSearch(SearchVo.XC_AIR,
                        filterData)));
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
        uApi().submitReimburse(getCheckMap(getReimburseUpFg(vo -> {
            //投研报告编号集合
            List<String> traveList = new ArrayList<>();
            execute(vo.getTraveData(), trave -> traveList.add(trave.getCode()));
            //出差申请单编号集合
            List<String> reportName = new ArrayList<>();
            execute(vo.getReportData(), report -> reportName.add(report.getReportCode()));
            //携程机票编号集合
            List<String> ticketList = new ArrayList<>();
            AirDataVo airVo = vo.getAirDataVo();
            executeNon(airVo, air -> execute(air.getCtripData(), ctrip
                    -> ticketList.add(ctrip.getFlightNumber())));
            return new ReimburseUpFg(getFirst(), getSerialNo(), vo.getAgent(), vo.getReimbursement(),
                    vo.getBudgetDepartment(), vo.getProject(), vo.getTotalAmountLower(), vo.getCause(),
                    traveList, reportName, ticketList);
        }), getSetData("报销类型", "经办人", "报销人", "预算归属部门", "事由")));
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
    }*/

}
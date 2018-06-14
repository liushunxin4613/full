package com.ylink.fullgoal.vo;

import com.leo.core.util.JavaTypeUtil;
import com.leo.core.util.RunUtil;
import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.fg.ContractPaymentFg;
import com.ylink.fullgoal.fg.CostIndexFg;
import com.ylink.fullgoal.fg.MessageBackFg;
import com.ylink.fullgoal.fg.UserList;
import com.ylink.fullgoal.hb.CtripHb;
import com.ylink.fullgoal.hb.ImageHb;
import com.ylink.fullgoal.hb.ReimburseHb;
import com.ylink.fullgoal.hb.ReportHb;
import com.ylink.fullgoal.hb.TraveHb;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ylink.fullgoal.config.UrlConfig.MESSAGE_BACK_IMAGE_INVOICE_USE_CCJP;
import static com.ylink.fullgoal.config.UrlConfig.MESSAGE_BACK_IMAGE_INVOICE_USE_JT;
import static com.ylink.fullgoal.config.UrlConfig.MESSAGE_BACK_IMAGE_INVOICE_USE_YB;
import static com.ylink.fullgoal.config.UrlConfig.MESSAGE_BACK_IMAGE_INVOICE_USE_ZS;

/**
 * 报销
 * 25个字段
 * 7个data字段、2个引入字段、16个普通字段
 */
public class ReimburseVo extends HelpVo {

    //修改状态

    /**
     * 经办人发起
     */
    public final static String STATE_INITIATE = "经办人发起";
    /**
     * 经办人确认
     */
    public final static String STATE_CONFIRM = "经办人确认";
    /**
     * 经办人修改
     */
    public final static String STATE_ALTER = "经办人修改";
    /**
     * 报销详情
     */
    public final static String STATE_DETAIL = "报销详情";

    //发票类型

    /**
     * 一般费用普票报销
     */
    public final static String REIMBURSE_TYPE_GENERAL = "一般费用报销";
    /**
     * 出差费用普票报销
     */
    public final static String REIMBURSE_TYPE_EVECTION = "出差费用报销";

    //报销类型
    /**
     * 一般费用报销
     */
    public final static String BILL_TYPE_Y = "1";
    /**
     * 出差费用报销
     */
    public final static String BILL_TYPE_C = "2";
    /**
     * 专票
     */
    public final static String IS_TICKET_ZY = "1";
    /**
     * 普票
     */
    public final static String IS_TICKET_ZN = "2";

    //隐式的

    //渠道
    private String channel;
    //发起日期
    private String fillDate;
    //费控审批批次号
    private String fkApprovalNum;

    //界面不显示的

    //修改状态 发起、确认、修改
    private transient String state;
    //报销状态 待处理、审核中、已完成、已取消四类
    private String reimbursementState;
    //审批状态
    private String approvalStatus;
    //报销单号
    private String orderNo;
    //报销类型(自用)
    private String reimburseType;
    //报销类别 1.一般费用报销;2.出差费用报销;
    private String billType;
    //是否专票 1.专票;2.普票;
    private String isTickets;
    //费用指标需不需要分摊
    private String apportion;
    //费用指标
    private CastTargetVo castTarget;
    //费用指标
    private CostIndexFg costIndexFg;
    //禁止规则组
    private List<InhibitionRuleVo> inhibitionRuleData;
    //提交标志
    private String sbumitFlag;

    //UI需用的

    //报销批次号
    private String serialNo;
    //经办人
    private UserList agent;
    //报销人
    private UserList reimbursement;
    //部门
    private String department;
    private String departmentShow;
    //预算归属部门
    private String budgetDepartment;
    private String budgetDepartmentShow;
    //项目
    private String project;
    private String projectShow;
    //金额
    private String totalAmountLower;
    //事由
    private String cause;
    //票据组
    private List<BillVo> billData;
    //流程处理组
    private List<ProcessVo> processData;

    //一般费用报销单有

    //合同付款申请单
    private List<ContractPaymentFg> paymentRequestList;
    //招待申请单
    private String serveBill;
    private String serveBillShow;

    //出差费用报销单有

    //出差申请单组
    private List<TraveHb> traveData;
    //投研报告
    private List<ReportHb> reportData;
    //交通费报销票据组
    private List<BillVo> trafficBillData;
    //住宿费报销票据组
    private List<BillVo> stayBillData;
    //车船机票费报销
    private AirDataVo airDataVo;

    //总金额
    private double sum;

    public ReimburseVo() {
    }

    public ReimburseVo(MessageBackFg fg, String serialNo) {
        if (fg != null) {
            setAgent(fg.getAgent());
            setDepartment(fg.getBudgetDepartment());
            setReimbursement(fg.getReimbursement());
            setBudgetDepartment(fg.getBudgetDepartment());
            setCause(fg.getCause());
            setPaymentRequestList(fg.getPaymentRequest());
            setProject(fg.getProject());
            setServeBill(fg.getProcess());
            setCostIndexFg(RunUtil.getFirstNonItem(fg.getCostList()));
            setTotalAmountLower(fg.getTotalAmountLower());
            setReportData(getExecute(fg.getReport(), ReportHb::new));
            setTraveData(getExecute(fg.getTravel(), TraveHb::new));
            AirDataVo vo = new AirDataVo();
            vo.setCtripData(getExecute(fg.getCtrip(), CtripHb::new));
            Map<String, List<BillVo>> map = new HashMap<>();
            execute(fg.getImageList(), imageHb -> {
                List<BillVo> data = map.get(imageHb.getInvoiceUse());
                if (data == null) {
                    map.put(imageHb.getInvoiceUse(), new ArrayList<>());
                }
                if (!TextUtils.isEmpty(imageHb.getImageURL())) {
                    imageHb.setImageURL(imageHb.getImageURL().replaceAll("\\\\", "/"));
                }
                map.get(imageHb.getInvoiceUse()).add(new BillVo(imageHb, serialNo));
            });
            //一般
            setBillData(map.get(MESSAGE_BACK_IMAGE_INVOICE_USE_YB));
            //交通
            setTrafficBillData(map.get(MESSAGE_BACK_IMAGE_INVOICE_USE_JT));
            //住宿
            setStayBillData(map.get(MESSAGE_BACK_IMAGE_INVOICE_USE_ZS));
            //车船机票
            vo.setAirBillData(map.get(MESSAGE_BACK_IMAGE_INVOICE_USE_CCJP));
            setAirDataVo(vo);
            initTotalAmountLower();
        }
    }

    public ReimburseVo(ReimburseHb hb) {
        if (hb != null) {
            setSerialNo(hb.getSerialNo());
            setApprovalStatus(hb.getApprovalStatus());
            setFkApprovalNum(hb.getFkApprovalNum());
            setChannel(hb.getChannel());
            /*setAgent(hb.getAgent());
            setReimbursement(hb.getReimbursement());*/
            setFillDate(hb.getFillDate());
            setBudgetDepartment(hb.getBudgetDepartment());
            setTotalAmountLower(hb.getTotalAmountLower());
            setProject(hb.getProject());
            /*setPaymentRequest(hb.getPaymentRequest());*/
            setServeBill(hb.getServeBill());
            setCause(hb.getCause());
            setBillType(hb.getBillType());
            setIsTickets(hb.getIsTickets());
            setSbumitFlag(hb.getSbumitFlag());
            setTraveData(hb.getTraveList());
            setReportData(hb.getReportName());
            if (!TextUtils.isEmpty(hb.getTicketList()) ||
                    !TextUtils.isEmpty(hb.getImageList())) {
                AirDataVo vo = new AirDataVo();
                vo.setCtripData(hb.getTicketList());
                Map<String, List<BillVo>> map = new HashMap<>();
                execute(hb.getImageList(), imageHb -> {
                    List<BillVo> data = map.get(imageHb.getType());
                    if (data == null) {
                        map.put(imageHb.getType(), new ArrayList<>());
                    }
                    map.get(imageHb.getType()).add(new BillVo(imageHb));
                });
                //普通
                setBillData(map.get(ImageHb.IMAGE_NONE));
                //交通
                setTrafficBillData(map.get(ImageHb.IMAGE_JT));
                //住宿
                setStayBillData(map.get(ImageHb.IMAGE_ZS));
                //车船机票
                vo.setAirBillData(map.get(ImageHb.IMAGE_CCJP));
                if (!vo.isEmpty()) {
                    setAirDataVo(vo);
                }
            }
        }
    }

    /**
     * 重算金额
     */
    public void initTotalAmountLower() {
        setTotalAmountLower(new DecimalFormat("#").format(getSumBill()));
    }

    /**
     * 计算所有金额
     */
    public double getSumBill() {
        sum = 0;
        execute((BillVo vo) -> sum += JavaTypeUtil.getdouble(vo.getMoney(), 0),
                getBillData(), getStayBillData(), getTrafficBillData(),
                getExecute(getAirDataVo(), AirDataVo::getAirBillData));
        return sum;
    }

    public UserList getAgent() {
        return agent;
    }

    public void setAgent(UserList agent) {
        this.agent = agent;
    }

    public UserList getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(UserList reimbursement) {
        this.reimbursement = reimbursement;
    }

    public String getState() {
        return state;
    }

    public ReimburseVo setState(String state) {
        this.state = state;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public ReimburseVo setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getFillDate() {
        return fillDate;
    }

    public ReimburseVo setFillDate(String fillDate) {
        this.fillDate = fillDate;
        return this;
    }

    public String getReimbursementState() {
        return reimbursementState;
    }

    public ReimburseVo setReimbursementState(String reimbursementState) {
        this.reimbursementState = reimbursementState;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public ReimburseVo setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getApportion() {
        return apportion;
    }

    public ReimburseVo setApportion(String apportion) {
        this.apportion = apportion;
        return this;
    }

    public CastTargetVo getCastTarget() {
        return castTarget;
    }

    public ReimburseVo setCastTarget(CastTargetVo castTarget) {
        this.castTarget = castTarget;
        return this;
    }

    public List<InhibitionRuleVo> getInhibitionRuleData() {
        return inhibitionRuleData;
    }

    public ReimburseVo setInhibitionRuleData(List<InhibitionRuleVo> inhibitionRuleData) {
        this.inhibitionRuleData = inhibitionRuleData;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public ReimburseVo setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public ReimburseVo setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getBudgetDepartment() {
        return budgetDepartment;
    }

    public ReimburseVo setBudgetDepartment(String budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
        return this;
    }

    public String getProject() {
        return project;
    }

    public ReimburseVo setProject(String project) {
        this.project = project;
        return this;
    }

    public String getTotalAmountLower() {
        return totalAmountLower;
    }

    public ReimburseVo setTotalAmountLower(String totalAmountLower) {
        this.totalAmountLower = totalAmountLower;
        return this;
    }

    public String getCause() {
        return cause;
    }

    public ReimburseVo setCause(String cause) {
        this.cause = cause;
        return this;
    }

    public List<BillVo> getBillData() {
        return billData;
    }

    public ReimburseVo setBillData(List<BillVo> billData) {
        this.billData = billData;
        return this;
    }

    public List<ProcessVo> getProcessData() {
        return processData;
    }

    public ReimburseVo setProcessData(List<ProcessVo> processData) {
        this.processData = processData;
        return this;
    }

    public String getBillType() {
        return billType;
    }

    public ReimburseVo setBillType(String billType) {
        this.billType = billType;
        return this;
    }

    public String getIsTickets() {
        return isTickets;
    }

    public ReimburseVo setIsTickets(String isTickets) {
        this.isTickets = isTickets;
        return this;
    }

    public String getSbumitFlag() {
        return sbumitFlag;
    }

    public ReimburseVo setSbumitFlag(String sbumitFlag) {
        this.sbumitFlag = sbumitFlag;
        return this;
    }

    public List<ContractPaymentFg> getPaymentRequestList() {
        return paymentRequestList;
    }

    public ReimburseVo setPaymentRequestList(List<ContractPaymentFg> paymentRequestList) {
        this.paymentRequestList = paymentRequestList;
        return this;
    }

    public List<ReportHb> getReportData() {
        return reportData;
    }

    public ReimburseVo setReportData(List<ReportHb> reportData) {
        this.reportData = reportData;
        return this;
    }

    public String getServeBill() {
        return serveBill;
    }

    public ReimburseVo setServeBill(String serveBill) {
        this.serveBill = serveBill;
        return this;
    }

    public List<TraveHb> getTraveData() {
        return traveData;
    }

    public ReimburseVo setTraveData(List<TraveHb> traveData) {
        this.traveData = traveData;
        return this;
    }

    public List<BillVo> getTrafficBillData() {
        return trafficBillData;
    }

    public ReimburseVo setTrafficBillData(List<BillVo> trafficBillData) {
        this.trafficBillData = trafficBillData;
        return this;
    }

    public List<BillVo> getStayBillData() {
        return stayBillData;
    }

    public ReimburseVo setStayBillData(List<BillVo> stayBillData) {
        this.stayBillData = stayBillData;
        return this;
    }

    public AirDataVo getAirDataVo() {
        return airDataVo;
    }

    public ReimburseVo setAirDataVo(AirDataVo airDataVo) {
        this.airDataVo = airDataVo;
        return this;
    }

    public String getReimburseType() {
        return reimburseType;
    }

    public ReimburseVo setReimburseType(String reimburseType) {
        this.reimburseType = reimburseType;
        return this;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public ReimburseVo setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
        return this;
    }

    public String getFkApprovalNum() {
        return fkApprovalNum;
    }

    public ReimburseVo setFkApprovalNum(String fkApprovalNum) {
        this.fkApprovalNum = fkApprovalNum;
        return this;
    }

    public CostIndexFg getCostIndexFg() {
        return costIndexFg;
    }

    public void setCostIndexFg(CostIndexFg costIndexFg) {
        this.costIndexFg = costIndexFg;
    }

    public List<CostIndexFg> getCostIndexData() {
        return costIndexFg == null ? TextUtils.getListData() : TextUtils.getListData(costIndexFg);
    }

    //******************** show *********************

    public String getDepartmentShow() {
        return getExecute(departmentShow, department);
    }

    public void setDepartmentShow(String departmentShow) {
        this.departmentShow = departmentShow;
    }

    public String getBudgetDepartmentShow() {
        return getExecute(budgetDepartmentShow, budgetDepartment);
    }

    public void setBudgetDepartmentShow(String budgetDepartmentShow) {
        this.budgetDepartmentShow = budgetDepartmentShow;
    }

    public String getProjectShow() {
        return getExecute(projectShow, project);
    }

    public void setProjectShow(String projectShow) {
        this.projectShow = projectShow;
    }

    public String getServeBillShow() {
        return getExecute(serveBillShow, serveBill);
    }

    public void setServeBillShow(String serveBillShow) {
        this.serveBillShow = serveBillShow;
    }

}
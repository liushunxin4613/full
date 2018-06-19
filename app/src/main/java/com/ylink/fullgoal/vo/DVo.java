package com.ylink.fullgoal.vo;

import android.support.annotation.NonNull;

import com.leo.core.bean.NewFieldBean;
import com.leo.core.iapi.inter.IController;
import com.ylink.fullgoal.cr.surface.BillTypeController;
import com.ylink.fullgoal.cr.surface.CauseController;
import com.ylink.fullgoal.cr.surface.ContractPaymentController;
import com.ylink.fullgoal.cr.surface.CostIndexController;
import com.ylink.fullgoal.cr.surface.CtripTicketsController;
import com.ylink.fullgoal.cr.surface.DepartmentController;
import com.ylink.fullgoal.cr.surface.FillDateController;
import com.ylink.fullgoal.cr.surface.FirstController;
import com.ylink.fullgoal.cr.surface.ImageListController;
import com.ylink.fullgoal.cr.surface.LogoController;
import com.ylink.fullgoal.cr.surface.MoneyController;
import com.ylink.fullgoal.cr.surface.ProcessController;
import com.ylink.fullgoal.cr.surface.ProjectController;
import com.ylink.fullgoal.cr.surface.ResearchReportController;
import com.ylink.fullgoal.cr.surface.RuleController;
import com.ylink.fullgoal.cr.surface.SbumitFlagController;
import com.ylink.fullgoal.cr.surface.SerialNoController;
import com.ylink.fullgoal.cr.surface.TaskTypeController;
import com.ylink.fullgoal.cr.surface.TravelFormController;
import com.ylink.fullgoal.cr.surface.UserController;

import java.lang.reflect.Field;

/**
 * 提交数据核心
 */
public class DVo extends NewFieldBean {

    //报销批次号
    private SerialNoController serialNo;
    //是否第一次提交
    private FirstController first;
    //提交时间
    private FillDateController fillDate;
    //报销类型
    private BillTypeController billType;
    //报销修改的 提交方式
    private LogoController logo;
    //金额
    private MoneyController money;
    //提交标识
    private SbumitFlagController sbumitFlag;
    //提交标识
    private TaskTypeController taskType;
    //事由控制器
    private CauseController cause;
    //经办人
    private UserController agent;
    //报销人
    private UserController reimbursement;
    //部门
    private DepartmentController department;
    //预算归属部门
    private DepartmentController budgetDepartment;
    //项目
    private ProjectController project;
    //合同付款申请单
    private ContractPaymentController contractPayment;
    //招待申请单
    private ProcessController process;
    //费用指标
    private CostIndexController costIndex;
    //出差申请单
    private TravelFormController trave;
    //调研报告
    private ResearchReportController report;
    //携程机票
    private CtripTicketsController ctrip;
    //图片
    private ImageListController imageList;
    //评审结果集
    private RuleController ruleList;

    @Override
    protected boolean isOther(@NonNull Field field) {
        return IController.class.isAssignableFrom(field.getType());
    }

    public DVo() {
        initNewFields();
    }

    public FirstController getFirst() {
        return first;
    }

    public void setFirst(FirstController first) {
        this.first = first;
    }

    public SerialNoController getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(SerialNoController serialNo) {
        this.serialNo = serialNo;
    }

    public UserController getAgent() {
        return agent;
    }

    public void setAgent(UserController agent) {
        this.agent = agent;
    }

    public UserController getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(UserController reimbursement) {
        this.reimbursement = reimbursement;
    }

    public DepartmentController getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentController department) {
        this.department = department;
    }

    public DepartmentController getBudgetDepartment() {
        return budgetDepartment;
    }

    public void setBudgetDepartment(DepartmentController budgetDepartment) {
        this.budgetDepartment = budgetDepartment;
    }

    public ProjectController getProject() {
        return project;
    }

    public void setProject(ProjectController project) {
        this.project = project;
    }

    public ContractPaymentController getContractPayment() {
        return contractPayment;
    }

    public void setContractPayment(ContractPaymentController contractPayment) {
        this.contractPayment = contractPayment;
    }

    public ProcessController getProcess() {
        return process;
    }

    public void setProcess(ProcessController process) {
        this.process = process;
    }

    public CostIndexController getCostIndex() {
        return costIndex;
    }

    public void setCostIndex(CostIndexController costIndex) {
        this.costIndex = costIndex;
    }

    public TravelFormController getTrave() {
        return trave;
    }

    public void setTrave(TravelFormController trave) {
        this.trave = trave;
    }

    public ResearchReportController getReport() {
        return report;
    }

    public void setReport(ResearchReportController report) {
        this.report = report;
    }

    public CtripTicketsController getCtrip() {
        return ctrip;
    }

    public void setCtrip(CtripTicketsController ctrip) {
        this.ctrip = ctrip;
    }

    public CauseController getCause() {
        return cause;
    }

    public void setCause(CauseController cause) {
        this.cause = cause;
    }

    public ImageListController getImageList() {
        return imageList;
    }

    public void setImageList(ImageListController imageList) {
        this.imageList = imageList;
    }

    public FillDateController getFillDate() {
        return fillDate;
    }

    public void setFillDate(FillDateController fillDate) {
        this.fillDate = fillDate;
    }

    public BillTypeController getBillType() {
        return billType;
    }

    public void setBillType(BillTypeController billType) {
        this.billType = billType;
    }

    public LogoController getLogo() {
        return logo;
    }

    public void setLogo(LogoController logo) {
        this.logo = logo;
    }

    public MoneyController getMoney() {
        return money;
    }

    public void setMoney(MoneyController money) {
        this.money = money;
    }

    public SbumitFlagController getSbumitFlag() {
        return sbumitFlag;
    }

    public void setSbumitFlag(SbumitFlagController sbumitFlag) {
        this.sbumitFlag = sbumitFlag;
    }

    public TaskTypeController getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeController taskType) {
        this.taskType = taskType;
    }

    public RuleController getRuleList() {
        return ruleList;
    }

    public void setRuleList(RuleController ruleList) {
        this.ruleList = ruleList;
    }

}
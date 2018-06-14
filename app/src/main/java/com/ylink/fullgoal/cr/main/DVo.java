package com.ylink.fullgoal.cr.main;

import com.leo.core.bean.NewFieldBean;
import com.ylink.fullgoal.cr.CauseController;
import com.ylink.fullgoal.cr.ContractPaymentController;
import com.ylink.fullgoal.cr.CostIndexController;
import com.ylink.fullgoal.cr.CtripTicketsController;
import com.ylink.fullgoal.cr.DepartmentController;
import com.ylink.fullgoal.cr.ImageListController;
import com.ylink.fullgoal.cr.ProcessController;
import com.ylink.fullgoal.cr.ProjectController;
import com.ylink.fullgoal.cr.ResearchReportController;
import com.ylink.fullgoal.cr.SerialNoController;
import com.ylink.fullgoal.cr.StateController;
import com.ylink.fullgoal.cr.TravelFormController;
import com.ylink.fullgoal.cr.UserController;

/**
 * 数据核心
 */
public class DVo extends NewFieldBean {

    //不处理数据
    private StateController state;
    //报销批次号
    private SerialNoController serialNo;
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
    //事由控制器
    private CauseController cause;
    //图片
    private ImageListController imageList;

    public DVo() {
        initNewFields();
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

    public StateController getState() {
        return state;
    }

    public void setState(StateController state) {
        this.state = state;
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

}
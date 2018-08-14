package com.ylink.fullgoal.fg;

import com.leo.core.iapi.core.IModel;
import com.leo.core.util.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询列表
 */
public class DataFg extends StatusCodeFg {

    //消息
    private String message;
    //sessionId
    private String sessionId;
    //用户列表
    private List<UserFg> userList;
    //部门列表
    private List<DepartmentFg> departtList;
    //项目列表
    private List<ProjectFg> projecttList;
    //费用指标列表
    private List<CostFg> costIndextList;
    //出差申请单列表
    private List<TravelFormFg> travelList;
    //投研报告列表
    private List<ResearchReportFg> reportList;
    //携程机票列表
    private List<CtripTicketsFg> ctriptList;
    //报销查询列表
    private List<ApplicationtFg> applicationtList;
    //银行卡查询列表
    private List<BankFg> bank;
    //银行卡查询列表
    private List<DimenFg> dimen;
    //银行卡查询列表
    private List<DimenListFg> dimenList;
    //申请单
    private List<ApplyFgV2> appList2;
    //申请单内容
    private List<ApplyContentFgV2> applyCodeResult2;
    //文件流数组
    private byte[] fileByte;

    public List<IModel> getModelData() {
        List<IModel> data = new ArrayList<>();
        Class clz = getClass();
        while (clz != Object.class) {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field != null && field.getType().isAssignableFrom(List.class)) {
                    field.setAccessible(true);
                    try {
                        List list = (List) field.get(this);
                        if (!TextUtils.isEmpty(list)) {
                            for (Object item : list) {
                                if(item instanceof IModel){
                                    data.add((IModel) item);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clz = clz.getSuperclass();
        }
        return data;
    }

    public List<UserFg> getUserList() {
        return userList;
    }

    public void setUserList(List<UserFg> userList) {
        this.userList = userList;
    }

    public List<DepartmentFg> getDeparttList() {
        return departtList;
    }

    public void setDeparttList(List<DepartmentFg> departtList) {
        this.departtList = departtList;
    }

    public List<ProjectFg> getProjecttList() {
        return projecttList;
    }

    public void setProjecttList(List<ProjectFg> projecttList) {
        this.projecttList = projecttList;
    }

    public List<CostFg> getCostIndextList() {
        return costIndextList;
    }

    public void setCostIndextList(List<CostFg> costIndextList) {
        this.costIndextList = costIndextList;
    }

    public List<ResearchReportFg> getReportList() {
        return reportList;
    }

    public void setReportList(List<ResearchReportFg> reportList) {
        this.reportList = reportList;
    }

    public List<TravelFormFg> getTravelList() {
        return travelList;
    }

    public void setTravelList(List<TravelFormFg> travelList) {
        this.travelList = travelList;
    }

    public List<CtripTicketsFg> getCtriptList() {
        return ctriptList;
    }

    public void setCtriptList(List<CtripTicketsFg> ctriptList) {
        this.ctriptList = ctriptList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ApplicationtFg> getApplicationtList() {
        return applicationtList;
    }

    public void setApplicationtList(List<ApplicationtFg> applicationtList) {
        this.applicationtList = applicationtList;
    }

    public List<BankFg> getBank() {
        return bank;
    }

    public void setBank(List<BankFg> bank) {
        this.bank = bank;
    }

    public List<DimenFg> getDimen() {
        return dimen;
    }

    public void setDimen(List<DimenFg> dimen) {
        this.dimen = dimen;
    }

    public List<DimenListFg> getDimenList() {
        return dimenList;
    }

    public void setDimenList(List<DimenListFg> dimenList) {
        this.dimenList = dimenList;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public void setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
    }

    public List<ApplyFgV2> getAppList2() {
        return appList2;
    }

    public void setAppList2(List<ApplyFgV2> appList2) {
        this.appList2 = appList2;
    }

    public List<ApplyContentFgV2> getApplyCodeResult2() {
        return applyCodeResult2;
    }

    public void setApplyCodeResult2(List<ApplyContentFgV2> applyCodeResult2) {
        this.applyCodeResult2 = applyCodeResult2;
    }

}
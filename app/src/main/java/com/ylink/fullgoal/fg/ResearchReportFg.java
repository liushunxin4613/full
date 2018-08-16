package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.DiaoyanNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 投研报告
 */
public class ResearchReportFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new DiaoyanNorm(getStockCode(), getStockName(), getType(),
                    getStatus(), getUploadTime(), getEndTime(), getReportInfo(),
                    (bean, view) -> api.finishActivity(new SearchVo<>(api.getSearch(), getThis())))
                    .setAllShow(isAllShow());
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getStockCode(), getStockName(), getType(), getStatus(), getUploadTime(),
                getEndTime(), getReportInfo()};
    }

    @Override
    public String getApiCode() {
        return getReportInfo();
    }

    /**
     * endTime : 2018-05-26
     * projectCode : 00000
     * reportInfo : 计划信息
     * status : 调研中
     * stockCode : 7804606546913
     * stockName : 调研报告
     * type : 类型
     * uploadTime : 2018-05-23
     */

    private String endTime;
    private String projectCode;
    private String reportInfo;
    private String status;
    private String stockCode;
    private String stockName;
    private String type;
    private String uploadTime;
    private transient boolean allShow;//是否显示全部

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public boolean isAllShow() {
        return allShow;
    }

    public void setAllShow(boolean allShow) {
        this.allShow = allShow;
    }

}
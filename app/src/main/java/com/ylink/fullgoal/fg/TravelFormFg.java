package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.ChuchaiNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 出差申请单
 */
public class TravelFormFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new ChuchaiNorm(getCode(), getAmount(), getDestination(),
                    getDates(), getStartDate(), getEndDate(), getWorkName(),
                    (bean, view) -> api.finishActivity(new SearchVo<>(api.getSearch(), getThis())));
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getCode(), getAmount(), getDestination(), getDates(), getStartDate(),
                getEndDate(), getWorkName()};
    }

    @Override
    public String getApiCode() {
        return getCode();
    }

    /**
     * amount : 1000
     * code : 1
     * dates : 1
     * destination : 上海
     * endDate : 2018-06-06
     * startDate : 2018-06-06
     * workName : CeShi
     */

    private String amount;
    private String code;
    private String dates;
    private String destination;
    private String endDate;
    private String startDate;
    private String workName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}

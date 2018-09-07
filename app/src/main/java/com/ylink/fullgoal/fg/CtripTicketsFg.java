package com.ylink.fullgoal.fg;

import android.support.annotation.NonNull;

import com.leo.core.api.core.CoreModel;
import com.leo.core.iapi.core.INorm;
import com.leo.core.iapi.main.IControllerApi;
import com.ylink.fullgoal.controllerApi.surface.BaseSearchControllerApi;
import com.ylink.fullgoal.norm.XiechengNorm;
import com.ylink.fullgoal.vo.SearchVo;

/**
 * 携程机票
 */
public class CtripTicketsFg extends CoreModel {

    @Override
    protected INorm createNorm(@NonNull IControllerApi controllerApi) {
        if (controllerApi instanceof BaseSearchControllerApi) {
            BaseSearchControllerApi api = (BaseSearchControllerApi) controllerApi;
            return new XiechengNorm(getFlightNumber(), getDeparture(), getDestination(), getCrew(),
                    getTakeOffDate(), getTakeOffTime(), getTicket(), getArrivelDate(), getArrivelTime(),
                    (bean, view) -> api.finishActivity(new SearchVo<>(api.getSearch(), getThis())));
        }
        return null;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{getFlightNumber(), getDeparture(), getDestination(), getCrew(),
                getTakeOffDate(), getTakeOffTime(), getTicket(), getArrivelDate(), getArrivelTime()};
    }

    @Override
    public String getApiCode() {
        return String.format("%s&%s&%s", getFlightNumber(), getTakeOffDate(), getTakeOffTime());
    }

    /**
     * arrivelDate : 2018-05-25
     * arrivelTime : 11:11
     * crew : 钱海
     * departure : 北京
     * destination : 上海
     * flightNumber : K0829
     * takeOffDate : 2018-05-25
     * takeOffTime : 05:25
     * ticket : 778元
     */

    private String arrivelDate;
    private String arrivelTime;
    private String crew;
    private String departure;
    private String destination;
    private String flightNumber;
    private String takeOffDate;
    private String takeOffTime;
    private String ticket;

    public String getArrivelDate() {
        return arrivelDate;
    }

    public void setArrivelDate(String arrivelDate) {
        this.arrivelDate = arrivelDate;
    }

    public String getArrivelTime() {
        return arrivelTime;
    }

    public void setArrivelTime(String arrivelTime) {
        this.arrivelTime = arrivelTime;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getTakeOffDate() {
        return takeOffDate;
    }

    public void setTakeOffDate(String takeOffDate) {
        this.takeOffDate = takeOffDate;
    }

    public String getTakeOffTime() {
        return takeOffTime;
    }

    public void setTakeOffTime(String takeOffTime) {
        this.takeOffTime = takeOffTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}

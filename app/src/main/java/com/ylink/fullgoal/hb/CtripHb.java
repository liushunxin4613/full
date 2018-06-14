package com.ylink.fullgoal.hb;

import com.ylink.fullgoal.fg.CtripTicketsFg;

/**
 * 携程机票
 */
public class CtripHb extends SerialNoHb {

    /**
     * crew : 陈莹磊
     * flightNumber : 20180023
     * departure : 北京
     * destination : 深圳
     * takeoffDate : 2018-02-01
     * takeoffTime : 2018-02-01 14:00:00
     * arrivelDate : 2018-02-03
     * arrivelTime : 2018-02-03 08:00:00
     * ticket : 3400.00
     * reimbursement : 张三
     */

    private String crew;
    private String flightNumber;
    private String departure;
    private String destination;
    private String takeoffDate;
    private String takeoffTime;
    private String arrivelDate;
    private String arrivelTime;
    private String ticket;
    private String reimbursement;

    public CtripHb(CtripTicketsFg fg) {
        if (fg != null) {
            setCrew(fg.getCrew());
            setFlightNumber(fg.getFlightNumber());
            setDeparture(fg.getDeparture());
            setDestination(fg.getDestination());
            setTakeoffDate(fg.getTakeOffDate());
            setTakeoffTime(fg.getTakeOffTime());
            setArrivelDate(fg.getArrivelDate());
            setArrivelTime(fg.getArrivelTime());
            setTicket(fg.getTicket());
        }
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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

    public String getTakeoffDate() {
        return takeoffDate;
    }

    public void setTakeoffDate(String takeoffDate) {
        this.takeoffDate = takeoffDate;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(String takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(String reimbursement) {
        this.reimbursement = reimbursement;
    }

}

package com.ylink.fullgoal.vo;

import java.util.List;

/**
 * 车船机票费
 */
public class AirDataVo {

    //携程机票组
    private List<AirVo> xcAirData;
    //车船机票费报销票据组
    private List<BillVo> airBillData;

    public List<AirVo> getXcAirData() {
        return xcAirData;
    }

    public void setXcAirData(List<AirVo> xcAirData) {
        this.xcAirData = xcAirData;
    }

    public List<BillVo> getAirBillData() {
        return airBillData;
    }

    public void setAirBillData(List<BillVo> airBillData) {
        this.airBillData = airBillData;
    }

}

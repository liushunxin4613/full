package com.ylink.fullgoal.vo1;

import com.leo.core.util.TextUtils;
import com.ylink.fullgoal.hb.CtripHb;

import java.util.List;

/**
 * 车船机票费
 */
public class AirDataVo {

    //携程机票组
    private List<CtripHb> ctripData;
    //车船机票费报销票据组
    private List<BillVo> airBillData;

    public AirDataVo() {
    }

    public AirDataVo(List<CtripHb> ctripData, List<BillVo> airBillData) {
        this.ctripData = ctripData;
        this.airBillData = airBillData;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getCtripData()) && TextUtils.isEmpty(getAirBillData());
    }

    public List<CtripHb> getCtripData() {
        return ctripData;
    }

    public void setCtripData(List<CtripHb> ctripData) {
        this.ctripData = ctripData;
    }

    public List<BillVo> getAirBillData() {
        return airBillData;
    }

    public void setAirBillData(List<BillVo> airBillData) {
        this.airBillData = airBillData;
    }

}

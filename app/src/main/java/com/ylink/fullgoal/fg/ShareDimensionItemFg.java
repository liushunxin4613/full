package com.ylink.fullgoal.fg;

public class ShareDimensionItemFg {

    private String dimensionCode;//维度编码 （对应费控的表名）
    private String enumCode;//维度详细信息编号
    private String enumName;//维度详细信息名称

    public ShareDimensionItemFg(){
    }

    public ShareDimensionItemFg(String dimensionCode, String enumCode, String enumName) {
        this.dimensionCode = dimensionCode;
        this.enumCode = enumCode;
        this.enumName = enumName;
    }

    public String getDimensionCode() {
        return dimensionCode;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

}
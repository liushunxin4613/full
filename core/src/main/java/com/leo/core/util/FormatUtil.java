package com.leo.core.util;

/**
 * 文字格式化
 */
public class FormatUtil {

    private static String format(String format, Object... args){
        if(TextUtils.check(format, args)){
            return String.format(format, args);
        }
        return null;
    }

    /**
     * 格式化银行卡号信息
     */
    public static String toBankNo(String bankNo){
        if(TextUtils.count(bankNo) >= 4){
            return format("**** **** **** %s", bankNo.substring(bankNo.length() - 4, bankNo.length()));
        }
        return null;
    }

}
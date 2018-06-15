package com.leo.core.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类<br>
 * 主要功能应有<br>
 * 1.获取当前时间,返回Date;
 * 2.将时间(Date)转换成毫秒(int)(以1970/0/0日起,单位为毫秒)
 * 3.将时间(Date)转换成秒(long)(以1970/0/0日起,单位为秒)
 * 4.将时间(Date)通过格式(String)转换成相应格式的字符串(String)
 * 5.将时间(Date)转换成"yy-MM-dd HH:mm:ss"(24小时制)(String)
 * 6.将时间(Date)转换成"yy-MM-dd",获得日期(String)
 * 7.获取当前时间(Date),返回"yy-MM-dd HH:mm:ss"格式字符串(String)
 * 8.获取当前时间(Date),返回"yy-MM-dd"格式字符串(String)
 * 9.将字符串(String)转换成时间(Date)
 * 10.将字符串(String)转换成字符串(String)
 * 11.将时间增加或减少(按年月日时分秒)
 * 12.将时间增加或减少(按秒)
 */
public class DateUtil {

    /**
     * 获取当前时间,返回Date
     */
    public static Date getNowDate(){
        return new Date();
    }

    /**
     * 将时间(Date)转换成毫秒(int)(以1970/0/0日起,单位为毫秒)
     */
    public static long getLongTime(Date date){
        return date == null ? 0 : date.getTime();
    }

    /**
     * 将时间(Date)转换成秒(long)(以1970/0/0日起,单位为秒)
     */
    public static int getLongSecondTime(Date date){
        return (int) (getLongTime(date)/1000);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getFormatString(Date date, String format){
        return (date == null || TextUtils.isEmpty(format)) ? null : new SimpleDateFormat(format).format(date);
    }

    public static final String DATE_FORMAT_24 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将时间(Date)转换成"yy-MM-dd HH:mm:ss"(24小时制)(String)
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTimeString(Date date){
        return getFormatString(date, DATE_FORMAT_24);
    }

    public static final String DATE_FORMAT_TIME = "yyyy-MM-dd";

    /**
     * 将时间(Date)转换成"yy-MM-dd",获得日期(String)
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateString(Date date){
        return getFormatString(date, DATE_FORMAT_TIME);
    }

    /**
     * 将字符串(String)转换成时间(Date)
     */
    @SuppressLint("SimpleDateFormat")
    public static Date getFormatDate(String str, String format){
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(format)){
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
//            LogUtil.ee("将字符串(String)转换成时间(Date)", e.toString());
        }
        return null;
    }

    /**
     * 将字符串(String)转换成字符串(String)
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatDate(String str, String format, String format1){
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(format) || TextUtils.isEmpty(format1)){
            return null;
        }
        try {
            return new SimpleDateFormat(format1).format(new SimpleDateFormat(format).parse(str));
        } catch (ParseException e) {
//            LogUtil.ee("将字符串(String)转换成字符串(String)", e.toString());
        }
        return null;
    }

    /**
     * 将时间增加或减少
     */
    public static Date updateDate(Date date, int year, int month, int day, int hour, int minute, int second){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.DAY_OF_YEAR, day);
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, minute);
            calendar.add(Calendar.SECOND, second);
            return calendar.getTime();
        }
        return null;
    }

    //特殊例子
    public static String getNowTimeString(){
        return getTimeString(getNowDate());
    }

    public static String getNowDateString(){
        return getDateString(getNowDate());
    }

    public static Date getFormatDate(String str){
        return getFormatDate(str, DATE_FORMAT_24);
    }

    public static Date getFormatTime(String str){
        return getFormatDate(str, DATE_FORMAT_TIME);
    }

    public static Date updateDate(Date date, int year, int month, int day){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.DAY_OF_YEAR, day);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateYearDate(Date date, int year){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateMonthDate(Date date, int month){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, month);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateDayDate(Date date, int day){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, day);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateTimeDate(Date date, int hour, int minute, int second){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, hour);
            calendar.add(Calendar.MINUTE, minute);
            calendar.add(Calendar.SECOND, second);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateHourDate(Date date, int hour){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, hour);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateMinuteDate(Date date, int minute){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, minute);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateSecondDate(Date date, int second){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, second);
            return calendar.getTime();
        }
        return null;
    }

    public static Date updateDate(Date date, int second){
        if (date != null){
            date.setTime(date.getTime() + second * 1000);
            return date;
        }
        return null;
    }

    //新增 2018-06-15

    /**
     * 修改天数
     * @param day 新增天数
     * @return 时间
     */
    public static Date getDDate(int day){
        return getDate(new Date(), 0, 0, day, 0, 0, 0);
    }

    /**
     * 修改月数
     * @param month 新增月数
     * @return 时间
     */
    public static Date getMDate(int month){
        return getDate(new Date(), 0, month, 0, 0, 0, 0);
    }

    /**
     * 修改年数
     * @param year 新增年数
     * @return 时间
     */
    public static Date getYDate(int year){
        return getDate(new Date(), year, 0, 0, 0, 0, 0);
    }

    /**
     * 修改时间
     * @param date 时间
     * @param year 新增年数
     * @param month 新增月数
     * @param day 新增天数
     * @param hour 新增小时数
     * @param minute 新增分钟数
     * @param second 新增秒数
     * @return 时间
     */
    public static Date getDate(Date date, int year, int month, int day, int hour,
                        int minute, int second) {
        if (date != null) {
            Calendar theCa = Calendar.getInstance();
            theCa.setTime(date);
            if (year != 0) {
                theCa.add(Calendar.YEAR, year);
            }
            if (month != 0) {
                theCa.add(Calendar.MONTH, month);
            }
            if (day != 0) {
                theCa.add(Calendar.DATE, day);
            }
            if (hour != 0) {
                theCa.add(Calendar.HOUR, hour);
            }
            if (minute != 0) {
                theCa.add(Calendar.MINUTE, minute);
            }
            if (second != 0) {
                theCa.add(Calendar.SECOND, second);
            }
            return theCa.getTime();
        }
        return null;
    }

}

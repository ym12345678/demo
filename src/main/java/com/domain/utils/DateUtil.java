package com.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: LJ
 * @create: 2018-11-06
 **/
public class DateUtil {
    public static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT1 = "yyyy-MM-dd";


    public static Date stringToDate(String value) {
        Date date = null;
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(DATE_FORMAT2);
            date = sDateFormat.parse(value);
        } catch (ParseException px) {
            px.printStackTrace();
        }
        return date;
    }



    public static Date parserDate(String openTime, String s) {//加了这个方法
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
            date = simpleDateFormat.parse(openTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 将指定时间转为字符串
     *
     * @param date   指定时间
     * @param format 字符串格式
     * */
    public static String formatDate(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    public static String getToDay(){
        return getCurrentTime(DATE_FORMAT1);
    }

    /**
     * 根据指定格式获取当前时间
     * @author chenssy
     * @date Dec 27, 2013
     * @param format
     * @return String
     */
    public static String getCurrentTime(String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 两个时间之间相差距离多少天
     * @param date1 时间参数1
     * @param date2 时间参数2
     * @return 相差天数
     */
    public static long getIntervalDay(Date date1, Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long diff ;

        if(time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }

        return diff / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取指定日期的后几天
     * */
    public static Date getDateAfterDay(Date date, int amount){
        Calendar calendar = Calendar.getInstance();
        if(date == null){
            calendar.setTime(new Date());
        }else{
            calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

}

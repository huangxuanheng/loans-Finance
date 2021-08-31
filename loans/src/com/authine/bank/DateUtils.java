package com.authine.bank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 根据两个时间获取相隔几个月
     * @param fromDate
     * @param toDay
     * @return
     */
    public static int monthCompare(Date fromDate,Date toDay){
        Calendar from=Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to=Calendar.getInstance();
        to.setTime(toDay);
        //只要年月
        int fromYear=from.get(Calendar.YEAR);
        int fromMonth=from.get(Calendar.MONDAY);

        int toYear=from.get(Calendar.YEAR);
        int toMonth=from.get(Calendar.MONDAY);

        int month=toYear*12+toMonth-(fromYear*12+fromMonth);
        return month;
    }


    /**

     * 计算两个日期之间相差的天数

     * @param smdate 较小的时间

     * @param bdate 较大的时间

     * @return 相差天数

     * @throws ParseException

     */

    public static int daysBetween(Date smdate,Date bdate) throws ParseException

    {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        smdate=sdf.parse(sdf.format(smdate));

        bdate=sdf.parse(sdf.format(bdate));

        Calendar cal = Calendar.getInstance();

        cal.setTime(smdate);

        long time1 = cal.getTimeInMillis();

        cal.setTime(bdate);

        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));

    }

    /**

     *字符串的日期格式的计算

     */

    public static int daysBetween(String smdate,String bdate) throws ParseException{

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.setTime(sdf.parse(smdate));

        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(bdate));

        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));

    }


    public static Date formatDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getNextMonth(Date date){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);//增加一个月

        return calendar.getTime();
    }

    public static String formatDateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}

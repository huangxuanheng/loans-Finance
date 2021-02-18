package com.authine.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Harry
 * @date 2020/8/9
 * @des 描述：
 */
public class SecretMusic {



    public static void main(String[] args) {
        //当前日期
        System.out.println(getFirstMonthDate());
        System.out.println(getFirstMonthDate());
        System.out.println(getMonth(new Date(),Inscription.getFormatDate("2020-10-20")));
        System.out.println(inscriptionBeans(Inscription.YELOW));
    }


    private static String getFirstMonthDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取前月的第一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String format1 = format.format(cale.getTime());
        return format1;
    }

    /**
     * 获取相差月数
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getMonth(Date startDate,Date endDate){
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(startDate);

        aft.setTime(endDate);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);

    }

    private static double inscriptionBeans(Inscription inscription){
        Date startDate = inscription.getDate();
        Date current=new Date();
//        Date current=Inscription.getFormatDate("2020-9-20");
        int month = getMonth(startDate, current);
        long value = inscription.getValue();
        return value*Math.pow((1-0.0038461235),month);
    }

}

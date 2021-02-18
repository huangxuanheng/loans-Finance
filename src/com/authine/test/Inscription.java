package com.authine.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Harry
 * @date 2020/8/9
 * @des 描述：铭文
 */
public enum Inscription {
    LOVE(1,1500,"爱上铭文"),
//    RED(2,1265,"红色铭文"),
    RED(2,1270,"红色铭文"),
    ORANGE(3,12750,"橙色铭文"),
//    YELOW(4,64250,"黄色铭文"),
    YELOW(4,64500,"黄色铭文"),
    GREED(5,129500,"绿色铭文"),
//    GREED(5,130000,"绿色铭文"),
    BLUE(6,652500,"绿色铭文"),
    ;
    private int code;
    private long value;   //当前时间内的秘豆初始值
    private String name;
    private Date date;    //当前时间

    public long getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    Inscription(int code, long value, String name) {
        this.code = code;
        this.name = name;
        this.value=value;
        this.date=getFormatDate("2020-08-01");
    }

    public static Date getFormatDate(String date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

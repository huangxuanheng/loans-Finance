package com.authine.test;

import com.authine.bank.DateUtils;

import java.text.ParseException;
import java.util.Random;

/**
 * @author Harry
 * @date 2020/9/12
 * @des 描述：
 */
public class OneMillionPlan {
    public static void main(String[] args) throws ParseException {
        float target=1000000;   //目标金额
        int cycle= 3;   //周期数
        int n=1000;   //固定周期
        float rate=0.05f;   //利率
        float total=10000; //本金
        getTimes(target, cycle, n, rate, total);
//        getRate(target, cycle, total);

    }

    private static void getRate(float target, int cycle, float total) throws ParseException {
        float rate;//计算从当前时间到今年过年，按照固定的操作周期，要达到目标金额，满足利率是多少？
        String endDate="2021-02-11";
        String startDate="2020-09-21";
        int days = DateUtils.daysBetween(startDate, endDate);
        int times=days/cycle;
        double log = Math.log(target / total);
        rate= (float) (Math.exp(log/times)-1);
        System.out.println(rate*100);
    }

    /**
     * 获取操作次数
     * @param target 目标金额
     * @param cycle 周期数
     * @param n 循环数
     * @param rate 利率
     * @param total 本金
     */
    private static void getTimes(float target, int cycle, int n, float rate, float total) {
        Random random = new Random();
        int day=0;
        for (int x=1;x<n;x++){
//            int i = random.nextInt(100);
//            rate= (i<=25?(random.nextInt(10)+3):(i<=75?(random.nextInt(10)+10):(random.nextInt(40)+20)))/100.0f;
            int y = random.nextInt(10);
            cycle=y<=7?(random.nextInt(3)+2):(random.nextInt(5)+5);
            float interest=total*rate;
            total+=interest;
            day+=cycle;
            System.out.println("本次操作涨幅："+rate+"，本次收益："+interest+"，累计复利："+total+"，周期时长："+cycle+",累计复利天数："+day);
            if(total>target){
                System.out.println("100万需要复利：操作"+x+"次一共复利"+day+"天,包括周末总共："+(day+day/5*2)+"天,"+(day+(day/5*2))/30.0+"月,总收益："+total);
                break;
            }
        }
    }
}

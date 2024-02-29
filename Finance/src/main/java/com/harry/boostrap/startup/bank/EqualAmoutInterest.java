package com.harry.boostrap.startup.bank;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Lists;

/**
 * @author Harry
 * @date 2020/8/22
 * @des 描述：
 */
public class EqualAmoutInterest {
    public static void main(String[] args) {


//        System.out.println("总利息："+allInterest(plans));
        long samePrincipal=300000;
        String startDate="2018-12-16";
//        String endDate="2020-09-27";
        String endDate=null;
        int numOfPeriods=36;

        List<RepaymentPlan> build = getAllPlans("建设银行", LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, 1200000, 0.05733f, InterestType.Y, 360, startDate,endDate);
        List<RepaymentPlan> build2 = getAllPlans("建设银行2", LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, 899000, 0.05733f, InterestType.Y, 360, startDate,endDate);
        List<RepaymentPlan> gf = getAllPlans("广州银行", LoanType.EQUAL_AMOUT_INTEREST, 200000, 0.0075f, InterestType.M, 60, startDate,endDate,0.05f);
        List<RepaymentPlan> wld = getAllPlans("微粒贷", LoanType.EQUAL_AMOUT_PRINCIPAL, 70000, 0.00035f, InterestType.D, 20, "2021-02-18", endDate);
//109000
        List<RepaymentPlan> sdd = getAllPlans("闪电贷", LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, 80000, 0.108f, InterestType.Y, 12, "2021-02-07", endDate);
        List<RepaymentPlan> sdd2 = getAllPlans("闪电贷", LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, 55336, 0.108f, InterestType.Y, 12, "2020-09-20", endDate);



        List<RepaymentPlan> jb = getAllPlans("借呗", LoanType.EQUAL_AMOUT_PRINCIPAL, 70000, 0.0004f, InterestType.D, 10, "2020-12-25", endDate);
//        print(jb);
//        calculationOptimal2(wld,sdd);
//        calculationOptimal2(gf,wld);
//        calculationOptimal2(gf,jb);
//        calculationOptimal(sdd,gf);
//        calculationOptimal(wld,sdd);
//        calculationOptimal(sdd,jb);
//        calculationOptimal2(sdd,jb);
//        calculationOptimal(gf,wld);
//        print(build);
        print(build2);
//        print(sdd2);
//        print(sdd,13);
//        print(sdd2,8);
//        print(gf);
//        print(wld);
    }

    private static void calculationOptimal(List<RepaymentPlan> plans1,List<RepaymentPlan> plans2){
        float interest1 = allInterest(plans1);
        float interest2 = allInterest(plans2);
        System.out.println("贷款单位"+plans1.get(0).getLoanInfo().getLoanUnit()+"，还款期数："+plans1.size()+",总利息："+interest1);
        System.out.println("贷款单位"+plans2.get(0).getLoanInfo().getLoanUnit()+"，还款期数："+plans2.size()+",总利息："+interest2);
        int n=plans1.size()>plans2.size()?plans2.size():plans1.size();
        for (int x=0;x<n;x++){
            float plans1TotalInterest=0;
            for (int y=0;y<plans1.size();y++){
                if(y>x){
                    break;
                }
                RepaymentPlan plan = plans1.get(y);
                plans1TotalInterest+=plan.getInterest();
            }

            float plans2TotalInterest=0;
            for (int y=0;y<plans2.size();y++){
                if(y>x){
                    break;
                }
                RepaymentPlan plan = plans2.get(y);
                plans2TotalInterest+=plan.getInterest();
            }

            if(interest1>interest2){
                if(plans1TotalInterest>=plans2TotalInterest){
                    System.out.println("此时贷款单位："+plans1.get(0).getLoanInfo().getLoanUnit()+" 已还款总利息是："+plans1TotalInterest+",贷款单位："+plans2.get(0).getLoanInfo().getLoanUnit()+" 还款利息是："+plans2TotalInterest+",即还款期数大于："+(x+1)+"期时，贷款方案："+plans2.get(0).getLoanInfo().getLoanUnit()+",要比方案："+plans1.get(0).getLoanInfo().getLoanUnit()+"好");
                    break;
                }
            }else {
                if(plans1TotalInterest<=plans2TotalInterest){
                    System.out.println("此时贷款单位："+plans1.get(0).getLoanInfo().getLoanUnit()+" 已还款总利息是："+plans1TotalInterest+",贷款单位："+plans2.get(0).getLoanInfo().getLoanUnit()+" 还款利息是："+plans2TotalInterest+",即还款期数大于："+(x+1)+"期时，贷款方案："+plans1.get(0).getLoanInfo().getLoanUnit()+",要比方案："+plans2.get(0).getLoanInfo().getLoanUnit()+"好");
                    break;
                }
            }
        }
        System.out.println();
    }


    private static void calculationOptimal2(List<RepaymentPlan> plans1,List<RepaymentPlan> plans2){
        float interest1 = allInterest(plans1);
        float interest2 = allInterest(plans2);
        int n=plans1.size()>plans2.size()?plans2.size():plans1.size();
        for (int x=0;x<n;x++){
            float plans1TotalInterest=0;
            for (int y=0;y<plans1.size();y++){
                if(y>x){
                    RepaymentPlan plan = plans1.get(y);
                    plans1TotalInterest=plan.getInterest();
                    break;
                }
            }

            float plans2TotalInterest=0;
            for (int y=0;y<plans2.size();y++){
                if(y>x){
                    RepaymentPlan plan = plans2.get(y);
                    plans2TotalInterest=plan.getInterest();
                    break;
                }
            }
            RepaymentPlan plan1 = plans1.get(x+1);
            RepaymentPlan plan2 = plans2.get(x+1);
            if(interest1>interest2){
                if(plans1TotalInterest>=plans2TotalInterest){
                    System.out.println("即还款期数大于："+(x+1)+"期时，贷款方案："+plan2.getLoanInfo().getLoanUnit()+",要比方案："+plan1.getLoanInfo().getLoanUnit()+"好"+"此时贷款方案："+plan1.getLoanInfo().getLoanUnit()+" 本期利息是："+plans1TotalInterest+",贷款方案："+plan2.getLoanInfo().getLoanUnit()+" 本期利息是："+plans2TotalInterest);
                    System.out.println("贷款方案："+plan1.getLoanInfo().getLoanUnit()+",剩余本金："+plan1.getRemainingMoney()+",贷款方案："+plan2.getLoanInfo().getLoanUnit()+",剩余本金："+plan2.getRemainingMoney());
                    break;
                }
            }else {
                if(plans1TotalInterest<=plans2TotalInterest){
                    System.out.println("即还款期数大于："+(x+1)+"期时，贷款方案："+plan1.getLoanInfo().getLoanUnit()+",要比方案："+plan2.getLoanInfo().getLoanUnit()+"好"+"此时贷款方案："+plan2.getLoanInfo().getLoanUnit()+" 本期利息是："+plans2TotalInterest+",贷款方案："+plan1.getLoanInfo().getLoanUnit()+" 本期利息是："+plans1TotalInterest);
                    System.out.println("贷款方案："+plan1.getLoanInfo().getLoanUnit()+",剩余本金："+plan1.getRemainingMoney()+",贷款方案："+plan2.getLoanInfo().getLoanUnit()+",剩余本金："+plan2.getRemainingMoney());
                    break;
                }
            }
        }
        System.out.println();
    }

    public static List<RepaymentPlan>getAllPlans(String loanUnit, LoanType loanType, float principal, float interest, InterestType interestType, int numOfPeriods, String startDate, String endDate){
        return getAllPlans(loanUnit,loanType,principal,interest,interestType,numOfPeriods,startDate,endDate,0);
    }

    /**
     * 获取还款计划
     * @param loanUnit 贷款单位
     * @param loanType 贷款类型
     * @param principal 本金
     * @param interest 利率
     * @param interestType 利率类型
     * @param numOfPeriods 分期数
     * @param startDate 账单日
     * @param endDate
     * @return
     */
    public static List<RepaymentPlan>getAllPlans(String loanUnit, LoanType loanType, float principal,
                                                 float interest, InterestType interestType, int numOfPeriods,
                                                 String startDate, String endDate,float damagesRate){
        LoanInfo loanInfo=new LoanInfo();
        loanInfo.setLoanUnit(loanUnit);
        loanInfo.setInterest(interest);
        loanInfo.setInterestType(interestType);
        loanInfo.setPrincipal(principal);
        loanInfo.setLoanType(loanType);
        loanInfo.setNumberOfPeriods(numOfPeriods);

        RepaymentPlan repaymentPlan=new RepaymentPlan();
        repaymentPlan.setStartDate(DateUtils.formatDate(startDate));
        repaymentPlan.setLoanInfo(loanInfo);
        if(endDate!=null){
            repaymentPlan.setEndDate(DateUtils.formatDate(endDate));
        }
        repaymentPlan.setDamagesRate(damagesRate);

        List<RepaymentPlan>plans=new ArrayList<>();
        if(principal==0){
            return Lists.newArrayList(repaymentPlan);
        }
        while (repaymentPlan!=null){
            plans.add(repaymentPlan);
            repaymentPlan=repaymentPlan.getNextPlan();
        }
        return plans;
    }


    /**
     * 获取贷款总利息
     * @param plans
     * @return
     */
    private static float allInterest(List<RepaymentPlan>plans){
        float totalInterest=0;
        for (RepaymentPlan plan:plans){
            totalInterest+=plan.getInterest();
        }
        return totalInterest;
    }

    private static void print(List<RepaymentPlan> plans) {
        float total=0;
        float all=0;
        for (RepaymentPlan plan:plans){
            System.out.println(plan.getLoanDes());
            total+=plan.getInterest();
            all+=plan.getTotal();
        }
        System.out.println("贷款单位："+plans.get(0).getLoanInfo().getLoanUnit()+":"+plans.get(0).getLoanInfo().getLoanType().getMsg()+"，总利息是："+total+",总还款："+all);
    }

    private static void print(List<RepaymentPlan> plans,int skip) {
        float total=0;
        float all=0;
        for (int x=0;x<plans.size();x++){
            if(x<skip){
                continue;
            }
            RepaymentPlan plan=plans.get(x);
            System.out.println(plan.getLoanDes());
            total+=plan.getInterest();
            all+=plan.getTotal();
        }
        System.out.println("贷款单位："+plans.get(0).getLoanInfo().getLoanUnit()+":"+plans.get(0).getLoanInfo().getLoanType().getMsg()+"，总利息是："+total+",总还款："+all);
    }
}

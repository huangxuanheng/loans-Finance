package com.harry.boostrap.startup.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;

/**
 * @Description: 房地产投资计划书
 * @Author: harry
 * @CreateTime: 2024/2/20
 */
public class InvestRealEstatePlanTest {

    @Test
    public void test(){
        //初始投入金额
        float input=118180;
        //月租金收入
        long rent=1200;
        //贷款总额
        long principal=30000;
        //贷款年利率
        float interest=0.06f;
        //贷款期数
        int numOfPeriods=360;
        String startDate=DateUtils.formatDateToStr(new Date());
        List<RepaymentPlan> planList = EqualAmoutInterest.getAllPlans("建设银行",
            LoanType.INTEREST_FIRST, principal, interest, InterestType.Y, numOfPeriods,
            startDate, null);
        calculateInvestmentReturnRate(input,rent,planList.get(0));
        print(planList);
    }


    @Test
    public void test2(){
        float fullPayment=290000;
        //初始投入金额
        float input=78000;
        //月租金收入
        float rent=1200;
        //贷款年利率
//        float interest=0.1f;
        float interest=0.035f;
        //贷款期数
        int numOfPeriods=360;
        String startDate=DateUtils.formatDateToStr(new Date());



        //贷款总额
        float principal= 0;

        final int min=10000;

        List<RealEstatePlan>realEstatePlans=new ArrayList<>();
        for (float x=input;x<fullPayment;x+=min){
            if(input>fullPayment){
                break;
            }
            input=x;
            principal= fullPayment-input;
            if(principal<min){
                input=fullPayment;
                principal=0;
            }
            List<RepaymentPlan> planList = EqualAmoutInterest.getAllPlans("建设银行",
                LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, principal, interest, InterestType.Y, numOfPeriods,
                startDate, null);
            //净现值
            float npv= rent-planList.get(0).getTotal();
            //投资回报率
            float rate=input==0?(npv>0?1:-1):npv*12/input;
            float totalInterest=0;
            float totalRepayment=0;
            for (RepaymentPlan plan:planList){
                totalInterest+=plan.getInterest();
                totalRepayment+=plan.getTotal();
            }
            RealEstatePlan realEstatePlan=new RealEstatePlan();
            realEstatePlan.setNpv(npv);
            realEstatePlan.setInput(input);
            realEstatePlan.setRate(rate);
            realEstatePlan.setRent(rent);
            realEstatePlan.setPrincipal(principal);
            realEstatePlan.setMonthlySupply(planList.get(0).getTotal());
            realEstatePlan.setLoadAnnualizedRate(interest);
            realEstatePlan.setTotalInterest(totalInterest);
            realEstatePlan.setTotalRepayment(totalRepayment);
            realEstatePlans.add(realEstatePlan);
        }
        printPlan(realEstatePlans);
    }

    private static void printPlan(List<RealEstatePlan>realEstatePlans){
        //计算回报率最高的
        realEstatePlans.sort((o1,o2)->{

            if(o1.getRate()>o2.getRate()){
                return -1;
            }else if(o1.getRate()<o2.getRate()){
                if(o2.getRate()<0){
                    return -1;
                }
                return 1;
            }else {
                return 0;
            }
        });
        for (RealEstatePlan realEstatePlan:realEstatePlans){
            System.out.println("初始投入资金："+realEstatePlan.getInput()+" 元");
            System.out.println("贷款总额："+realEstatePlan.getPrincipal()+"元,月供："+realEstatePlan.getMonthlySupply()+"元，贷款年化率："+realEstatePlan.getLoadAnnualizedRate()*100+"%");
            System.out.println("净现值："+realEstatePlan.getNpv()+"元");
            if(realEstatePlan.getInput()==0&&realEstatePlan.getNpv()>0){
                System.out.println("投资回报率：无穷大 0年内回本");
            }else if(realEstatePlan.getRate()<0){
                System.out.println("投资回报率：亏本生意");
            }else {
                System.out.println("投资回报率："+realEstatePlan.getRate()*100+"%, "+Math.round(1/realEstatePlan.getRate())+" 年内回本");
            }

            System.out.println("总利息是："+realEstatePlan.getTotalInterest()+",总还款："+realEstatePlan.getTotalRepayment());
            System.out.println("----------------------------------------------------------------------");
        }
    }
    private void calculateInvestmentReturnRate(float input, long rent, RepaymentPlan repaymentPlan) {
        long npv= (long) (rent-repaymentPlan.getTotal());
        float rate=input==0?1:npv*12/input;
        System.out.println("初始投入资金："+input+" 元");
        System.out.println("贷款总额："+repaymentPlan.getLoanInfo().getPrincipal()+"元,月供："+repaymentPlan.getTotal()+"元,月利息："+repaymentPlan.getInterest());
        System.out.println("净现值："+npv+"元");
        System.out.println("投资回报率："+rate*100+"%, "+Math.round(1/rate)+" 年内回本");
    }

    private static void print(List<RepaymentPlan> plans) {
        float total=0;
        float all=0;
        for (RepaymentPlan plan:plans){
            total+=plan.getInterest();
            all+=plan.getTotal();
        }
        System.out.println("总利息是："+total+",总还款："+all);
    }
}

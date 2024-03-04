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
        float input=100000;
        //月租金收入
        long rent=2100;
        //贷款总额
        long principal=100000;
        //贷款年利率
        float interest=0.052f;
        //贷款期数
        int numOfPeriods=120;
        String startDate=DateUtils.formatDateToStr(new Date());
        List<RepaymentPlan> planList = EqualAmoutInterest.getAllPlans("建设银行",
            LoanType.EQUAL_AMOUT_PRINCIPAL_AND_INTEREST, principal, interest, InterestType.Y, numOfPeriods,
            startDate, null);
        calculateInvestmentReturnRate(input,rent,planList.get(0));
        print(planList);
    }


    @Test
    public void test2(){
        float fullPayment=152000;
        //月租金收入
        float rent=1500;
        //贷款年利率
        float interest=0.082f;
        //贷款期数
        int numOfPeriods=360;
        String startDate=DateUtils.formatDateToStr(new Date());

        //初始投入金额
        float input=0;

        //贷款总额
        float principal= 0;

        final int min=10000;

        List<RealEstatePlan>realEstatePlans=new ArrayList<>();
        for (int x=min;x<fullPayment;x+=min){
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
            float rate=npv*12/input;
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
                return 1;
            }else {
                return 0;
            }
        });
        for (RealEstatePlan realEstatePlan:realEstatePlans){
            System.out.println("初始投入资金："+realEstatePlan.getInput()+" 元");
            System.out.println("贷款总额："+realEstatePlan.getPrincipal()+"元,月供："+realEstatePlan.getMonthlySupply()+"元，贷款年化率："+realEstatePlan.getLoadAnnualizedRate()*100+"%");
            System.out.println("净现值："+realEstatePlan.getNpv()+"元");
            System.out.println("投资回报率："+realEstatePlan.getRate()*100+"%, "+Math.round(1/realEstatePlan.getRate())+" 年内回本");
            System.out.println("总利息是："+realEstatePlan.getTotalInterest()+",总还款："+realEstatePlan.getTotalRepayment());
            System.out.println("----------------------------------------------------------------------");
        }
    }
    private void calculateInvestmentReturnRate(float input, long rent, RepaymentPlan repaymentPlan) {
        long npv= (long) (rent-repaymentPlan.getTotal());
        float rate=npv*12/input;
        System.out.println("初始投入资金："+input+" 元");
        System.out.println("贷款总额："+repaymentPlan.getLoanInfo().getPrincipal()+"元,月供："+repaymentPlan.getTotal()+"元");
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

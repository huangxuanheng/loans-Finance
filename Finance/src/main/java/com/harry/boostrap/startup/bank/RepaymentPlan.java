package com.harry.boostrap.startup.bank;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Harry
 * @date 2020/8/22
 * @des 描述：还款计划
 */
public class RepaymentPlan {
    private LoanInfo loanInfo;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 上一期还款计划
     */
    private RepaymentPlan previousPlan;

    private float remainingMoney;

    private int days;

    private  float principal;

    private float total=0;
    //利息
    private float interest=0;

    //违约金
    private float damages;
    //违约金比率
    private float damagesRate;

    public float getDamages() {
        if(damages==0){
            damages=remainingMoney*damagesRate;
        }
        return damages;
    }

    public float getDamagesRate() {
        return damagesRate;
    }

    public void setDamagesRate(float damagesRate) {
        this.damagesRate = damagesRate;
    }

    public RepaymentPlan getPreviousPlan() {
        return previousPlan;
    }

    public void setPreviousPlan(RepaymentPlan previousPlan) {
        this.previousPlan = previousPlan;
    }

    public LoanInfo getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(LoanInfo loanInfo) {
        this.loanInfo = loanInfo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束时间
     */
    public Date getEndDate() {
        return endDate==null?DateUtils.getNextMonth(startDate):endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 本期结束日期距开始日期天数
     */
    public int getDays() {
        try {
            if(days==0){
                days=DateUtils.daysBetween(startDate,getEndDate());
            }
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 本期还款本金
     */
    public float getPrincipal() {
        if(principal==0){
            LoanType loanType = loanInfo.getLoanType();

            switch (loanType){
                case EQUAL_AMOUT_PRINCIPAL_AND_INTEREST:
                    principal=getTotal()-getInterest();
                    break;
                case INTEREST_FIRST:
                    if(getRemainingMonth()==1){
                        principal=loanInfo.getPrincipal();
                    }else {
                        principal=0;
                    }

                    break;
                default:
                    principal=loanInfo.getPrincipal()/loanInfo.getNumberOfPeriods();
                    break;
            }
        }
        return principal;
    }

    /**
     * 本期还款利息
     */
    public float getInterest() {
        if(interest==0){
            LoanType loanType = loanInfo.getLoanType();

            switch (loanType){
                case INTEREST_FIRST:
                    //先息后本
                    interest=loanInfo.getPrincipal()*getDays()*loanInfo.getDayInterest();
                    break;
                case EQUAL_AMOUT_INTEREST:
                    //等额等息
                    //计算公式：初始本金*月利率
                    interest=loanInfo.getPrincipal()*loanInfo.getMonthInterest();
                    break;
                case EQUAL_AMOUT_PRINCIPAL:
                    //等额本金  如微粒贷
                    //计算公式：剩余还款本金*本期天数*日利息
                    interest=getRemainingMoney()*getDays()*loanInfo.getDayInterest();
                    break;
                case EQUAL_AMOUT_PRINCIPAL_AND_INTEREST:
                    //等额本息
                    //公式：上一期剩余本金*月利率
                    interest=(previousPlan==null?loanInfo.getPrincipal():previousPlan.getRemainingMoney())*loanInfo.getMonthInterest();
                    break;
            }
        }

        return interest;
    }


    /**
     * 获取本期还款总额
     * @return
     */
    public float getTotal() {
        if(total==0){
            LoanType loanType = loanInfo.getLoanType();

            switch (loanType){
                case EQUAL_AMOUT_PRINCIPAL_AND_INTEREST:
                    //等额本息
                    //公式：等额本息：〔贷款本金×月利率×（1＋月利率）＾还款月数〕÷〔（1＋月利率）＾还款月数－1〕
                    total= (float) ((loanInfo.getPrincipal()*loanInfo.getMonthInterest()*Math.pow((1+loanInfo.getMonthInterest()),loanInfo.getNumberOfPeriods()))/(Math.pow((1+loanInfo.getMonthInterest()),loanInfo.getNumberOfPeriods())-1));
                    break;
                default:
                    total=getPrincipal()+getInterest();
                    break;
            }
        }

        return total;
    }


    /**
     * 本期还款之后剩余还款本金
     * @return
     */
    public float getRemainingMoney() {
        if(remainingMoney==0){
            LoanType loanType = loanInfo.getLoanType();

            switch (loanType){
                case EQUAL_AMOUT_PRINCIPAL_AND_INTEREST:
                    //等额本息
                    remainingMoney= (previousPlan==null?loanInfo.getPrincipal():previousPlan.getRemainingMoney())-getPrincipal();
                    break;
                default:
                    remainingMoney= previousPlan==null?loanInfo.getPrincipal():(previousPlan.getRemainingMoney())-getPrincipal();
                    break;
            }
        }
        return remainingMoney;
    }

    /**
     * 剩余还款月数
     * @return
     */
    public int getRemainingMonth(){
        return previousPlan==null?loanInfo.getNumberOfPeriods():previousPlan.getRemainingMonth()-1;
    }

    public RepaymentPlan getNextPlan(){
        if(getRemainingMonth()<=1){
            return null;
        }
        RepaymentPlan plan=new RepaymentPlan();
        plan.setLoanInfo(loanInfo);
        plan.setStartDate(getEndDate());
        plan.setPreviousPlan(this);
        plan.setDamagesRate(damagesRate);
        return plan;
    }

    public String getLoanDes(){
        return loanInfo.getLoanUnit()+":"+loanInfo.getLoanType().getMsg()+",当前剩余还款期数："+getRemainingMonth()+",本期还款本金："+getPrincipal()+",本期利息："+getInterest()+
                ",本期需总还款："+getTotal()+"，剩余还款本金："+getRemainingMoney()+",本期账单日："+DateUtils.formatDateToStr(startDate)
                +",本期还款日："+DateUtils.formatDateToStr(getEndDate())+","+getLoanInfo().getInterestDes()
                +"，提前还款违约金："+getDamages();
    }
}

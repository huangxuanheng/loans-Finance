package com.harry.boostrap.startup.bank;

/**
 * @author Harry
 * @date 2020/8/22
 * @des 描述：贷款信息
 */
public class LoanInfo {
    /**
     * 本金
     */
    private float principal;

    /**
     * 分期总数
     */
    private int NumberOfPeriods;

    /**
     * 利率
     */
    private float interest;

    /**
     * 利率类型
     */
    private InterestType interestType;

    /**
     * 贷款类型
     */
    private LoanType loanType;

    /**
     * 贷款单位
     */
    private String loanUnit;
    //日利率
    private float dayInterest;
    //月利率
    private float monthInterest;
    //年利率
    private float yearInterest;

    public String getLoanUnit() {
        return loanUnit;
    }

    public void setLoanUnit(String loanUnit) {
        this.loanUnit = loanUnit;
    }

    public float getPrincipal() {
        return principal;
    }

    public void setPrincipal(float principal) {
        this.principal = principal;
    }

    public int getNumberOfPeriods() {
        return NumberOfPeriods;
    }

    public void setNumberOfPeriods(int numberOfPeriods) {
        NumberOfPeriods = numberOfPeriods;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    /**
     * 日利率
     * @return
     */
    public float getDayInterest(){
        if(dayInterest==0){
            dayInterest=interestType==InterestType.D?interest:(interestType==InterestType.M?interest/30.0f:interest/(365.0f));
        }
        return dayInterest;
    }

    /**
     * 月利率
     * @return
     */
    public float getMonthInterest(){
        if(monthInterest==0){
            monthInterest=interestType==InterestType.D?interest*30:(interestType==InterestType.M?interest:interest/(12.0f));
        }
        return monthInterest;
    }


    /**
     * 年利率
     * @return
     */
    public float getYearInterest(){
        if(yearInterest==0){
            yearInterest=interestType==InterestType.D?interest*30*12:(interestType==InterestType.M?interest*12:interest);
        }
        return yearInterest;
    }

    /**
     * 获取利率说明
     * @return
     */
    public String getInterestDes(){
        return "折算日利率："+getDayInterest()+",月利率："+getMonthInterest()+",年利率："+getYearInterest();
    }
}

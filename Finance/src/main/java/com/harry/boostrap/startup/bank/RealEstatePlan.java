package com.harry.boostrap.startup.bank;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: harry
 * @CreateTime: 2024/2/28
 */
@Data
public class RealEstatePlan {
    //初始投入金额
    private float input;
    //贷款总额
    private float principal;
    //月租金
    private float rent;

    //净现值
    private float npv;
    //月供
    private float monthlySupply;
    /**
     * 贷款年化率
     */
    private float loadAnnualizedRate;
    //投资回报率
    private float rate;
    //总利息
    private float totalInterest;
    //总还款金额
    private float totalRepayment;
}

package com.harry.boostrap.startup.analyze.enterprise.interest;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：利润表
 */
@Data
public class Interest extends BaseEntity {
    /**
     * 营业总收入
     */
    private List<Double>total_revenue;
    /**
     * 其中：营业收入
     */
    private List<Double>revenue;
    /**
     * 营业总成本
     */
    private List<Double>operating_costs;
    /**
     * 其中：营业成本
     */
    private List<Double>operating_cost;
    /**
     * 营业税金及附加
     */
    private List<Double>operating_taxes_and_surcharge;

    /**
     * 销售费用
     */
    private List<Double>sales_fee;
    /**
     * 管理费用
     */
    private List<Double>manage_fee;
    /**
     * 研发费用
     */
    private List<Double>rad_cost;
    /**
     * 财务费用
     */
    private List<Double>financing_expenses;
    /**
     * 其中：利息费用
     */
    private List<Double>finance_cost_interest_fee;
    /**
     * 利息收入
     */
    private List<Double>finance_cost_interest_income;
    /**
     * 资产减值损失
     */
    private List<Double>asset_impairment_loss;
    /**
     * 信用减值损失
     */
    private List<Double>credit_impairment_loss;
    /**
     * 加：公允价值变动收益
     */
    private List<Double>income_from_chg_in_fv;
    /**
     * 投资收益
     */
    private List<Double>invest_income;
    /**
     * 其中：对联营企业和合营企业的投资收益
     */
    private List<Double>invest_incomes_from_rr;
    /**
     * 资产处置收益
     */
    private List<Double>asset_disposal_income;
    /**
     * 其他收益
     */
    private List<Double>other_income;
    /**
     * 营业利润
     */
    private List<Double>op;
    /**
     * 加：营业外收入
     */
    private List<Double>non_operating_income;
    /**
     * 减：营业外支出
     */
    private List<Double>non_operating_payout;
    /**
     * 利润总额
     */
    private List<Double>profit_total_amt;
    /**
     * 减：所得税费用
     */
    private List<Double>income_tax_expenses;



    /**
     * 净利润
     */
    private List<Double>net_profit;
    /**
     * （一）持续经营净利润
     */
    private List<Double>continous_operating_np;


    /**
     * 归属于母公司所有者的净利润
     */
    private List<Double>net_profit_atsopc;
    /**
     * 少数股东损益
     */
    private List<Double>minority_gal;
    /**
     * 扣除非经常性损益后的净利润
     */
    private List<Double>net_profit_after_nrgal_atsolc;
    /**
     * 其他综合收益
     */
    private List<Double>othr_compre_income_atoopc;
    /**
     * 归属母公司所有者的其他综合收益
     */
    private List<Double>othr_compre_income;
    /**
     * 综合收益总额
     */
    private List<Double>total_compre_income;
    /**
     * 归属于母公司股东的综合收益总额
     */
    private List<Double>total_compre_income_atsopc;
    /**
     * 归属于少数股东的综合收益总额
     */
    private List<Double>total_compre_income_atms;


}

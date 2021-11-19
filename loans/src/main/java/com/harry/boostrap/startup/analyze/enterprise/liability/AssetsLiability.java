package com.harry.boostrap.startup.analyze.enterprise.liability;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：资产负债对象
 */
@Data
public class AssetsLiability extends BaseEntity {
    /**
     * 应收账款
     */
    private List<Double> account_receivable;
    /**
     * 应付账款
     */
    private List<Double> accounts_payable;
    /**
     * 负债率：（今年总资产-去年总资产）/去年总资产
     */
    private List<Double> asset_liab_ratio;
    /**
     * 应付票据
     */
    private List<Double> bill_payable;
    /**
     * 应收票据
     */
    private List<Double> bills_receivable;
    /**
     * 应付债券
     */
    private List<Double> bond_payable;
    /**
     * 在建工程
     */
    private List<Double> construction_in_process_sum;
    /**
     * 其他非流动性金融资产
     */
    private List<Double> other_illiquid_fnncl_assets;
    /**
     * 货币资金
     */
    private List<Double> currency_funds;
    /**
     * 固定资产总和
     */
    private List<Double> fixed_asset_sum;
    /**
     * 商誉
     */
    private List<Double> goodwill;
    /**
     * 存货，库存
     */
    private List<Double> inventory;
    /**
     * 投资房地产
     */
    private List<Double> invest_property;
    /**
     * 长期股权投资
     */
    private List<Double> lt_equity_invest;
    /**
     * 长期贷款
     */
    private List<Double> lt_loan;
    /**
     * 长期应付款
     */
    private List<Double> lt_payable;
    /**
     * 一年内到期的非流动负债
     */
    private List<Double> noncurrent_liab_due_in1y;
    /**
     * 其他权益工具投资
     */
    private List<Double> other_eq_ins_invest;
    /**
     * 预付款
     */
    private List<Double> pre_payment;
    /**
     * 预收款
     */
    private List<Double> pre_receivable;
    /**
     * 短期贷款
     */
    private List<Double> st_loan;
    /**
     * 总资产
     */
    private List<Double> total_assets;
    /**
     * 总负债
     */
    private List<Double> total_liab;
    /**
     * 负债总额和所有者权益
     */
    private List<Double> total_liab_and_holders_equity;
    /**
     * 交易性金融资产
     */
    private List<Double> tradable_fnncl_assets;
    /**
     * 归属于母公司股东权益合计
     */
    private List<Double> total_quity_atsopc;

}

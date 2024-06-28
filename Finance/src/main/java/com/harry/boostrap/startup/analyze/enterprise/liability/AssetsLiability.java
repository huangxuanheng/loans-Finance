package com.harry.boostrap.startup.analyze.enterprise.liability;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;
import org.assertj.core.util.Lists;

/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：资产负债对象
 */
@Data
public class AssetsLiability extends BaseEntity {
    /**
     * 货币资金
     */
    private List<Double> currency_funds;
    /**
     * 交易性金融资产
     */
    private List<Double> tradable_fnncl_assets;
    /**
     * 应收票据及应收账款
     */
    private List<Double> ar_and_br;
    /**
     * 其中：应收票据
     */
    private List<Double> bills_receivable;
    /**
     * 应收账款
     */
    private List<Double> account_receivable;
    /**
     * 预付款项
     */
    private List<Double> pre_payment;
    /**
     * 应收利息
     */
    private List<Double> interest_receivable;
    /**
     * 应收股利
     */
    private List<Double> dividend_receivable;
    /**
     * 其他应收款
     */
    private List<Double> othr_receivables;
    /**
     * 存货，库存
     */
    private List<Double> inventory;
    /**
     * 合同资产
     */
    private List<Double> contractual_assets;
    /**
     * 一年内到期的非流动资产
     */
    private List<Double> nca_due_within_one_year;
    /**
     * 其他流动资产
     */
    private List<Double> othr_current_assets;
    /**
     * 流动资产合计
     */
    private List<Double> total_current_assets;
    /**
     * 可供出售金融资产
     */
    private List<Double> salable_financial_assets;
    /**
     * 长期股权投资
     */
    private List<Double> lt_equity_invest;
    /**
     * 其他非流动金融资产
     */
    private List<Double> other_illiquid_fnncl_assets;
    /**
     * 固定资产合计
     */
    private List<Double> fixed_asset_sum;
    /**
     * 投资性房地产
     */
    private List<Double> invest_property;
    /**
     * 其中：固定资产
     */
    private List<Double> fixed_asset;
    /**
     * 在建工程合计
     */
    private List<Double> construction_in_process_sum;
    /**
     * 其中：在建工程
     */
    private List<Double> construction_in_process;
    /**
     * 工程物资
     */
    private List<Double> project_goods_and_material;
    /**
     * 生产性生物资产
     */
    private List<Double> productive_biological_assets;
    /**
     *无形资产
     */
    private List<Double> intangible_assets;
    /**
     * 开发支出
     */
    private List<Double> dev_expenditure;
    /**
     * 长期待摊费用
     */
    private List<Double> lt_deferred_expense;
    /**
     * 递延所得税资产
     */
    private List<Double> dt_assets;
    /**
     * 其他非流动资产
     */
    private List<Double> othr_noncurrent_assets;
    /**
     * 非流动资产合计
     */
    private List<Double> total_noncurrent_assets;
    /**
     * 资产合计
     */
    private List<Double> total_assets;

    /**
     * 短期借款
     */
    private List<Double> st_loan;
    /**
     * 应付票据及应付账款
     */
    private List<Double> bp_and_ap;
    /**
     * 应付账款
     */
    private List<Double> accounts_payable;
    /**
     * 预收款项
     */
    private List<Double> pre_receivable;
    /**
     * 合同负债
     */
    private List<Double> contract_liabilities;
    /**
     * 应付职工薪酬
     */
    private List<Double> payroll_payable;
    /**
     * 应交税费
     */
    private List<Double> tax_payable;
    /**
     * 应付股利
     */
    private List<Double> dividend_payable;

    /**
     * 其他应付款
     */
    private List<Double> othr_payables;
    /**
     * 一年内到期的非流动负债
     */
    private List<Double> noncurrent_liab_due_in1y;
    /**
     * 其他流动负债
     */
    private List<Double> othr_current_liab;
    /**
     * 流动负债合计
     */
    private List<Double> total_current_liab;
    /**
     * 长期借款
     */
    private List<Double> lt_loan;
    /**
     * 应付债券
     */
    private List<Double> bond_payable;
    /**
     * 长期应付款合计
     */
    private List<Double> lt_payable_sum;
    /**
     * 长期应付款
     */
    private List<Double> lt_payable;
    /**
     * 递延所得税负债
     */
    private List<Double> dt_liab;
    /**
     * 递延收益-非流动负债
     */
    private List<Double> noncurrent_liab_di;
    /**
     * 非流动负债合计
     */
    private List<Double> total_noncurrent_liab;
    /**
     * 负债合计
     */
    private List<Double> total_liab;
    /**
     * 实收资本(或股本)
     */
    private List<Double> shares;
    /**
     * 其他权益工具
     */
    private List<Double> othr_equity_instruments;
    /**
     * 永续债
     */
    private List<Double> perpetual_bond;
    /**
     * 资本公积
     */
    private List<Double> capital_reserve;
    /**
     * 减：库存股
     */
    private List<Double> treasury_stock;
    /**
     * 其他综合收益
     */
    private List<Double> othr_compre_income;
    /**
     * 盈余公积
     */
    private List<Double> earned_surplus;
    /**
     * 未分配利润
     */
    private List<Double> undstrbtd_profit;
    /**
     * 归属于母公司股东权益合计
     */
    private List<Double> total_quity_atsopc;
    /**
     * 少数股东权益
     */
    private List<Double> minority_equity;
    /**
     * 股东权益合计
     */
    private List<Double> total_holders_equity;

    /**
     * 负债和股东权益总计
     */
    private List<Double> total_liab_and_holders_equity;




    /**
     * 负债率：（今年总资产-去年总资产）/去年总资产
     */
    private List<Double> asset_liab_ratio;
    /**
     * 应付票据
     */
    private List<Double> bill_payable;




    /**
     * 商誉
     */
    private List<Double> goodwill;


    /**
     * 其他权益工具投资
     */
    private List<Double> other_eq_ins_invest;



    public List<Double> getCurrency_funds() {
        return getInitValue(currency_funds);
    }

    private List<Double>getInitValue(List<Double>targetValue){
        return targetValue==null?Lists.newArrayList(0d,0d):targetValue;
    }

    public List<Double> getSt_loan() {
        return getInitValue(st_loan);
    }


    public List<Double> getNoncurrent_liab_due_in1y() {
        return getInitValue(noncurrent_liab_due_in1y);
    }

    public List<Double> getLt_loan() {
        return getInitValue(lt_loan);
    }

    public List<Double> getLt_payable() {
        return getInitValue(lt_payable);
    }

    public List<Double> getBill_payable() {
        return getInitValue(bill_payable);
    }

    public List<Double> getAccounts_payable() {
        return getInitValue(accounts_payable);
    }

    public List<Double> getPre_receivable() {
        return getInitValue(pre_receivable);
    }

    public List<Double> getContract_liabilities() {
        return getInitValue(contract_liabilities);
    }

    public List<Double> getAccount_receivable() {
        return getInitValue(account_receivable);
    }

    public List<Double> getPre_payment() {
        return getInitValue(pre_payment);
    }

    public List<Double> getContractual_assets() {
        return getInitValue(contractual_assets);
    }

    public List<Double> getBills_receivable() {
        return getInitValue(bills_receivable);
    }

    public List<Double> getFixed_asset() {
        return getInitValue(fixed_asset);
    }

    public List<Double> getFixed_asset_sum() {
        return getInitValue(fixed_asset_sum);
    }

    public List<Double> getConstruction_in_process() {
        return getInitValue(construction_in_process);
    }

    public List<Double> getProject_goods_and_material() {
        return getInitValue(project_goods_and_material);
    }

    public List<Double> getOther_eq_ins_invest() {
        return getInitValue(other_eq_ins_invest);
    }

    public List<Double> getOther_illiquid_fnncl_assets() {
        return getInitValue(other_illiquid_fnncl_assets);
    }

    public List<Double> getLt_equity_invest() {
        return getInitValue(lt_equity_invest);
    }

    public List<Double> getInvest_property() {
        return getInitValue(invest_property);
    }

    public List<Double> getInventory() {
        return getInitValue(inventory);
    }

    public List<Double> getSalable_financial_assets() {
        return getInitValue(salable_financial_assets);
    }

    public List<Double> getOthr_noncurrent_assets() {
        return getInitValue(othr_noncurrent_assets);
    }

    public List<Double> getProductive_biological_assets() {
        return getInitValue(productive_biological_assets);
    }

    public List<Double>getGoodwill(){
        return getInitValue(goodwill);
    }

    public List<Double> getOthr_receivables() {
        return getInitValue(othr_receivables);
    }
}

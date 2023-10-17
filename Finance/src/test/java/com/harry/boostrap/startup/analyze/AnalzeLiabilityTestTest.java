package com.harry.boostrap.startup.analyze;

import com.harry.boostrap.startup.analyze.enterprise.cash.CashContent;
import com.harry.boostrap.startup.analyze.enterprise.cash.CashHandler;
import com.harry.boostrap.startup.analyze.enterprise.interest.Interest;
import com.harry.boostrap.startup.analyze.enterprise.interest.InterestHandler;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import com.harry.boostrap.startup.analyze.enterprise.liability.AssetsLiability;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.harry.boostrap.startup.analyze.utils.DataCheckNullAndAssigmentUtils;
import com.harry.boostrap.startup.analyze.utils.HtmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnalzeLiabilityTestTest {

    @Autowired
    private HtmlUtils htmlUtils;
    @Test
    public void createHtml() throws IOException, URISyntaxException {
        String type = "Q4";
        int count = 6;

        analysisFinance(AnalzeLiability.getSymbol("000895"), AnalzeLiability.getSymbol("000895"),
            AnalzeLiability.getSymbol("000895"), type, count);
    }

    private void analysisFinance(String targetSymbol, String targetSymbol2, String targetSymbol3,
        String type, int count)
        throws IOException, URISyntaxException {
        //资产负债
        AnnualReport<AssetsLiability> targetAssetsLiabilitys = AnalzeLiability.getResponseData(
            targetSymbol, type, count);
        AnnualReport<AssetsLiability> targetAssetsLiabilitys2 = AnalzeLiability.getResponseData(
            targetSymbol2, type, count);
        AnnualReport<AssetsLiability> targetAssetsLiabilitys3 = AnalzeLiability.getResponseData(
            targetSymbol3, type, count);
        AnnualReport<Interest> targetInterests = InterestHandler.getInterest(targetSymbol, type,
            count);
        AnnualReport<Interest> targetInterests2 = InterestHandler.getInterest(targetSymbol2, type,
            count);
        AnnualReport<Interest> targetInterests3 = InterestHandler.getInterest(targetSymbol3, type,
            count);
        AnnualReport<CashContent> targetCashFlows = CashHandler.getCashFlow(targetSymbol, type,
            count);
        AnnualReport<CashContent> targetCashFlows2 = CashHandler.getCashFlow(targetSymbol2, type,
            count);
        AnnualReport<CashContent> targetCashFlows3 = CashHandler.getCashFlow(targetSymbol3, type,
            count);
        Map<String, Object> params = new HashMap<>();
        params.put("target_company_name", targetAssetsLiabilitys.getQuote_name());
        params.put("target2_company_name", targetAssetsLiabilitys2.getQuote_name());
        params.put("target3_company_name", targetAssetsLiabilitys3.getQuote_name());

        for (int x = 0; x < count; x++) {
            //遍历到最后一年时忽略计算
            if (x + 1 == count) {
                break;
            }
            AssetsLiability targetAssetsLiability = targetAssetsLiabilitys.getList().get(x);
            AssetsLiability targetAssetsLiability2 = targetAssetsLiabilitys2.getList().get(x);
            AssetsLiability targetAssetsLiability3 = targetAssetsLiabilitys3.getList().get(x);
            Interest targetInterest = targetInterests.getList().get(x);
            Interest targetInterest2 = targetInterests2.getList().get(x);
            Interest targetInterest3 = targetInterests3.getList().get(x);
            CashContent targetCashFlow = targetCashFlows.getList().get(x);
            CashContent targetCashFlow2 = targetCashFlows2.getList().get(x);
            CashContent targetCashFlow3 = targetCashFlows3.getList().get(x);

            params.put("y" + (x + 1), targetAssetsLiability.getReport_name());
            params.put("target2_y" + (x + 1), targetAssetsLiability2.getReport_name());
            params.put("target3_y" + (x + 1), targetAssetsLiability3.getReport_name());

            AssetsLiability preTargetAssetsLiability = targetAssetsLiabilitys.getList().get(x + 1);
            AssetsLiability preTargetAssetsLiability2 = targetAssetsLiabilitys2.getList()
                .get(x + 1);
            AssetsLiability preTargetAssetsLiability3 = targetAssetsLiabilitys3.getList()
                .get(x + 1);
            Interest preTargetInterest = targetInterests.getList().get(x + 1);
            Interest preTargetInterest2 = targetInterests2.getList().get(x + 1);
            Interest preTargetInterest3 = targetInterests3.getList().get(x + 1);
            CashContent preTargetCashFlow = targetCashFlows.getList().get(x + 1);
            CashContent preTargetCashFlow2 = targetCashFlows2.getList().get(x + 1);
            CashContent preTargetCashFlow3 = targetCashFlows3.getList().get(x + 1);
            //以年为单位组装参数
            params.putAll(createHtmlParams(targetAssetsLiability, targetAssetsLiability2,
                targetAssetsLiability3,
                targetInterest, targetInterest2, targetInterest3, targetCashFlow, targetCashFlow2,
                targetCashFlow3,
                preTargetAssetsLiability, preTargetAssetsLiability2, preTargetAssetsLiability3,
                preTargetInterest, preTargetInterest2, preTargetInterest3, preTargetCashFlow,
                preTargetCashFlow2, preTargetCashFlow3,
                x + 1
            ));
        }

        String htmlFile = htmlUtils.getHtmlFile("finance.html", params);
        log.info(htmlFile);
    }

    private Map<String, ?> createHtmlParams(AssetsLiability targetAssetsLiability,
        AssetsLiability targetAssetsLiability2, AssetsLiability targetAssetsLiability3,
        Interest targetInterest, Interest targetInterest2, Interest targetInterest3,
        CashContent targetCashFlow,
        CashContent targetCashFlow2, CashContent targetCashFlow3,
        AssetsLiability preTargetAssetsLiability, AssetsLiability preTargetAssetsLiability2,
        AssetsLiability preTargetAssetsLiability3, Interest preTargetInterest,
        Interest preTargetInterest2, Interest preTargetInterest3, CashContent preTargetCashFlow,
        CashContent preTargetCashFlow2, CashContent preTargetCashFlow3, int num) {
        Map<String, Object> param = new HashMap<>();
        String target = "y" + num + "_";
        String target2 = "target2_y" + num + "_";
        String target3 = "target3_y" + num + "_";
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target, targetAssetsLiability));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target, targetInterest));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target, targetCashFlow));

        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2, targetAssetsLiability2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2, targetInterest2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2, targetCashFlow2));

        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3, targetAssetsLiability2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3, targetInterest3));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3, targetCashFlow3));

        //总资产增长率
        String total_assets_r = target + "total_assets_r";
        String total_assets_r2 = target2 + "total_assets_r";
        String total_assets_r3 = target3 + "total_assets_r";

        param.put(total_assets_r, (targetAssetsLiability.getTotal_assets().get(0)
            - preTargetAssetsLiability.getTotal_assets().get(0))
            / preTargetAssetsLiability.getTotal_assets().get(0) * 100 + "%");
        param.put(total_assets_r2, (targetAssetsLiability2.getTotal_assets().get(0)
            - preTargetAssetsLiability2.getTotal_assets().get(0))
            / preTargetAssetsLiability2.getTotal_assets().get(0) * 100 + "%");
        param.put(total_assets_r3, (targetAssetsLiability3.getTotal_assets().get(0)
            - preTargetAssetsLiability3.getTotal_assets().get(0))
            / preTargetAssetsLiability3.getTotal_assets().get(0) * 100 + "%");

        //负债率
        String total_liab_r = target + "total_liab_r";
        String total_liab_r2 = target + "total_liab_r";
        String total_liab_r3 = target + "total_liab_r";

        param.put(total_liab_r,
            (targetAssetsLiability.getTotal_liab().get(0)) / targetAssetsLiability.getTotal_assets()
                .get(0) * 100 + "%");
        param.put(total_liab_r2, (targetAssetsLiability2.getTotal_liab().get(0)
            / targetAssetsLiability2.getTotal_assets().get(0) * 100 + "%"));
        param.put(total_liab_r3, (targetAssetsLiability3.getTotal_liab().get(0)
            / targetAssetsLiability3.getTotal_assets().get(0) * 100 + "%"));

        //准货币资金合计
        String total_currency_funds = target + "total_currency_funds";
        String total_currency_funds2 = target2 + "total_currency_funds";
        String total_currency_funds3 = target3 + "total_currency_funds";
        double tcf = targetAssetsLiability.getCurrency_funds().get(0)
            + targetAssetsLiability.getTradable_fnncl_assets().get(0);
        double tcf2 = targetAssetsLiability2.getCurrency_funds().get(0)
            + targetAssetsLiability2.getTradable_fnncl_assets().get(0);
        double tcf3 = targetAssetsLiability3.getCurrency_funds().get(0)
            + targetAssetsLiability3.getTradable_fnncl_assets().get(0);

        param.put(total_currency_funds, getStrValue(tcf));
        param.put(total_currency_funds2, getStrValue(tcf2));
        param.put(total_currency_funds3, getStrValue(tcf3));
        //有息负债合计
        String total_interest_bearing_liabilities = target + "total_interest_bearing_liabilities";
        String total_interest_bearing_liabilities2 = target + "total_interest_bearing_liabilities";
        String total_interest_bearing_liabilities3 = target + "total_interest_bearing_liabilities";

        double tibl = targetAssetsLiability.getSt_loan().get(0)
            + targetAssetsLiability.getNoncurrent_liab_due_in1y().get(0)
            + targetAssetsLiability.getLt_loan().get(0) + targetAssetsLiability.getBond_payable()
            .get(0) + targetAssetsLiability.getLt_payable().get(0);
        double tibl2 = targetAssetsLiability2.getSt_loan().get(0)
            + targetAssetsLiability2.getNoncurrent_liab_due_in1y().get(0)
            + targetAssetsLiability2.getLt_loan().get(0) + targetAssetsLiability2.getBond_payable()
            .get(0) + targetAssetsLiability2.getLt_payable().get(0);
        double tibl3 = targetAssetsLiability3.getSt_loan().get(0)
            + targetAssetsLiability3.getNoncurrent_liab_due_in1y().get(0)
            + targetAssetsLiability3.getLt_loan().get(0) + targetAssetsLiability3.getBond_payable()
            .get(0) + targetAssetsLiability3.getLt_payable().get(0);


        param.put(total_interest_bearing_liabilities, getStrValue(tibl));
        param.put(total_interest_bearing_liabilities2, getStrValue(tibl2));
        param.put(total_interest_bearing_liabilities3, getStrValue(tibl3));
        //准货币资金-有息负债差额
        String total_currency_funds_total_interest_bearing_liabilities_c=target+"currency_funds_interest_bearing_liabilities_c";
        String total_currency_funds_total_interest_bearing_liabilities_c2=target2+"currency_funds_interest_bearing_liabilities_c";
        String total_currency_funds_total_interest_bearing_liabilities_c3=target3+"currency_funds_interest_bearing_liabilities_c";

        double tcftiblc=tcf-tibl;
        double tcftiblc2=tcf-tibl;
        double tcftiblc3=tcf-tibl;
        param.put(total_currency_funds_total_interest_bearing_liabilities_c, getStrValue(tcftiblc));
        param.put(total_currency_funds_total_interest_bearing_liabilities_c2, getStrValue(tcftiblc2));
        param.put(total_currency_funds_total_interest_bearing_liabilities_c3, getStrValue(tcftiblc3));

        //应付预收合计:应付票据+应付账款+预收款项+合同负债
        String total_payable_receivable=target+"total_payable_receivable";
        String total_payable_receivable2=target+"total_payable_receivable";
        String total_payable_receivable3=target+"total_payable_receivable";

        double tpr=targetAssetsLiability.getBill_payable().get(0)+targetAssetsLiability.getAccounts_payable().get(0)+targetAssetsLiability.getPre_receivable().get(0)+targetAssetsLiability.getContract_liabilities().get(0)+targetAssetsLiability.getLt_payable().get(0);
        double tpr2=targetAssetsLiability2.getBill_payable().get(0)+targetAssetsLiability2.getAccounts_payable().get(0)+targetAssetsLiability2.getPre_receivable().get(0)+targetAssetsLiability2.getContract_liabilities().get(0)+targetAssetsLiability2.getLt_payable().get(0);
        double tpr3=targetAssetsLiability3.getBill_payable().get(0)+targetAssetsLiability3.getAccounts_payable().get(0)+targetAssetsLiability3.getPre_receivable().get(0)+targetAssetsLiability3.getContract_liabilities().get(0)+targetAssetsLiability3.getLt_payable().get(0);

        param.put(total_payable_receivable, getStrValue(tpr));
        param.put(total_payable_receivable2, getStrValue(tpr2));
        param.put(total_payable_receivable3, getStrValue(tpr3));

        //应收预付合计:应收票据+应收账款+应收款项融资+预付款项+合同资产
        String total_receivable_payable=target+"total_receivable_payable";
        String total_receivable_payable2=target2+"total_receivable_payable";
        String total_receivable_payable3=target3+"total_receivable_payable";
        double tprc=targetAssetsLiability.getBills_receivable().get(0)+targetAssetsLiability.getAccount_receivable().get(0)+targetAssetsLiability.getPre_payment().get(0)+targetAssetsLiability.getContractual_assets().get(0);
        double tprc2=targetAssetsLiability2.getBills_receivable().get(0)+targetAssetsLiability2.getAccount_receivable().get(0)+targetAssetsLiability2.getPre_payment().get(0)+targetAssetsLiability2.getContractual_assets().get(0);
        double tprc3=targetAssetsLiability3.getBills_receivable().get(0)+targetAssetsLiability3.getAccount_receivable().get(0)+targetAssetsLiability3.getPre_payment().get(0)+targetAssetsLiability3.getContractual_assets().get(0);
        param.put(total_receivable_payable, getStrValue(tprc));
        param.put(total_receivable_payable2, getStrValue(tprc2));
        param.put(total_receivable_payable3, getStrValue(tprc3));

        //应付预收-应收预付
        String total_pay_receivable_c=target+"total_pay_receivable_c";
        String total_pay_receivable_c2=target+"total_pay_receivable_c";
        String total_pay_receivable_c3=target+"total_pay_receivable_c";
        param.put(total_pay_receivable_c, getStrValue(tpr-tprc));
        param.put(total_pay_receivable_c2, getStrValue(tpr2-tprc2));
        param.put(total_pay_receivable_c3, getStrValue(tpr3-tprc3));

        //应收账款+合同资产
        String total_account_contractual=target+"total_account_contractual";
        String total_account_contractual2=target2+"total_account_contractual";
        String total_account_contractual3=target3+"total_account_contractual";

        double tac=targetAssetsLiability.getAccount_receivable().get(0)+targetAssetsLiability.getContractual_assets().get(0);
        double tac2=targetAssetsLiability2.getAccount_receivable().get(0)+targetAssetsLiability2.getContractual_assets().get(0);
        double tac3=targetAssetsLiability3.getAccount_receivable().get(0)+targetAssetsLiability3.getContractual_assets().get(0);

        param.put(total_account_contractual, tac/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(total_account_contractual2, tac2/targetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(total_account_contractual3, tac3/targetAssetsLiability3.getTotal_assets().get(0)*100+"%");

        //固定资产+在建工程+工程物资
        String total_fixed_asset=target+"total_fixed_asset";
        String total_fixed_asset2=target2+"total_fixed_asset";
        String total_fixed_asset3=target3+"total_fixed_asset";
        double tfa=targetAssetsLiability.getFixed_asset().get(0)+targetAssetsLiability.getConstruction_in_process().get(0)+targetAssetsLiability.getProject_goods_and_material().get(0);
        double tfa2=targetAssetsLiability2.getFixed_asset().get(0)+targetAssetsLiability2.getConstruction_in_process().get(0)+targetAssetsLiability2.getProject_goods_and_material().get(0);
        double tfa3=targetAssetsLiability3.getFixed_asset().get(0)+targetAssetsLiability3.getConstruction_in_process().get(0)+targetAssetsLiability3.getProject_goods_and_material().get(0);
        param.put(total_fixed_asset, getStrValue(tfa));
        param.put(total_fixed_asset2, getStrValue(tfa2));
        param.put(total_fixed_asset3, getStrValue(tfa3));
//        固定资产合计占总资产比率
        String total_fixed_asset_div_total_asset=target+"total_fixed_asset_div_total_asset";
        String total_fixed_asset_div_total_asset2=target2+"total_fixed_asset_div_total_asset";
        String total_fixed_asset_div_total_asset3=target3+"total_fixed_asset_div_total_asset";

        param.put(total_fixed_asset_div_total_asset, tfa/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(total_fixed_asset_div_total_asset2, tfa2/targetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(total_fixed_asset_div_total_asset3, tfa3/targetAssetsLiability3.getTotal_assets().get(0)*100+"%");

        //投资类资产合计:以公允价值计量且其变动计入当期损益的金融资产+债权投资+可供出售金融资产
        // +其他权益工具投资+其他债权投资+持有至到期投资+其他非流动金融资产+长期股权投资+投资性房地产
        String total_invest=target+"total_invest";
        String total_invest2=target2+"total_invest";
        String total_invest3=target3+"total_invest";
        double ti=targetAssetsLiability.getOther_eq_ins_invest().get(0)+targetAssetsLiability.getOther_illiquid_fnncl_assets().get(0)+targetAssetsLiability.getLt_equity_invest().get(0)+targetAssetsLiability.getInvest_property().get(0);
        double ti2=targetAssetsLiability2.getOther_eq_ins_invest().get(0)+targetAssetsLiability2.getOther_illiquid_fnncl_assets().get(0)+targetAssetsLiability2.getLt_equity_invest().get(0)+targetAssetsLiability2.getInvest_property().get(0);
        double ti3=targetAssetsLiability3.getOther_eq_ins_invest().get(0)+targetAssetsLiability3.getOther_illiquid_fnncl_assets().get(0)+targetAssetsLiability3.getLt_equity_invest().get(0)+targetAssetsLiability3.getInvest_property().get(0);

        param.put(total_invest, getStrValue(ti));
        param.put(total_invest2, getStrValue(ti2));
        param.put(total_invest3, getStrValue(ti3));

        //投资类资产占总资产比率
        String total_invest_div_total_asset=target+"total_invest_div_total_asset";
        String total_invest_div_total_asset2=target2+"total_invest_div_total_asset";
        String total_invest_div_total_asset3=target3+"total_invest_div_total_asset";

        param.put(total_invest_div_total_asset, ti/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(total_invest_div_total_asset2, ti2/targetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(total_invest_div_total_asset3, ti3/targetAssetsLiability3.getTotal_assets().get(0)*100+"%");
        //存货占总资产比率
        String inventory_div_assets=target+"inventory_div_assets";
        String inventory_div_assets2=target2+"inventory_div_assets";
        String inventory_div_assets3=target3+"inventory_div_assets";
        param.put(inventory_div_assets, targetAssetsLiability.getInventory().get(0)/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(inventory_div_assets2, targetAssetsLiability2.getInventory().get(0)/targetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(inventory_div_assets3, targetAssetsLiability3.getInventory().get(0)/targetAssetsLiability3.getTotal_assets().get(0)*100+"%");

        //商誉占总资产比率
        String y1_goodwill_div_assets=target+"goodwill_div_assets";
        String y1_goodwill_div_assets2=target2+"goodwill_div_assets";
        String y1_goodwill_div_assets3=target3+"goodwill_div_assets";

        param.put(y1_goodwill_div_assets, targetAssetsLiability.getGoodwill().get(0)/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(y1_goodwill_div_assets2, targetAssetsLiability2.getGoodwill().get(0)/targetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(y1_goodwill_div_assets3, targetAssetsLiability3.getGoodwill().get(0)/targetAssetsLiability3.getTotal_assets().get(0)*100+"%");

        //营业收入增长率
        String total_revenue_r=target+"total_revenue_r";
        String total_revenue_r2=target2+"total_revenue_r";
        String total_revenue_r3=target3+"total_revenue_r";
        double tr=targetInterest.getTotal_revenue().get(0)-preTargetInterest.getTotal_revenue().get(0);
        double tr2=targetInterest2.getTotal_revenue().get(0)-preTargetInterest2.getTotal_revenue().get(0);
        double tr3=targetInterest3.getTotal_revenue().get(0)-preTargetInterest3.getTotal_revenue().get(0);

        param.put(total_revenue_r, tr/preTargetInterest.getTotal_revenue().get(0)*100+"%");
        param.put(total_revenue_r2, tr2/preTargetInterest2.getTotal_revenue().get(0)*100+"%");
        param.put(total_revenue_r3, tr3/preTargetInterest3.getTotal_revenue().get(0)*100+"%");

        //毛利率=（营业收入-营业成本）/营业收入*100%
        String gross_margin=target+"gross_margin";
        String gross_margin2=target2+"gross_margin";
        String gross_margin3=target3+"gross_margin";
        double gm=targetInterest.getTotal_revenue().get(0)-targetInterest.getOperating_costs().get(0);
        double gm2=targetInterest2.getTotal_revenue().get(0)-targetInterest2.getOperating_costs().get(0);
        double gm3=targetInterest3.getTotal_revenue().get(0)-targetInterest3.getOperating_costs().get(0);

        param.put(gross_margin, gm/preTargetInterest.getTotal_revenue().get(0)*100+"%");
        param.put(gross_margin2, gm2/preTargetInterest2.getTotal_revenue().get(0)*100+"%");
        param.put(gross_margin3, gm3/preTargetInterest3.getTotal_revenue().get(0)*100+"%");

        //上一年的毛利率
        double pre_gm=preTargetInterest.getTotal_revenue().get(0)-preTargetInterest.getOperating_costs().get(0);
        double pre_gm2=preTargetInterest2.getTotal_revenue().get(0)-preTargetInterest2.getOperating_costs().get(0);
        double pre_gm3=preTargetInterest3.getTotal_revenue().get(0)-preTargetInterest3.getOperating_costs().get(0);
        //毛利率波动
        String gross_margin_z=target+"gross_margin_z";
        String gross_margin_z2=target+"gross_margin_z";
        String gross_margin_z3=target+"gross_margin_z";
        param.put(gross_margin_z, (gm-pre_gm)/pre_gm*100+"%");
        param.put(gross_margin_z2, (gm2-pre_gm2)/pre_gm2*100+"%");
        param.put(gross_margin_z3, (gm3-pre_gm3)/pre_gm3*100+"%");

        //四费合计：销售费用+管理费用+研发费用+财务费用
        String total_four_free=target+"total_four_free";
        String total_four_free2=target2+"total_four_free";
        String total_four_free3=target3+"total_four_free";
        double tff=targetInterest.getSales_fee().get(0)+targetInterest.getManage_fee().get(0)+targetInterest.getRad_cost().get(0)+targetInterest.getFinancing_expenses().get(0);
        double tff2=targetInterest2.getSales_fee().get(0)+targetInterest2.getManage_fee().get(0)+targetInterest2.getRad_cost().get(0)+targetInterest2.getFinancing_expenses().get(0);
        double tff3=targetInterest3.getSales_fee().get(0)+targetInterest3.getManage_fee().get(0)+targetInterest3.getRad_cost().get(0)+targetInterest3.getFinancing_expenses().get(0);

        param.put(total_four_free, getStrValue(tff));
        param.put(total_four_free2, getStrValue(tff2));
        param.put(total_four_free3, getStrValue(tff3));

        String total_four_free_div_total_revenue=target+"total_four_free_div_total_revenue";
        String total_four_free_div_total_revenue2=target2+"total_four_free_div_total_revenue";
        String total_four_free_div_total_revenue3=target3+"total_four_free_div_total_revenue";

        //费用率=四费/营业收入*100%
        param.put(total_four_free_div_total_revenue, tff/targetInterest.getTotal_revenue().get(0)*100+"%");
        param.put(total_four_free_div_total_revenue2, tff2/targetInterest2.getTotal_revenue().get(0)*100+"%");
        param.put(total_four_free_div_total_revenue3, tff3/targetInterest3.getTotal_revenue().get(0)*100+"%");
        //费用率占毛利率比例
        String total_four_free_div_gross_margin=target+"total_four_free_div_gross_margin";
        String total_four_free_div_gross_margin2=target2+"total_four_free_div_gross_margin";
        String total_four_free_div_gross_margin3=target3+"total_four_free_div_gross_margin";
        param.put(total_four_free_div_gross_margin, tff/gm*100+"%");
        param.put(total_four_free_div_gross_margin2, tff2/gm2*100+"%");
        param.put(total_four_free_div_gross_margin3, tff3/gm3*100+"%");

        //销售费用率 =销售费用/营业收入
        String sales_fee_div_assets=target+"sales_fee_div_assets";
        String sales_fee_div_assets2=target2+"sales_fee_div_assets";
        String sales_fee_div_assets3=target3+"sales_fee_div_assets";
        param.put(sales_fee_div_assets, tff/gm*100+"%");
        param.put(sales_fee_div_assets2, tff2/gm2*100+"%");
        param.put(sales_fee_div_assets3, tff3/gm3*100+"%");
        //主营利润=营业收入-营业成本-税金及附加-销售费用-管理费用-研发费用-财务费用
        String main_op=target+"main_op";
        String main_op2=target2+"main_op";
        String main_op3=target3+"main_op";
        double mo=targetInterest.getTotal_revenue().get(0)-targetInterest.getOperating_costs().get(0)-targetInterest.getOperating_taxes_and_surcharge().get(0)-tff;
        double mo2=targetInterest2.getTotal_revenue().get(0)-targetInterest2.getOperating_costs().get(0)-targetInterest2.getOperating_taxes_and_surcharge().get(0)-tff2;
        double mo3=targetInterest3.getTotal_revenue().get(0)-targetInterest3.getOperating_costs().get(0)-targetInterest3.getOperating_taxes_and_surcharge().get(0)-tff3;

        param.put(main_op, getStrValue(mo));
        param.put(main_op2, getStrValue(mo2));
        param.put(main_op3, getStrValue(mo3));

        //营业利润率=营业利润/营业收入*100%
        String main_op_r=target+"main_op_r";
        String main_op_r2=target2+"main_op_r";
        String main_op_r3=target3+"main_op_r";
        param.put(main_op_r, targetInterest.getOp().get(0)/targetInterest.getTotal_revenue().get(0)*100+"%");
        param.put(main_op_r2, targetInterest2.getOp().get(0)/targetInterest2.getTotal_revenue().get(0)*100+"%");
        param.put(main_op_r3, targetInterest3.getOp().get(0)/targetInterest3.getTotal_revenue().get(0)*100+"%");

        //主营利润/营业利润比值
        String main_op_div_op=target+"main_op_div_op";
        String main_op_div_op2=target2+"main_op_div_op";
        String main_op_div_op3=target3+"main_op_div_op";
        param.put(main_op_div_op,  mo/targetInterest.getOp().get(0)*100+"%");
        param.put(main_op_div_op2, mo/targetInterest2.getOp().get(0)*100+"%");
        param.put(main_op_div_op3, mo/targetInterest3.getOp().get(0)*100+"%");

        //净利润现金比率=经营活动产生的现金流量净额/净利润
        String ncf_from_oa_div_net_profit=target+"ncf_from_oa_div_net_profit";
        String ncf_from_oa_div_net_profit2=target2+"ncf_from_oa_div_net_profit";
        String ncf_from_oa_div_net_profit3=target3+"ncf_from_oa_div_net_profit";
        param.put(ncf_from_oa_div_net_profit,   targetCashFlow.getNcf_from_oa().get(0)/targetInterest.getNet_profit().get(0)*100+"%");
        param.put(ncf_from_oa_div_net_profit2, targetCashFlow2.getNcf_from_oa().get(0)/targetInterest2.getNet_profit().get(0)*100+"%");
        param.put(ncf_from_oa_div_net_profit3, targetCashFlow3.getNcf_from_oa().get(0)/targetInterest3.getNet_profit().get(0)*100+"%");

        //净资产收益率roe=净利润/所有者权益*100%
        String roe=target+"roe";
        String roe2=target2+"roe";
        String roe3=target3+"roe";
        param.put(roe,  targetInterest.getNet_profit_atsopc().get(0)/targetAssetsLiability.getTotal_quity_atsopc()
            .get(0)*100+"%");
        param.put(roe2, targetInterest2.getNet_profit_atsopc().get(0)/targetAssetsLiability2.getTotal_quity_atsopc().get(0)*100+"%");
        param.put(roe3, targetInterest3.getNet_profit_atsopc().get(0)/targetAssetsLiability3.getTotal_quity_atsopc().get(0)*100+"%");

        //归属于母公司所有者的净利润增长率
        String net_profit_atsopc_r=target+"net_profit_atsopc_r";
        String net_profit_atsopc_r2=target2+"net_profit_atsopc_r";
        String net_profit_atsopc_r3=target3+"net_profit_atsopc_r";
        double npa=targetInterest.getNet_profit_atsopc().get(0)-preTargetInterest.getNet_profit_atsopc().get(0);
        double npa2=targetInterest.getNet_profit_atsopc().get(0)-preTargetInterest2.getNet_profit_atsopc().get(0);
        double npa3=targetInterest.getNet_profit_atsopc().get(0)-preTargetInterest3.getNet_profit_atsopc().get(0);
        param.put(net_profit_atsopc_r,  npa/preTargetInterest.getNet_profit_atsopc().get(0)*100+"%");
        param.put(net_profit_atsopc_r2, npa2/preTargetInterest2.getNet_profit_atsopc().get(0)*100+"%");
        param.put(net_profit_atsopc_r3, npa3/preTargetInterest3.getNet_profit_atsopc().get(0)*100+"%");
        //购建固定资产、无形资产和其他长期资产支付的现金/经营活动产生的现金流量净额
        String cash_paid_for_assets_div_ncf_from_oa=target+"cash_paid_for_assets_div_ncf_from_oa";
        String cash_paid_for_assets_div_ncf_from_oa2=target2+"cash_paid_for_assets_div_ncf_from_oa";
        String cash_paid_for_assets_div_ncf_from_oa3=target3+"cash_paid_for_assets_div_ncf_from_oa";
        param.put(cash_paid_for_assets_div_ncf_from_oa,  targetCashFlow.getCash_paid_for_assets().get(0)/targetCashFlow.getNcf_from_fa().get(0)*100+"%");
        param.put(cash_paid_for_assets_div_ncf_from_oa2, targetCashFlow2.getCash_paid_for_assets().get(0)/targetCashFlow2.getNcf_from_fa().get(0)*100+"%");
        param.put(cash_paid_for_assets_div_ncf_from_oa3, targetCashFlow3.getCash_paid_for_assets().get(0)/targetCashFlow3.getNcf_from_fa().get(0)*100+"%");

        //分红率=分配股利、利润或偿付利息支付的现金与经营活动产生的现金流量净额的比率
        String dividend_rate=target+"dividend_rate";
        String dividend_rate2=target2+"dividend_rate";
        String dividend_rate3=target3+"dividend_rate";
        param.put(dividend_rate,  targetCashFlow.getCash_paid_of_distribution().get(0)/targetCashFlow.getNcf_from_fa().get(0)*100+"%");
        param.put(dividend_rate2, targetCashFlow2.getCash_paid_of_distribution().get(0)/targetCashFlow2.getNcf_from_fa().get(0)*100+"%");
        param.put(dividend_rate3, targetCashFlow3.getCash_paid_of_distribution().get(0)/targetCashFlow3.getNcf_from_fa().get(0)*100+"%");
        return param;
    }

    private String getStrValue(double value) {
        double vv = value / 1000000000.0;
        if (vv * 100 > 1) {
            return vv + "亿";
        } else {
            return value / 10000.0 + "万";
        }
    }


}
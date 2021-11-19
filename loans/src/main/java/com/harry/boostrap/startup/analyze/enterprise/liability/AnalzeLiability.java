package com.harry.boostrap.startup.analyze.enterprise.liability;

import com.alibaba.fastjson.TypeReference;
import com.harry.boostrap.startup.analyze.enterprise.cash.CashContent;
import com.harry.boostrap.startup.analyze.enterprise.cash.CashHandler;
import com.harry.boostrap.startup.analyze.enterprise.interest.Interest;
import com.harry.boostrap.startup.analyze.enterprise.interest.InterestHandler;
import com.harry.boostrap.startup.analyze.enterprise.interest.Quota;
import com.harry.boostrap.startup.analyze.enterprise.quote.QuoteHandler;
import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExcelConstants;
import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExportExcel;
import com.harry.boostrap.startup.analyze.excel.ColorType;
import com.harry.boostrap.startup.analyze.excel.ExportData;
import com.harry.boostrap.startup.analyze.utils.DataCheckNullAndAssigmentUtils;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import com.harry.boostrap.startup.analyze.utils.LogerColorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.harry.boostrap.startup.analyze.excel.ColorType.*;
/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：分析财务报表
 */
@Slf4j
public class AnalzeLiability {
    public static void main(String[] args) throws IOException, URISyntaxException {
        defaultAnalzeLiability("");
    }

    public static AnalzeLiabilityExportExcel defaultAnalzeLiability(String symbol) throws IOException, URISyntaxException {
        symbol= getSymbol(symbol);
        String type="Q4";
        int count=5;
        return analzeLiability(symbol, type, count);
    }

    private static AnalzeLiabilityExportExcel analzeLiability(String symbol, String type, int count) throws IOException, URISyntaxException {
        AnnualReport data = getResponseData(symbol, type, count);
        AnnualReport<Interest> interest = InterestHandler.getInterest(symbol, type, count);
        AnnualReport<Quota> quota = InterestHandler.getQuota(symbol, type, count);
        AnnualReport<CashContent> cashFlow = CashHandler.getCashFlow(symbol, type, count);
        AnalzeLiabilityExportExcel analzeLiabilityExportExcel=new AnalzeLiabilityExportExcel();
        analzeLiabilityExportExcel.setQuote(QuoteHandler.handlerQuote(symbol));
        analzeLiabilityExportExcel.setSymbol(symbol);
        List<Map<String, ExportData>>datas=new ArrayList<>();
        if(data!=null){
            analzeLiabilityExportExcel.setCompanyName(data.getQuote_name());
            println("当前查询上市公司：“"+data.getQuote_name()+"”的财务报表,币种名称是"+data.getCurrency_name()+",币种类型："+data.getCurrency()+",季度："+data.getLast_report_name());
            Map<String, Interest> interestMap = interest.getList().stream().collect(Collectors.toMap(Interest::getReport_name, Function.identity()));
            Map<String, Quota> quotaMap = quota.getList().stream().collect(Collectors.toMap(Quota::getReport_name, Function.identity()));
            Map<String, CashContent> cashContentMap = cashFlow.getList().stream().collect(Collectors.toMap(CashContent::getReport_name, Function.identity()));
            List<AssetsLiability> list = data.getList();
            if(!CollectionUtils.isEmpty(list)){
                for (int x=0;x<list.size();x++){
                    //当前年报
                    Map<String, ExportData> dataMap=new HashMap<>();
                    AssetsLiability current = list.get(x);
                    println("报告日期："+current.getReport_name());
                    dataMap.put(AnalzeLiabilityExcelConstants.DATE,new ExportData(current.getReport_name()));
                    Interest interestCurrent = interestMap.get(current.getReport_name());
                    dataMap.putAll(InterestHandler.handlePeriod(quotaMap,interestCurrent));
                    CashContent cashContent = cashContentMap.get(current.getReport_name());
                    //净利润现金含量
                    dataMap.putAll(CashHandler.handlerCash(interestCurrent,cashContent));

                    dataMap.putAll(CashHandler.handlerCashPaidForAssets(cashContent));

                    if(x+1>list.size()-1){
                        break;
                    }
                    //上一年报
                    AssetsLiability last = list.get(x+1);
                    Interest interestLast = interestMap.get(last.getReport_name());
                    dataMap.putAll(InterestHandler.grossSellingRateV(quotaMap,current.getReport_name(),interestLast));

                    dataMap.putAll(analyzeLiaility(current, last));

                    //看归母净利润，了解公司的整体盈利能力及持续性。
                    dataMap.putAll(wholeProfit(interestCurrent,interestLast,current));
                    datas.add(dataMap);
                    println("-------------------------------------------------------------");
                }
            }
        }
        analzeLiabilityExportExcel.setDataMap(datas);
        return analzeLiabilityExportExcel;
    }

    public static String getSymbol(String symbol) {
        return symbol.startsWith("6")?("SH"+symbol):("SZ"+symbol);
    }

    /**
     * 看归母净利润，了解公司的整体盈利能力及持续性。
     *  用“归母净利润”除以“归母所有者权益”可以得到净资产收益率，也叫 ROE。 ■ 最优秀公司的 ROE 一般会持续大于 20%，优秀公司的 ROE 也会持续大于 15%。ROE 小于 15%的公司
     * 需要淘汰掉。另外归母净利润增长率持续小于 10%的公司也要淘汰掉。
     * @param insterestCurrent
     * @param current
     * @param last
     */
    private static Map<String, ExportData> wholeProfit(Interest insterestCurrent,Interest last,AssetsLiability current) {
        Map<String, ExportData>dataMap=new HashMap<>();
        //归属于母公司所有者的净利润
        double netProfitAtsopc = insterestCurrent.getNet_profit_atsopc().get(0).doubleValue();
        double netProfitAtsopcLast = last.getNet_profit_atsopc().get(0).doubleValue();
        //归属于母公司股东权益合计
        double totalQuityAtsopc = current.getTotal_quity_atsopc().get(0).doubleValue();

        double roe=netProfitAtsopc/totalQuityAtsopc*100;

        if(roe>20){
            println("净资产收益率ROE："+roe+"，好公司的指标标准");
            dataMap.put(AnalzeLiabilityExcelConstants.ROE,new ExportData(roe));
        }else if(roe>15){
            println("净资产收益率ROE："+roe+",优秀公司指标");
            dataMap.put(AnalzeLiabilityExcelConstants.ROE,new ExportData(roe));
        }else {
            println("净资产收益率ROE："+roe+",淘汰掉",RED);
            dataMap.put(AnalzeLiabilityExcelConstants.ROE,new ExportData(roe,RED));
        }
        //净利润增长率
        double profitAtsopcV = (netProfitAtsopc - netProfitAtsopcLast) / netProfitAtsopcLast * 100;

        if(profitAtsopcV<10){
            println("净利润增长率（%）："+profitAtsopcV+"，太差了要淘汰",RED);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_PROFIT_GROWTH_RATE,new ExportData(profitAtsopcV,RED));
        }else {
            println("净利润增长率（%）："+profitAtsopcV);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_PROFIT_GROWTH_RATE,new ExportData(profitAtsopcV));
        }


        //营业收入
        double currentTotalRevenue = insterestCurrent.getTotal_revenue() == null ? 0 : insterestCurrent.getTotal_revenue().get(0).doubleValue();
        double lastTotalRevenue = last.getTotal_revenue() == null ? 0 : last.getTotal_revenue().get(0).doubleValue();
        double totalRevenueRate = (currentTotalRevenue - lastTotalRevenue) / lastTotalRevenue*100;
        //营业利润率=营业利润/营业收入*100%
        double opV = insterestCurrent.getOp() == null ? 0 : insterestCurrent.getOp().get(0).doubleValue()/currentTotalRevenue*100;
        if(opV>=20){
            println("营业利润率（%）："+opV);
            dataMap.put(AnalzeLiabilityExcelConstants.OP_RATE,new ExportData(opV));
        }else {
            println("营业利润率（%）："+opV,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.OP_RATE,new ExportData(opV,RED));
        }

        if(totalRevenueRate>10){
            println("营业收入增长率（%）："+totalRevenueRate);
            dataMap.put(AnalzeLiabilityExcelConstants.TOTAL_REVENUE_RATE,new ExportData(totalRevenueRate));
        }else if(totalRevenueRate>0){
            println("营业收入增长率（%）："+totalRevenueRate+"，公司行业地位及成长性一般",YELLOW);
            dataMap.put(AnalzeLiabilityExcelConstants.TOTAL_REVENUE_RATE,new ExportData(totalRevenueRate,YELLOW));
        }else {
            println("营业收入增长率（%）："+totalRevenueRate+"，公司行业地位及成长性成长差",RED);
            dataMap.put(AnalzeLiabilityExcelConstants.TOTAL_REVENUE_RATE,new ExportData(totalRevenueRate,RED));
        }
        return dataMap;
    }

    private static Map<String, ExportData> analyzeLiaility(AssetsLiability current, AssetsLiability last) {
        Map<String, ExportData>dataMap=new HashMap<>();
        //总资产比上一年涨跌
        double totalAssets = (current.getTotal_assets().get(0) - last.getTotal_assets().get(0)) / last.getTotal_assets().get(0)*100;

        if(totalAssets>0){
            println("总资产增长率（%）："+totalAssets);
            dataMap.put(AnalzeLiabilityExcelConstants.GROWTH_RATE_OF_TOTAL_ASSETS,new ExportData(totalAssets));
        }else {
            println("总资产增长率（%）："+totalAssets,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.GROWTH_RATE_OF_TOTAL_ASSETS,new ExportData(totalAssets,RED));
        }

        //负债超过60%有偿债风险
        double liability = current.getAsset_liab_ratio().get(0) * 100;

        if(liability>60){
            println("负债率（%）："+liability,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.LIABILITY,new ExportData(liability,RED));
        }else {
            println("负债率（%）："+liability);
            dataMap.put(AnalzeLiabilityExcelConstants.LIABILITY,new ExportData(liability));
        }

        double redyCurrency = current.getCurrency_funds()==null?0:current.getCurrency_funds().get(0) + (current.getTradable_fnncl_assets()==null?0:current.getTradable_fnncl_assets().get(0));
        dataMap.put(AnalzeLiabilityExcelConstants.QUASI_MONETARY_FUND,new ExportData(redyCurrency));
        println("准货币基金："+redyCurrency);
        double InterestBearingLiabilities = (current.getSt_loan()==null?0:current.getSt_loan().get(0)) + (current.getNoncurrent_liab_due_in1y()==null?0:current.getNoncurrent_liab_due_in1y().get(0)) + (current.getLt_loan()==null?0:current.getLt_loan().get(0)) + (current.getBond_payable()==null?0:current.getBond_payable().get(0)) + (current.getLt_payable()==null?0:current.getLt_payable().get(0));
        println("有息负债："+InterestBearingLiabilities);
        dataMap.put(AnalzeLiabilityExcelConstants.INTEREST_BEARING_LIABILITIES,new ExportData(InterestBearingLiabilities));
        double solvency = redyCurrency - InterestBearingLiabilities;

        if(solvency>0){
            println("偿债能力："+solvency);
            dataMap.put(AnalzeLiabilityExcelConstants.SOLVENCY,new ExportData(solvency));
        }else {
            println("偿债能力："+solvency,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.SOLVENCY,new ExportData(solvency,RED));
        }
        //企业竞争力
        //应付预收-应收预付
        //值越大代表公司的竞争力越强
        //负值代表竞争力很差
        double componeyCompetity = (current.getAccounts_payable()==null?0:current.getAccounts_payable().get(0)) + (current.getPre_receivable()==null?0:current.getPre_receivable().get(0)) + (current.getBill_payable()==null?0:current.getBill_payable().get(0)) - (current.getAccount_receivable()==null?0:current.getAccount_receivable().get(0)) - (current.getBills_receivable()==null?0:current.getBills_receivable().get(0)) - (current.getPre_payment()==null?0:current.getPre_payment().get(0));

        if(componeyCompetity>0){
            println("企业竞争力："+componeyCompetity);
            dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_COMPETITIVENESS,new ExportData(componeyCompetity));
        }else {
            println("企业竞争力："+componeyCompetity,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_COMPETITIVENESS,new ExportData(componeyCompetity,RED));
        }
        //产品竞争力
        //应收/总资产
        //值越小代表产品得到市场认可容易销售，小于1%表示产品竞争力非常强，小于3%表示产品容易销售，大于10%表示产品比较难销售，大于20%表示很难销售
        double productCompetity = ((current.getAccount_receivable()==null?0:current.getAccount_receivable().get(0)) + (current.getBills_receivable()==null?0:current.getBills_receivable().get(0)))/ current.getTotal_assets().get(0) * 100;

        if(productCompetity<1){
            println("产品竞争力(%)："+productCompetity+",产品竞争力非常强，非常容易销售");
            dataMap.put(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,new ExportData(productCompetity));
        }else if(productCompetity<3){
            println("产品竞争力(%)："+productCompetity+",产品竞争力强，容易销售");
            dataMap.put(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,new ExportData(productCompetity));
        }else if(productCompetity<10){
            println("产品竞争力(%)："+productCompetity+",产品竞争力还可以，可以销售",BLUE);
            dataMap.put(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,new ExportData(productCompetity,BLUE));
        }else if(productCompetity<20){
            println("产品竞争力(%)："+productCompetity+",产品竞争力较弱，不太容易销售",YELLOW);
            dataMap.put(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,new ExportData(productCompetity,YELLOW));
        }else {
            println("产品竞争力(%)："+productCompetity+",产品竞争力非常弱，很难销售",RED);
            dataMap.put(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,new ExportData(productCompetity,RED));
        }
        //（固定资产+在建工程）/总资产
        //值小于20%表示轻资产型公司，大于40%表示重资产型公司直接淘汰
        double maintainCompetitiveness = ((current.getFixed_asset_sum()==null?0:current.getFixed_asset_sum().get(0)) + (current.getConstruction_in_process_sum()==null?0:current.getConstruction_in_process_sum().get(0)))/current.getTotal_assets().get(0)*100;

        if(maintainCompetitiveness>40){
            println("维持竞争力(%)："+maintainCompetitiveness,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.MAINTAIN_COMPETITIVENESS,new ExportData(maintainCompetitiveness,RED));
        }else {
            println("维持竞争力(%)："+maintainCompetitiveness);
            dataMap.put(AnalzeLiabilityExcelConstants.MAINTAIN_COMPETITIVENESS,new ExportData(maintainCompetitiveness));
        }
        //看投资类/总资产比率
        //值越小越好，最好是0，大于10%直接淘汰
        double concentration = ((current.getInvest_property()==null?0:current.getInvest_property().get(0)) + (current.getLt_equity_invest()==null?0:current.getLt_equity_invest().get(0)) + (current.getOther_illiquid_fnncl_assets()==null?0:current.getOther_illiquid_fnncl_assets().get(0)))/current.getTotal_assets().get(0)*100;

        if(concentration>10){
            println("专注度(%)："+concentration,RED);
            dataMap.put(AnalzeLiabilityExcelConstants.CONCENTRATION,new ExportData(concentration,RED));
        }else {
            println("专注度(%)："+concentration);
            dataMap.put(AnalzeLiabilityExcelConstants.CONCENTRATION,new ExportData(concentration));
        }


        //存货占比
        double inventoryVs=(current.getInventory()==null?0:current.getInventory().get(0))/current.getTotal_assets().get(0)*100;
        //商誉占比
        double goodwillVs=(current.getGoodwill()==null?0:current.getGoodwill().get(0))/current.getTotal_assets().get(0)*100;


        /**
         * 看存货、商誉，了解公司未来业绩爆雷的风险。
         * ■ 易爆雷资产主要包括应收账款、存货、长期股权投资、固定资产、商誉，这五个科目是最容易埋雷的地
         * 方，后期爆雷的时候会导致利润大幅减少甚至大幅亏损。由于我们在前六步中已经看过了应收账款、固定资产、长
         * 期股权投资科目，排除了这三个科目爆雷的风险。
         * 〇 这里重点看一下存货和商誉。
         * ■ 对于“应付预收”减“应收预付”差额大于 0 并且应收账款占总资产比率小于 1%的公司来说，存货基
         * 本没有爆雷的风险。
         * ■ 对于应收账款占总资产的比率大于 5%并且存货占总资产的比率大于 15%的公司，爆雷的风险比较大，
         * 需要淘汰掉。
         * 商誉/总资产大于10直接淘汰
         */


        if(componeyCompetity>0&&productCompetity<=1){
            println("整体暴雷风险(%)："+0+",也就是没有暴雷风险");
            dataMap.put(AnalzeLiabilityExcelConstants.GOODS_HUNDER_RISK,new ExportData(0d));
            dataMap.put(AnalzeLiabilityExcelConstants.STOCK_HUNDER_RISK,new ExportData(0d));
        }else if(productCompetity>5&&inventoryVs>15){
            println("暴雷风险(存货%)："+inventoryVs+",产品竞争力："+productCompetity+",整体存在暴雷风险比较大",YELLOW);
            dataMap.put(AnalzeLiabilityExcelConstants.STOCK_HUNDER_RISK,new ExportData(inventoryVs,RED));
            dataMap.put(AnalzeLiabilityExcelConstants.GOODS_HUNDER_RISK,new ExportData(goodwillVs));
        }else if(goodwillVs>10){
            println("暴雷风险(商誉%)："+goodwillVs+",大于10%，暴雷非常大",RED);
            dataMap.put(AnalzeLiabilityExcelConstants.GOODS_HUNDER_RISK,new ExportData(goodwillVs,RED));
            dataMap.put(AnalzeLiabilityExcelConstants.STOCK_HUNDER_RISK,new ExportData(inventoryVs));
        }else {
            println("暴雷风险不大");
            dataMap.put(AnalzeLiabilityExcelConstants.GOODS_HUNDER_RISK,new ExportData(goodwillVs));
            dataMap.put(AnalzeLiabilityExcelConstants.STOCK_HUNDER_RISK,new ExportData(inventoryVs));
        }
        return dataMap;

    }

    public static void println(String msg, ColorType colorType){
        log.info(LogerColorUtils.getColorFormat(msg, colorType));
    }

    public static void println(String msg){
        log.info(LogerColorUtils.getColorFormat(msg, WHITE));
    }

    /**
     * 获取股票年报
     * @param symbol 股票代码，上证所:SH+股票代码，深：SZ+股票代码
     * @param type 查询类型，Q4年报，Q2中报，Q1一季报，Q3三季报
     * @param count 连续查询最近多少年
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private static AnnualReport getResponseData(String symbol,String type,int count) throws IOException, URISyntaxException{
        if(count<2){
            throw new RuntimeException("查询年数不能低于2年");
        }
        String mainUrl="https://xueqiu.com";
        String url="https://stock.xueqiu.com/v5/stock/finance/cn/balance.json?symbol="+symbol+"&type="+type+"&is_detail=true&count="+count+"&timestamp=";
        HttpUtil.get(mainUrl,null);
        AnnualReport<AssetsLiability> s = HttpUtil.get(url,new TypeReference<AnnualReport<AssetsLiability>>(){});
        System.out.println(s);
        if(s!=null){
            DataCheckNullAndAssigmentUtils.assigmentAssetsLiability(s.getList());
        }
        return s;
    }


}

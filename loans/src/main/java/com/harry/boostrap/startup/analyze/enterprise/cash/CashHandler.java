package com.harry.boostrap.startup.analyze.enterprise.cash;

import com.alibaba.fastjson.TypeReference;
import com.harry.boostrap.startup.analyze.enterprise.interest.Interest;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExcelConstants;
import com.harry.boostrap.startup.analyze.excel.ColorType;
import com.harry.boostrap.startup.analyze.excel.ExportData;
import com.harry.boostrap.startup.analyze.utils.DataCheckNullAndAssigmentUtils;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability.println;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：现金含量
 */
public class CashHandler {

    /**
     * 获取股票年报
     * @param symbol 股票代码，上证所:SH+股票代码，深：SZ+股票代码
     * @param type 查询类型，Q4年报，Q2中报，Q1一季报，Q3三季报
     * @param count 连续查询最近多少年
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static AnnualReport<CashContent> getCashFlow(String symbol, String type, int count) throws IOException, URISyntaxException{
        if(count<2){
            throw new RuntimeException("查询年数不能低于2年");
        }
        String url="https://stock.xueqiu.com/v5/stock/finance/cn/cash_flow.json?symbol="+symbol+"&type="+type+"&is_detail=true&count="+count+"&timestamp=";
        AnnualReport<CashContent> s = HttpUtil.get(url,new TypeReference<AnnualReport<CashContent>>(){});
        System.out.println(s);
        if(s!=null){
            DataCheckNullAndAssigmentUtils.assigmentAssetsLiability(s.getList());
        }
        return s;
    }

    /**
     * 看净利润，了解公司的经营成果及含金量。净利润主要看净利润含金量。
     * @param interest
     * @param cashContent
     */
    public static Map<String, ExportData> handlerCash(Interest interest, CashContent cashContent){
        Map<String, ExportData>dataMap=new HashMap<>();
        //净利润
        double netProfit = interest.getNet_profit().get(0).doubleValue();
        /**
         * 经营活动产生的现金流量净额
         */
        double ncfFromOa = cashContent.getNcf_from_oa().get(0).doubleValue();
        //净利润含金量
        double netProfitCashContent=ncfFromOa/netProfit*100;

        //过去 5 年的平均净利润现金比率小于 100%的公司，淘汰掉。
        if(netProfitCashContent<80){
            println("净利润含金量(%):"+netProfitCashContent+",经营成果和含金量太低，淘汰", ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_PROFIT_CASH_CONTENT,new ExportData(netProfitCashContent,ColorType.RED));
        }else if(netProfitCashContent<100){
            println("净利润含金量(%):"+netProfitCashContent+",经营成果和含金量还可以，可以继续看", ColorType.YELLOW);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_PROFIT_CASH_CONTENT,new ExportData(netProfitCashContent,ColorType.YELLOW));
        }else {
            println("净利润含金量(%):"+netProfitCashContent);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_PROFIT_CASH_CONTENT,new ExportData(netProfitCashContent));
        }
        return dataMap;
    }

    /**
     * 1.看购买固定资产、无形资产和其他长期资产支付的现金，了解公司的增长潜力。
     * 购建固定资产、无形资产和其他长期资产支付的现金与经营活动产生的现金流量净额的比率大于 100%
     * 或持续小于 3%的公司需要淘汰掉。这两种类型的公司前者风险较大，后者回报较低。
     *
     * 2.看分配股利、利润或偿付利息支付的现金，了解公司的现金分红情况。
     * 分配股利、利润或偿付利息支付的现金与经营活动产生的现金流量净额的比率最好在 20%-70%之间，
     * 比率小于 20%不够厚道，大于 70%难以持续。
     *
     * 3.近五年数据收集汇总：经营活动产生的现金流量净额、投资活动产生的现
     * 金流量净额、筹资活动产生的现金流量净额，并判断公司当年所属类型:
     * 优秀的公司一般都是“正负负”和“正正负”型
     * @param cashContent
     */
    public static Map<String, ExportData> handlerCashPaidForAssets(CashContent cashContent){
        Map<String, ExportData>dataMap=new HashMap<>();
        /**
         * 经营活动产生的现金流量净额
         */
        double ncfFromOa = cashContent.getNcf_from_oa().get(0).doubleValue();
        if(ncfFromOa>0){
            println("经营活动产生的现金流量净额:"+ncfFromOa);
            dataMap.put(AnalzeLiabilityExcelConstants.NCF_FROM_OA,new ExportData(ncfFromOa));
        }else {
            println("经营活动产生的现金流量净额:"+ncfFromOa,ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.NCF_FROM_OA,new ExportData(ncfFromOa,ColorType.RED));
        }
        double ncfFromOaV = cashContent.getNcf_from_oa().get(1).doubleValue();

        if(ncfFromOaV>0){
            println("经营活动产生的现金流量净额增长率（%）:"+ncfFromOaV);
            dataMap.put(AnalzeLiabilityExcelConstants.NCF_FROM_OA_V,new ExportData(ncfFromOaV));
        }else {
            println("经营活动产生的现金流量净额（%）:"+ncfFromOaV,ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.NCF_FROM_OA_V,new ExportData(ncfFromOaV,ColorType.RED));
        }

        //购建固定资产、无形资产和其他长期资产支付的现金
        double cashPaidForAssets = cashContent.getCash_paid_for_assets().get(0).doubleValue();
        //购建固定资产、无形资产和其他长期资产支付的现金与经营活动产生的现金流量净额的比率
        double v = cashPaidForAssets / ncfFromOa * 100;

        if(v>100||v<3){
            println("购建固定资产、无形资产和其他长期资产支付的现金与经营活动产生的现金流量净额的比率(%):"+v, ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_CASH_FLOW_RATIO,new ExportData(v,ColorType.RED));
        }else {
            println("购建固定资产、无形资产和其他长期资产支付的现金与经营活动产生的现金流量净额的比率(%):"+v);
            dataMap.put(AnalzeLiabilityExcelConstants.NET_CASH_FLOW_RATIO,new ExportData(v));
        }

        //分配股利、利润或偿付利息支付的现金
        double cashPaidOfDistribution = cashContent.getCash_paid_of_distribution().get(0).doubleValue();
        //分配股利、利润或偿付利息支付的现金与经营活动产生的现金流量净额的比率
        double cashPaidOfDistributionV = cashPaidOfDistribution / ncfFromOa * 100;

        if(cashPaidOfDistributionV>20&&cashPaidOfDistributionV<70){
            println("现金分红比例(%):"+cashPaidOfDistributionV);
            dataMap.put(AnalzeLiabilityExcelConstants.CASH_DIVIDEND_RATIO,new ExportData(cashPaidOfDistributionV));
        }else {

            println("现金分红比例:"+cashPaidOfDistributionV+",分红太低不够厚道或者难以持续", ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.CASH_DIVIDEND_RATIO,new ExportData(cashPaidOfDistributionV,ColorType.RED));
        }

        //投资活动产生的现金流量净额
        double ncf_from_ia = cashContent.getNcf_from_ia().get(0).doubleValue();
        //筹资活动产生的现金流量净额
        double ncf_from_fa = cashContent.getNcf_from_fa().get(0).doubleValue();
        String s = "经营活动产生的现金流量净额:" + ncfFromOa + "、投资活动产生的现金流量净额:" + ncf_from_ia + "、筹资活动产生的现金流量净额:" + ncf_from_fa;
        if(ncfFromOa>0){
            if(ncf_from_fa<0&&ncf_from_ia<0){
                dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,new ExportData("正负负"));
                println(s);
            }else if(ncf_from_fa>0&&ncf_from_ia>0){
                dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,new ExportData("正正正",ColorType.RED));
                println(s,ColorType.RED);
            }else if(ncf_from_fa<0&&ncf_from_ia>0){
                dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,new ExportData("正正负"));
                println(s);
            }else {
                dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,new ExportData("正负正",ColorType.RED));
                println(s,ColorType.RED);
            }
        }else {
            dataMap.put(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,new ExportData("负xx",ColorType.RED));
            println(s,ColorType.RED);
        }
        //期末现金及现金等价物余额
        double finalBalanceOfCce = cashContent.getFinal_balance_of_cce().get(0).doubleValue();
        dataMap.put(AnalzeLiabilityExcelConstants.FINAL_BALANCE_OF_CCE,new ExportData(finalBalanceOfCce));
        println("期末现金及现金等价物余额:"+finalBalanceOfCce);

        return dataMap;
    }
}

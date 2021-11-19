package com.harry.boostrap.startup.analyze.enterprise.interest;

import com.alibaba.fastjson.TypeReference;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExcelConstants;
import com.harry.boostrap.startup.analyze.excel.ColorType;
import com.harry.boostrap.startup.analyze.excel.ExportData;
import com.harry.boostrap.startup.analyze.utils.DataCheckNullAndAssigmentUtils;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import com.harry.boostrap.startup.analyze.utils.LogerColorUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability.println;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：
 */
public class InterestHandler {
    public static void main(String[] args) throws IOException, URISyntaxException {
        AnnualReport data = getInterest("SH600132", "Q4", 5);
        AnnualReport<Quota> quota = getQuota("SH600132", "Q4", 5);
        println("当前查询上市公司：“"+data.getQuote_name()+"”的财务报表,币种名称是"+data.getCurrency_name()+",币种类型："+data.getCurrency()+",季度："+data.getLast_report_name());
        List<Interest> list = data.getList();
        if(!CollectionUtils.isEmpty(list)){
            Map<String, Quota> quotaMap = quota.getList().stream().collect(Collectors.toMap(Quota::getReport_name, Function.identity()));
            for (int x=0;x<list.size();x++){
                //当前年报
                Interest current = list.get(x);
                println("报告日期："+current.getReport_name());

                handlePeriod(quotaMap, current);

                if(x+1>list.size()-1){
                    break;
                }
                //上一年报
                Interest last = list.get(x+1);
                grossSellingRateV(quotaMap, current.getReport_name(), last);
                println("-------------------------------------------------------------");
            }
        }
    }

    /**
     * 看毛利率，了解公司的产品竞争力及风险
     * 毛利率主要看两点，数值和波幅。毛利率小于 40%或波动幅度大于 20%的公司淘汰掉。
     * 处理毛利率浮动
     * @param quotaMap
     * @param reportName
     * @param last
     */
    public static Map<String, ExportData> grossSellingRateV(Map<String, Quota> quotaMap, String reportName, Interest last) {
        Map<String, ExportData>dataMap=new HashMap<>();
        Quota lastQuota = quotaMap.get(last.getReport_name());
        //销售毛利率
        double lastGrossSellingRate = lastQuota.getGross_selling_rate().get(0).doubleValue();
        double grossSellingRateV = (quotaMap.get(reportName).getGross_selling_rate().get(0).doubleValue() - lastGrossSellingRate) / lastGrossSellingRate * 100;

        if(Math.abs(grossSellingRateV)>20){
            //毛利率浮动超过20%，淘汰
            println("毛利率浮动值（%）:"+grossSellingRateV, ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.FLOATING_VALUE_OF_GROSS_PROFIT_MARGIN,new ExportData(grossSellingRateV,ColorType.RED));
        }else{
            println("毛利率浮动值（%）:"+grossSellingRateV);
            dataMap.put(AnalzeLiabilityExcelConstants.FLOATING_VALUE_OF_GROSS_PROFIT_MARGIN,new ExportData(grossSellingRateV));
        }
        return dataMap;
    }

    /**
     * 1.看期间费用率，了解公司的成本管控能力
     * 优秀公司的期间费用率与毛利率的比率一般小于 40%。期间费用率与毛利率的比率大于 60%的公司淘
     * 汰掉。
     * 2.看销售费用率，了解公司产品的销售难易度。
     * 销售费用率主要看两点，数值和变动趋势。一般来说，销售费用率小于 15%的公司，其产品比较容易
     * 销售，销售风险相对较小。销售费用率大于 30%的公司，其产品销售难度大，销售风险也大。销售费用率大于 30%
     * 的公司淘汰掉。
     * @param quotaMap
     * @param current
     */
    //处理期间费用率
    public static Map<String, ExportData> handlePeriod(Map<String, Quota> quotaMap, Interest current) {
        Map<String, ExportData>dataMap=new HashMap<>();
        //研发费用
        double radCost = current.getRad_cost()==null?0:current.getRad_cost().get(0).doubleValue();

        //销售费用
        double salesFee = current.getSales_fee()==null?0:current.getSales_fee().get(0).doubleValue();
        //管理费用
        double manageFee = current.getManage_fee()==null?0:current.getManage_fee().get(0).doubleValue();
        //财务费用
        double financingExpenses = current.getFinancing_expenses()==null?0:current.getFinancing_expenses().get(0).doubleValue();
        //期间费用合计
        double periodTotal=radCost+salesFee+manageFee+financingExpenses;
        //营业收入
        double totalRevenue = current.getTotal_revenue().get(0).doubleValue();
        //期间费用率
        double period=periodTotal/totalRevenue*100;

        println("期间费用率（%）："+period);
//        dataMap.put(AnalzeLiabilityExcelConstants.PERIOD_EXPENSE_RATE,period);
        Quota currentQuota = quotaMap.get(current.getReport_name());
        //销售毛利率
        double currentGrossSellingRate = currentQuota.getGross_selling_rate().get(0).doubleValue();
        println("销售毛利率（%）："+currentGrossSellingRate);
        if(currentGrossSellingRate>=40){
            dataMap.put(AnalzeLiabilityExcelConstants.GROSS_PROFIT_MARGIN,new ExportData(currentGrossSellingRate));
        }else if(currentGrossSellingRate>20){
            dataMap.put(AnalzeLiabilityExcelConstants.GROSS_PROFIT_MARGIN,new ExportData(currentGrossSellingRate,ColorType.YELLOW));
        }else {
            dataMap.put(AnalzeLiabilityExcelConstants.GROSS_PROFIT_MARGIN,new ExportData(currentGrossSellingRate,ColorType.RED));
        }

        //期间费用率与毛利率的比率
        double quotaInGrossSellingRate=period/currentGrossSellingRate*100;

        if(quotaInGrossSellingRate>60){
            println("期间费用率与毛利率的比率:"+quotaInGrossSellingRate+"大于60%，需要淘汰", ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.RATIO_OF_PERIOD_EXPENSE_RATIO_TO_GROSS_PROFIT_MARGIN,new ExportData(quotaInGrossSellingRate,ColorType.RED));
        }else  if(quotaInGrossSellingRate>40){
            println("期间费用率与毛利率的比率:"+quotaInGrossSellingRate, ColorType.YELLOW);
            dataMap.put(AnalzeLiabilityExcelConstants.RATIO_OF_PERIOD_EXPENSE_RATIO_TO_GROSS_PROFIT_MARGIN,new ExportData(quotaInGrossSellingRate,ColorType.YELLOW));
        }else {
            println("期间费用率与毛利率的比率:"+quotaInGrossSellingRate);
            dataMap.put(AnalzeLiabilityExcelConstants.RATIO_OF_PERIOD_EXPENSE_RATIO_TO_GROSS_PROFIT_MARGIN,new ExportData(quotaInGrossSellingRate));
        }
        //销售费率
        double saleFeeV=salesFee/totalRevenue*100;

        if(saleFeeV<15){
            println("销售费率(%):"+saleFeeV+",产品比较容易销售");
            dataMap.put(AnalzeLiabilityExcelConstants.SALES_RATE,new ExportData(saleFeeV));
        }else if(saleFeeV<30){
            println("销售费率(%):"+saleFeeV+"，产品可以销售");
            dataMap.put(AnalzeLiabilityExcelConstants.SALES_RATE,new ExportData(saleFeeV,ColorType.YELLOW));
        }else {
            println("销售费率(%):"+saleFeeV+"，产品很难销售，淘汰", ColorType.RED);
            dataMap.put(AnalzeLiabilityExcelConstants.SALES_RATE,new ExportData(saleFeeV,ColorType.RED));
        }
        return dataMap;
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
    public static AnnualReport<Interest> getInterest(String symbol, String type, int count) throws IOException, URISyntaxException{
        if(count<2){
            throw new RuntimeException("查询年数不能低于2年");
        }
        String mainUrl="https://xueqiu.com";
        String url="https://stock.xueqiu.com/v5/stock/finance/cn/income.json?symbol="+symbol+"&type="+type+"&is_detail=true&count="+count+"&timestamp=";
        HttpUtil.get(mainUrl,null);
        AnnualReport<Interest> s = HttpUtil.get(url,new TypeReference<AnnualReport<Interest>>(){});
        System.out.println(s);
        if(s!=null){
            DataCheckNullAndAssigmentUtils.assigmentAssetsLiability(s.getList());
        }
        return s;
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
    public static AnnualReport<Quota> getQuota(String symbol, String type, int count) throws IOException, URISyntaxException{
        if(count<2){
            throw new RuntimeException("查询年数不能低于2年");
        }
        String url="https://stock.xueqiu.com/v5/stock/finance/cn/indicator.json?symbol="+symbol+"&type="+type+"&is_detail=true&count="+count+"&timestamp=";
        AnnualReport<Quota> s = HttpUtil.get(url,new TypeReference<AnnualReport<Quota>>(){});
        System.out.println(s);
        if(s!=null){
            DataCheckNullAndAssigmentUtils.assigmentAssetsLiability(s.getList());
        }
        return s;
    }
}

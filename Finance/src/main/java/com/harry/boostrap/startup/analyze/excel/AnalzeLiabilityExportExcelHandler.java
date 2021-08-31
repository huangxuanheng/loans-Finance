package com.harry.boostrap.startup.analyze.excel;

import org.assertj.core.util.Lists;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability.defaultAnalzeLiability;

/**
 * @author Harry
 * @date 2021/1/16
 * @des 描述：
 */
public class AnalzeLiabilityExportExcelHandler {


    public static void createExcel(String symbol) throws IOException, URISyntaxException {
        List<ExportData> headers = getExportHeaders();

        AnalzeLiabilityExportExcel analzeLiabilityExportExcel = defaultAnalzeLiability(symbol);
        ExportCommonHelper.export(analzeLiabilityExportExcel.getQuoteStr(),headers,analzeLiabilityExportExcel.getDataMap(),false);
    }

    public static void createExcel(String sheetName,List<String> symbols) throws IOException, URISyntaxException {
        List<ExportData> headers = getExportHeaders();
        List<AnalzeLiabilityExportExcel>exportExcels=new ArrayList<>();
        for (String symbol:symbols){
            exportExcels.add(defaultAnalzeLiability(symbol));
        }
//        ExportCommonHelper.export(sheetName,headers,exportExcels);
        ExportCommonHelper.exportVertical(sheetName,headers,exportExcels);
    }

    public static void createExcelVertical(String symbol) throws IOException, URISyntaxException {
        List<ExportData> headers = getExportHeaders();

        AnalzeLiabilityExportExcel analzeLiabilityExportExcel = defaultAnalzeLiability(symbol);
        ExportCommonHelper.exportVertical(analzeLiabilityExportExcel.getCompanyName(),headers, Lists.newArrayList(analzeLiabilityExportExcel));
    }

    private static List<ExportData> getExportHeaders() {
        List<ExportData> headers=new ArrayList<>();
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.DATE,"报告日期",null));
//        headers.add(new ExportData(AnalzeLiabilityExcelConstants.PERIOD_EXPENSE_RATE,"期间费用率(%)","看期间费用率，了解公司的成本管控能力."));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.GROSS_PROFIT_MARGIN,"销售毛利率(%)","毛利率主要看两点，数值和波幅。毛利率小于 40%或波动幅度大于 20%的公司淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.RATIO_OF_PERIOD_EXPENSE_RATIO_TO_GROSS_PROFIT_MARGIN,"期间费用率与毛利率的比率(%)","看期间费用率，了解公司的成本管控能力.\n优秀公司的期间费用率与毛利率的比率一般小于 40%。\n期间费用率与毛利率的比率大于 60%的公司淘汰掉。"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.SALES_RATE,"销售费用率(%)","看销售费用率，了解公司产品的销售难易度.\n销售费用率主要看两点，数值和变动趋势。一般来说，销售费用率小于 15%的公司，其产品比较容易销售，销售风险相对较小。\n销售费用率大于 30%的公司，其产品销售难度大，销售风险也大。销售费用率大于 30%的公司淘汰掉。"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.NET_PROFIT_CASH_CONTENT,"净利润含金量(%)","看净利润，了解公司的经营成果及含金量。净利润主要看净利润含金量。\n过去 5 年的平均净利润现金比率小于 100%的公司，淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.NCF_FROM_OA,"经营活动产生的现金流量净额","经营活动产生的现金流量净额首先必须大于0，经营活动产生的现金流量净额小于0的公司没有造血能力，直接淘汰"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.NCF_FROM_OA_V,"经营活动产生的现金流量净额增长率(%)","经营活动产生的现金流量净额的增长率要大于0，如果小于0，则意味着公司的造血能力在下降"));


        headers.add(new ExportData(AnalzeLiabilityExcelConstants.NET_CASH_FLOW_RATIO,"现金流量净额比率(%)","看购买固定资产、无形资产和其他长期资产支付的现金，了解公司的增长潜力。\n购建固定资产、无形资产和其他长期资产支付的现金与经营活动产生的现金流量净额的比率大于 100%\n或持续小于 3%的公司需要淘汰掉。这两种类型的公司前者风险较大，后者回报较低"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.CASH_DIVIDEND_RATIO,"现金分红比例(%)","看分配股利、利润或偿付利息支付的现金，了解公司的现金分红情况.\n分配股利、利润或偿付利息支付的现金与经营活动产生的现金流量净额的比率最好在 20%-70%之间，\n比率小于 20%不够厚道，大于 70%难以持续"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.COMPANY_NET_CASH_FLOW_FOR_THE_YEAR_TYPE,"公司当年现金流量净额所属类型","经营活动产生的现金流量净额、投资活动产生的现金流量净额、筹资活动产生的现金流量净额，并判断公司当年所属类型: 优秀的公司一般都是“正负负”和“正正负”型"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.FINAL_BALANCE_OF_CCE,"期末现金及现金等价物余额","最近五年年报数据汇总，一般钱越多的公司，抗风险能力越强，进行现金分红的能力也越强"));

        headers.add(new ExportData(AnalzeLiabilityExcelConstants.FLOATING_VALUE_OF_GROSS_PROFIT_MARGIN,"毛利率浮动值(%)","毛利率主要看两点，数值和波幅。毛利率小于 40%或波动幅度大于 20%的公司淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.GROWTH_RATE_OF_TOTAL_ASSETS,"总资产增长率(%)","看总资产，了解公司的实力和成长性"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.LIABILITY,"负债率(%)","负债主要看两点，一是资产负债率，二是准货币资金减有息负债的差额;\n资产负债率大于 60%的公司，淘汰；准货币资金减有息负债小于 0 的公司，淘汰"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.QUASI_MONETARY_FUND,"准货币基金",null));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.INTEREST_BEARING_LIABILITIES,"有息负债",null));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.SOLVENCY,"偿债能力","准货币资金减有息负债小于 0 的公司，淘汰"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.COMPANY_COMPETITIVENESS,"企业竞争力(%)","差额越大，公司的竞争优势越强；差额小于 0，公司的竞争力弱，淘汰掉。\n差额=（应付票据+应付账款+预收款项+合同负债）-（应收票据+应收账款+应收款项融资+预付款项+合同资产）"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.PRODUCT_COMPETITIVENESS,"产品竞争力(%)","最优秀的公司（应收账款+合同资产）占总资产的比率小于 1%，优秀的公司一般小于 3%。\n（应收账款+合同资产）占总资产的比率大于 15%的公司需要淘汰掉。"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.MAINTAIN_COMPETITIVENESS,"维持竞争力(%)","（固定资产+在建工程）与总资产的比率大于 40%的公司为重资产型公司。重资产型公司保持竞争力的\n成本比较高，风险比较大。另外（固定资产+在建工程）与总资产的比率保持稳定或持续下降的公司风险较小，比率短期内增幅较大的公司财务造假的可能性较大,低于 20%，属于非常轻的轻资产型公司，风险很小。"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.CONCENTRATION,"专注度(%)","投资类资产占总资产的比率大于 10%的公司不够专注，需要淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.STOCK_HUNDER_RISK,"存货暴雷风险(%)","“应付预收”减“应收预付”的差额大于 0 并且应收账款占总资产比率小于 1%的公司，存货基本没有\n" +
                "爆雷的风险。应收账款占总资产的比率大于 5%并且存货占总资产的比率大于 15%的公司，爆雷的风险比较大，需要淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.GOODS_HUNDER_RISK,"商誉暴雷风险(%)","商誉占总资产的比率超过 10%的公司，爆雷风险较大，需要淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.ROE,"净资产收益率ROE(%)","看归母净利润，了解公司的整体盈利能力及持续性.\n最优秀公司的 ROE 一般会持续大于 20%，优秀公司的 ROE 也会持续大于 15%。ROE 小于 15%的公司.\n需要淘汰掉。另外归母净利润增长率持续小于 10%的公司也要淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.NET_PROFIT_GROWTH_RATE,"净利润增长率(%)","归母净利润增长率持续小于 10%的公司也要淘汰掉"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.OP_RATE,"营业利润率(%)","营业利润率=营业利润/营业收入*100%，看企业的成长能力，连续五年大于20%"));
        headers.add(new ExportData(AnalzeLiabilityExcelConstants.TOTAL_REVENUE_RATE,"营业收入增长率(%)","营业收入主要看两点，规模和增长率。营业收入的规模越大越好，增长率最好要大于 10%，越高越好"));
        return headers;
    }
}

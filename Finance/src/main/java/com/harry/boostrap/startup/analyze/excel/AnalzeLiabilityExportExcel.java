package com.harry.boostrap.startup.analyze.excel;

import java.util.Date;
import lombok.Data;

import java.util.List;
import java.util.Map;
import org.apache.http.client.utils.DateUtils;

/**
 * @author Harry
 * @date 2021/1/16
 * @des 描述：
 */
@Data
public class AnalzeLiabilityExportExcel {

    /**
     * 财务报表数据
     */
    private List<Map<String, ExportData>>dataMap;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 当前行情
     */
    private Map<String, ExportData> quote;

    /**
     * 获取当前行情
     * @return
     */
    public  String getQuoteStr() {
        return DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 报告"+companyName+"："+symbol+",当前股价："+quote.get(AnalzeLiabilityExcelConstants.CURRENT).getValue()+"，当日成交额："
                +String.format("%.2f", quote.get(AnalzeLiabilityExcelConstants.AMOUNT).getValue())+"亿,流通市值："
                +String.format("%.2f", quote.get(AnalzeLiabilityExcelConstants.FLOAT_MARKET_CAPITAL).getValue())+"亿，\n动态市盈率（TTM）："
                +quote.get(AnalzeLiabilityExcelConstants.PE_TTM).getValue()+"，股息率（TTM）:"
                +quote.get(AnalzeLiabilityExcelConstants.DIVIDEND_YIELD).getValue()
                +",股息："+String.format("%.2f", quote.get(AnalzeLiabilityExcelConstants.DIVIDEND).getValue())
                +",动态市盈率好价格："+String.format("%.2f", (15*(double)(quote.get(AnalzeLiabilityExcelConstants.CURRENT).getValue())/((double)quote.get(AnalzeLiabilityExcelConstants.PE_TTM).getValue())))
                +",股息率好价格："+String.format("%.2f", ((double)quote.get(AnalzeLiabilityExcelConstants.DIVIDEND).getValue()/3.186*100))
                ;
    }
}

package com.harry.boostrap.startup.analyze.enterprise.quote;

import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExcelConstants;
import com.harry.boostrap.startup.analyze.excel.ExportData;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability.println;
import static com.harry.boostrap.startup.analyze.excel.ColorType.*;


/**
 * @author Harry
 * @date 2021/1/16
 * @des 描述：引用数据，当前行情
 */
public class QuoteHandler {

    public static void main(String[] args) throws IOException, URISyntaxException {
        handlerQuote("SH600036");
    }

    public static Map<String, ExportData> handlerQuote(String symbol) throws IOException, URISyntaxException {
        Map<String, ExportData> quoteMap=new HashMap<>();
        Quote quote = getQuote(symbol);
        if(quote.getCurrent()!=null){
            println("当前价格："+quote.getCurrent()+"");
            quoteMap.put(AnalzeLiabilityExcelConstants.CURRENT,new ExportData(quote.getCurrent()));
        }
        float unit=100000000;
        if(quote.getAmount()!=null){
            double v = quote.getAmount() / unit;

            if(v>30){
                println("当日成交额："+v+"亿");
                quoteMap.put(AnalzeLiabilityExcelConstants.AMOUNT,new ExportData(v));
            }else if(v>8){
                println("当日成交额："+v+"亿",GREEN);
                quoteMap.put(AnalzeLiabilityExcelConstants.AMOUNT,new ExportData(v,GREEN));
            }else if(v>5){
                println("当日成交额："+v+"亿",YELLOW);
                quoteMap.put(AnalzeLiabilityExcelConstants.AMOUNT,new ExportData(v,YELLOW));
            }else{
                println("当日成交额："+v+"亿",RED);
                quoteMap.put(AnalzeLiabilityExcelConstants.AMOUNT,new ExportData(v,RED));
            }

        }
        if(quote.getFloat_market_capital()!=null){
            double v = quote.getFloat_market_capital() / unit;

            if(v>1000){
                println("流通市值："+v+"亿");
                quoteMap.put(AnalzeLiabilityExcelConstants.FLOAT_MARKET_CAPITAL,new ExportData(v));
            }else if(v>100){
                println("流通市值："+v+"亿",YELLOW);
                quoteMap.put(AnalzeLiabilityExcelConstants.FLOAT_MARKET_CAPITAL,new ExportData(v,YELLOW));
            }else{
                println("流通市值："+v+"亿",RED);
                quoteMap.put(AnalzeLiabilityExcelConstants.FLOAT_MARKET_CAPITAL,new ExportData(v,RED));
            }
        }
        if(quote.getPe_ttm()!=null){

            if(quote.getPe_ttm()>30){
                println("动态市盈率（TTM）:"+quote.getPe_ttm(),RED);
                quoteMap.put(AnalzeLiabilityExcelConstants.PE_TTM,new ExportData(quote.getPe_ttm(),RED));
            }else if(quote.getPe_ttm()>20){
                println("动态市盈率（TTM）:"+quote.getPe_ttm(),YELLOW);
                quoteMap.put(AnalzeLiabilityExcelConstants.PE_TTM,new ExportData(quote.getPe_ttm(),YELLOW));
            }else{
                println("动态市盈率（TTM）:"+quote.getPe_ttm());
                quoteMap.put(AnalzeLiabilityExcelConstants.PE_TTM,new ExportData(quote.getPe_ttm()));
            }
        }

        if(quote.getDividend_yield()!=null){

            if(quote.getDividend_yield()>30){
                println("股息率（TTM）:"+quote.getDividend_yield()+"%",RED);
                quoteMap.put(AnalzeLiabilityExcelConstants.DIVIDEND_YIELD,new ExportData(quote.getDividend_yield(),RED));
            }else if(quote.getPe_ttm()>20){
                println("股息率（TTM）:"+quote.getDividend_yield()+"%",YELLOW);
                quoteMap.put(AnalzeLiabilityExcelConstants.DIVIDEND_YIELD,new ExportData(quote.getDividend_yield(),YELLOW));
            }else{
                println("股息率（TTM）:"+quote.getDividend_yield()+"%");
                quoteMap.put(AnalzeLiabilityExcelConstants.DIVIDEND_YIELD,new ExportData(quote.getDividend_yield()));
            }
        }
        quoteMap.put(AnalzeLiabilityExcelConstants.DIVIDEND,new ExportData(quote.getDividend()));
        return quoteMap;
    }

    /**
     * 获取股票年报
     * @param symbol 股票代码，上证所:SH+股票代码，深：SZ+股票代码
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Quote getQuote(String symbol) throws IOException, URISyntaxException{
        String mainUrl="https://xueqiu.com";
        String url="https://stock.xueqiu.com/v5/stock/quote.json?symbol="+symbol+"&extend=detail";
        HttpUtil.get(mainUrl,null);
        Quote quote = HttpUtil.getObject(url, "quote", Quote.class);
        return quote;
    }
}

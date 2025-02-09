package com.harry.boostrap.startup.gold;

import com.alibaba.fastjson.JSON;
import com.harry.boostrap.startup.analyze.utils.HtmlUtils;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExponentETFServiceTest extends TestCase {

    @Autowired
    private ExponentETFService exponentETFService;

    @Autowired
    private HtmlUtils htmlUtils;
    @Test
    public void testGetDayAvgPriceInfos() {
        ExponentETFService exponentETFService=new ExponentETFService();
        List<DayAvgPriceInfo> sh510300 = exponentETFService.getDayAvgPriceInfos("510300.json");
        List<DayAvgPriceInfo> golds = exponentETFService.getDayAvgPriceInfos("518880.json");

        Map<String, Double> sh510300Map = sh510300.stream().collect(Collectors.toMap(DayAvgPriceInfo::getDate, DayAvgPriceInfo::getAvgPrice));
        Map<String, Double> goldMap = golds.stream().collect(Collectors.toMap(DayAvgPriceInfo::getDate, DayAvgPriceInfo::getAvgPrice));
        List<String>dates=new ArrayList<>();
        List<Double>prices1=new ArrayList<>();
        List<Double>prices2=new ArrayList<>();
        List<Double>prices3=new ArrayList<>();
        //股票价格/黄金价格比值
        String stockName1="股票价格/黄金价格比值";
        // 股票价格
        String stockName2;
        //黄金价格
        String stockName3;
        //谁的数据少，以谁为基础轮训
        if(golds.size()<sh510300.size()){
            //黄金的数据少
            for (DayAvgPriceInfo dayAvgPriceInfo:golds){
                Double price = sh510300Map.get(dayAvgPriceInfo.getDate());
                //指数基金价格/黄金价格
                double b = price.doubleValue() / dayAvgPriceInfo.getAvgPrice();
                dates.add(dayAvgPriceInfo.getDate());
                prices1.add(b);
                prices2.add(price);
                prices3.add(dayAvgPriceInfo.getAvgPrice());
            }

            stockName2="股票价格";
            stockName3="黄金价格";
        }else {
            for (DayAvgPriceInfo dayAvgPriceInfo:sh510300){
                //黄金价格
                Double price = goldMap.get(dayAvgPriceInfo.getDate());
                //指数基金价格/黄金价格
                double b = dayAvgPriceInfo.getAvgPrice() / price.doubleValue();
                dates.add(dayAvgPriceInfo.getDate());
                prices1.add(b);

                prices2.add(price);
                prices3.add(dayAvgPriceInfo.getAvgPrice());
            }
            stockName2="黄金价格";
            stockName3="股票价格";
        }

        htmlUtils.createHtmlFile2("股票走势图.html"
                ,JSON.toJSONString(dates),JSON.toJSONString(prices1),JSON.toJSONString(prices2),JSON.toJSONString(prices3)
                ,JSON.toJSONString(stockName1),JSON.toJSONString(stockName2),JSON.toJSONString(stockName3));
    }
}
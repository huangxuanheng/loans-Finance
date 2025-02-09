package com.harry.boostrap.startup.gold;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class GoldETFAndExponentETFBuyAnalyseTest {
    public static void main(String[] args) {
        ExponentETFService exponentETFService=new ExponentETFService();
        List<DayAvgPriceInfo> sh510300 = exponentETFService.getDayAvgPriceInfos("510300.json");
        System.out.println(JSON.toJSONString(sh510300));
    }
}

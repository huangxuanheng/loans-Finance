package com.harry.boostrap.startup.analyze.enterprise.cash;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import java.util.Date;

/**
 * @Description: 分红业务
 * @Author: harry
 * @CreateTime: 2023/10/20
 */
public class DividendService {

    /**
     * 取最近一次分红日期
     * @param symbol
     * @return
     */
    public static Date getDividendDate(String symbol){

        String url="https://stock.xueqiu.com/v5/stock/f10/cn/bonus.json?symbol="+symbol+"&size=1&page=1&extend=true";
        try {
            HttpUtil.get("https://xueqiu.com",null);
            JSONArray object = HttpUtil.getJSONArray(url, "items");
            if(object.isEmpty()){
                return null;
            }
            JSONObject item= (JSONObject) object.get(0);
            return item.getDate("dividend_date");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}

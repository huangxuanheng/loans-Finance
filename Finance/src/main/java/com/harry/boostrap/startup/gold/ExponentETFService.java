package com.harry.boostrap.startup.gold;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harry.boostrap.startup.bank.DateUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExponentETFService {
    /**
     * 获取指数基金历史平均值
     * @param symbol 指数代码
     * @return
     */
    public List<DayAvgPriceInfo> getDayAvgPriceInfos(String symbol){


        List<DayAvgPriceInfo>dayAvgPriceInfos=new ArrayList<>();
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(symbol);
            byte[]buf=new byte[1024];
            int len=-1;
            StringBuilder sb=new StringBuilder();
            while ((len=resourceAsStream.read(buf))!=-1){
                sb.append(new String(buf,0,len));
            }
            JSONObject jsonObject=JSON.parseObject(sb.toString());
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray item= data.getJSONArray("item");
            for (int x=0;x<item.size();x++){
                JSONArray jsonArray = item.getJSONArray(x);
                Date date = jsonArray.getDate(0);
                double max = jsonArray.getDoubleValue(3);
                double min = jsonArray.getDoubleValue(4);
                double avg=(max+min)/2.0;

//                SimpleDateFormat dateFormat=new SimpleDateFormat();
                DayAvgPriceInfo dayAvgPriceInfo=new DayAvgPriceInfo();
                dayAvgPriceInfo.setDate(DateUtils.formatDateToStr(date));
                dayAvgPriceInfo.setAvgPrice(avg);
                dayAvgPriceInfos.add(dayAvgPriceInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayAvgPriceInfos;
    }
}

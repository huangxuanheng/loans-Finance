package com.harry.boostrap.startup.analyze.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：
 */
public class DataCheckNullAndAssigmentUtils {
    public static <T>void assignmentAssetsLiability(List<T> list) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (T assetsLiability:list){
            Class<?> aClass = assetsLiability.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field:declaredFields){
                field.setAccessible(true);
                try {
                    Object o = field.get(assetsLiability);
                    if(o instanceof List){
                        List<Double>doubleList= (List<Double>) o;
                        Double aDouble = doubleList.get(0);
                        if(aDouble==null){
                            doubleList.set(0,0d);
                        }
                        Double aDouble1 = doubleList.get(1);
                        if(aDouble1==null){
                            doubleList.set(1,0d);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Map<String,Object> assignment(String prefix,Object obj) {
        String s = JSON.toJSONString(obj);
        JSONObject jsonObject = JSON.parseObject(s);
        Map<String,Object>param=new HashMap<>();
        jsonObject.entrySet().stream().forEach(stringObjectEntry -> {
            if(stringObjectEntry.getValue() instanceof List){
                List<Object>value= (List<Object>) stringObjectEntry.getValue();
                Object od = value.get(0);
                Double d;
                if(od instanceof Double){
                    d= (Double) od;
                }else {
                    BigDecimal bd= (BigDecimal) od;
                    d= bd.doubleValue();
                }

                double dd=d.doubleValue()/100000000.0;
                String str="";
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                if(Math.abs(dd*100)>1){
                    str=decimalFormat.format(dd)+"亿";
                }else if(dd!=0){
                    str=decimalFormat.format(d.doubleValue()/10000.0) +"万";
                }
                param.put(prefix+stringObjectEntry.getKey(),str);
            }

        });
        return param;
    }
}

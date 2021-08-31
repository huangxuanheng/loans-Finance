package com.harry.boostrap.startup.analyze.utils;

import com.harry.boostrap.startup.analyze.enterprise.liability.AssetsLiability;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：
 */
public class DataCheckNullAndAssigmentUtils {
    public static <T>void assigmentAssetsLiability(List<T> list) {
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
}

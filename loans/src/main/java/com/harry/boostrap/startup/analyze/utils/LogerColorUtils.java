package com.harry.boostrap.startup.analyze.utils;

import com.harry.boostrap.startup.analyze.excel.ColorType;

/**
 * author harry
 * email:xianheng.huang@lolaage.com
 * createDate:2018/9/11
 */
public class LogerColorUtils {

    /**
     * 给指定的文本添加颜色，打印到控制台
     * @param content 需要突出显示颜色的文字内容
     * @param colrType 颜色类型
     * @return
     */
    public static String getColorFormat(Object content, ColorType colrType){
        StringBuilder format=new StringBuilder();
        format.append("\033[");
        format.append(colrType.getValue());
        format.append("m");
        format.append(content);
        format.append("\033[0m");
        return format.toString();
    }


}

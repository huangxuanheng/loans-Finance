package com.harry.boostrap.startup.gold;

import lombok.Data;

import java.util.Date;

@Data
public class DayAvgPriceInfo {
    /**
     * 日期
     */
    private String date;
    /**
     * 平均价格
     */
    private double avgPrice;
}

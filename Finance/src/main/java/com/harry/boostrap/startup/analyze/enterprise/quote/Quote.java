package com.harry.boostrap.startup.analyze.enterprise.quote;

import lombok.Data;

/**
 * @author Harry
 * @date 2021/1/16
 * @des 描述：引用数据，当天的最高，最低，市盈率，成交额等
 */
@Data
public class Quote {
    /**
     * 成交额
     */
    private Double amount;
    /**
     * 当前股票价格
     */
    private Double current;
    /**
     * 当日最高
     */
    private Double high;
    /**
     * 当日最低
     */
    private Double low;
    /**
     * 昨日收盘价
     */
    private Double last_close;
    /**
     * 市盈率(TTM)
     */
    private Double pe_ttm;
    /**
     * 	股息率(TTM)
     */
    private Double dividend_yield;
    /**
     * 股息(TTM)
     */
    private Double dividend;
    /**
     * 每股收益
     */
    private Double eps;
    /**
     * 每股净资产
     */
    private Double navps;
    /**
     * 流通市值
     */
    private Double float_market_capital;
    /**
     * 总市值
     */
    private Double market_capital;
    /**
     * 货币单位
     */
    private String currency;
    /**
     * 股票名称
     */
    private String name;


}

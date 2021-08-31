package com.harry.boostrap.startup.analyze.enterprise.liability;

import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：年报
 */
@Data
public class AnnualReport<T> {
    /**
     * 币种类型
     */
    private String currency;
    /**
     * 币种类型名称
     */
    private String currency_name;
    /**
     * 最后季报名称
     */
    private String last_report_name;
    /**
     * 报价名称
     */
    private String quote_name;
    /**
     * 负债数据集合
     */
    private List<T> list;
}

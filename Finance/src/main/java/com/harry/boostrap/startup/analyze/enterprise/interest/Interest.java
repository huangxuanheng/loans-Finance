package com.harry.boostrap.startup.analyze.enterprise.interest;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：利润表
 */
@Data
public class Interest extends BaseEntity {
    /**
     * 营业总收入
     */
    private List<Double>total_revenue;
    /**
     * 销售费用
     */
    private List<Double>sales_fee;
    /**
     * 研发费用
     */
    private List<Double>rad_cost;
    /**
     * 利润总额
     */
    private List<Double>profit_total_amt;
    /**
     * 营业总成本
     */
    private List<Double>operating_costs;
    /**
     * 营业成本
     */
    private List<Double>operating_cost;
    /**
     * 营业利润
     */
    private List<Double>op;
    /**
     * 净利润
     */
    private List<Double>net_profit;
    /**
     * 管理费用
     */
    private List<Double>manage_fee;
    /**
     * 财务费用
     */
    private List<Double>financing_expenses;
    /**
     * 归属于母公司所有者的净利润
     */
    private List<Double>net_profit_atsopc;

}

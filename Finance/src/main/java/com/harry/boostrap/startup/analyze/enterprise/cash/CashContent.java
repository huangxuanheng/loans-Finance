package com.harry.boostrap.startup.analyze.enterprise.cash;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：现金含量
 */
@Data
public class CashContent extends BaseEntity {
    /**
     * 经营活动产生的现金流量净额
     */
    private List<Double>ncf_from_oa;
    /**
     * 购建固定资产、无形资产和其他长期资产支付的现金
     */
    private List<Double>cash_paid_for_assets;
    /**
     * 分配股利、利润或偿付利息支付的现金
     */
    private List<Double>cash_paid_of_distribution;
    /**
     * 现金及现金等价物净增加额
     */
    private List<Double>net_increase_in_cce;
    /**
     * 投资活动产生的现金流量净额
     */
    private List<Double>ncf_from_ia;
    /**
     * 筹资活动产生的现金流量净额
     */
    private List<Double>ncf_from_fa;
    /**
     * 期末现金及现金等价物余额
     */
    private List<Double>final_balance_of_cce;

}

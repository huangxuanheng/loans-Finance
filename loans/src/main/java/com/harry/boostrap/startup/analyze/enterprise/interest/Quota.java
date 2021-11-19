package com.harry.boostrap.startup.analyze.enterprise.interest;

import com.harry.boostrap.startup.analyze.enterprise.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author Harry
 * @date 2021/1/3
 * @des 描述：主要指标
 */
@Data
public class Quota extends BaseEntity {
    /**
     * 资产负债率
     */
    private List<Double> asset_liab_ratio;
    /**
     * 毛利率
     */
    private List<Double> gross_selling_rate;
    /**
     * 营业收入同比增长
     */
    private List<Double> operating_income_yoy;
}

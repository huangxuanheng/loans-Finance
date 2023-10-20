package com.harry.boostrap.startup.analyze.enterprise.quote;

import com.alibaba.fastjson.JSONObject;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 中国10年国债收益率
 * @Author: harry
 * @CreateTime: 2023/10/20
 */
public class TenYearTreasuryBondYield {

    /**
     * 获取中国十年国债收益率
     * {
     *     "data": {
     *         "snapshot": {
     *             "CN10YR.OTC": [
     *                 2.745,
     *                 0
     *             ]
     *         }
     *     }
     * }
     * @return
     */
    public static double getBondYield(){
        String url="https://api-ddc-wscn.awtmt.com/market/real?fields=last_px&prod_code=CN10YR.OTC";
        try {
            JSONObject object = HttpUtil.getObject(url, "snapshot", JSONObject.class);
            List<BigDecimal> o = (List<BigDecimal>) object.get("CN10YR.OTC");
            return o.get(0).doubleValue();
        } catch (IOException e) {
            System.out.println(e);
        }
        return 0;
    }

}

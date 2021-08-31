package com.harry.boostrap.startup.analyze.enterprise;

import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import lombok.Data;

/**
 * @author Harry
 * @date 2021/1/2
 * @des 描述：
 */
@Data
public class ResponseData {
    private String error_code;
    /**
     * 年报
     */
    private AnnualReport data;
}

package com.authine.bank;

/**
 * @author Harry
 * @date 2020/8/22
 * @des 描述：贷款类型
 */
public enum LoanType {
    EQUAL_AMOUT_PRINCIPAL_AND_INTEREST("等额本息"),
    EQUAL_AMOUT_PRINCIPAL("等额本金"),
    EQUAL_AMOUT_INTEREST("等本等息"),
    INTEREST_FIRST("先息后本"),
    ;
    private String msg;
    LoanType(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }
}

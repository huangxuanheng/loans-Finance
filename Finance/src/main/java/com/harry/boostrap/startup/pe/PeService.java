package com.harry.boostrap.startup.pe;

import com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import com.harry.boostrap.startup.config.WarnFundConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 市盈率业务
 */
@Service
@Slf4j
public class PeService {
    @Autowired
    private WarnFundConfig warnFundConfig;
    /**
     * 获取指数基金估值
     * @param symbol 指数代码
     * @param type 类型，取值：all-10年，3y-3年，5y-5年
     * @return
     */
    private Map<String, Double> getFundPe(String symbol,String type){
        String url="https://danjuanfunds.com/djapi/index_eva/pe_history/"+symbol+"?day="+type;
        Map<String, Double> fundPe = null;
        try {
            fundPe = HttpUtil.getFundPe(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fundPe;
    }


    public void scheduleCheckLowPeAndSendMsg(){
        Map<String, String> symbols = warnFundConfig.getSymbols();
//        symbols.forEach((symbol,name)->{
//            Map<String, Double> fundPe = getFundPe(AnalzeLiability.getSymbol(symbol), "3y");
//            //当前指数市盈率
//            Double pe = fundPe.get("pe");
//            //当前市盈率低估参考范围最低值，该值会跟着type不同值不同
//            Double lv = fundPe.get("lv");
//            if(lv.doubleValue()>pe.doubleValue()){
//                sendMsg(symbol,name,pe,lv);
//            }
//        });
    }

    /**
     * 发送消息
     * @param symbol
     * @param name
     * @param pe
     * @param lv
     */
    private void sendMsg(String symbol, String name, Double pe, Double lv) {
        log.info("发送消息通知已经触底最低估了");
    }
}

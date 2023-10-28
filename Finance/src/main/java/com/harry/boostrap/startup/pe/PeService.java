package com.harry.boostrap.startup.pe;

import com.harry.boostrap.startup.analyze.enterprise.cash.DividendService;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability;
import com.harry.boostrap.startup.analyze.enterprise.quote.Quote;
import com.harry.boostrap.startup.analyze.enterprise.quote.QuoteHandler;
import com.harry.boostrap.startup.analyze.enterprise.quote.TenYearTreasuryBondYield;
import com.harry.boostrap.startup.analyze.utils.EmailHelper;
import com.harry.boostrap.startup.analyze.utils.HttpUtil;
import com.harry.boostrap.startup.config.WarnFundConfig;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;

import com.harry.boostrap.startup.config.WarnStockConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

/**
 * 市盈率业务
 */
@Service
@Slf4j
public class PeService {
    @Autowired
    private WarnFundConfig warnFundConfig;
    @Autowired
    private WarnStockConfig warnStockConfig;
    @Autowired
    private EmailHelper emailHelper;
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

    @Scheduled(cron = "0 38 14 * * ?")
    public void scheduleCheckLowPeAndSendMsg(){
        //检查指数基金是否满足最低低估值并发送预警买入提醒
        checkFundPe();
        //检查股票价是否满足好价格买入时机并提醒
        checkStockPe();
    }

    private void checkStockPe() {
        Map<String, String> symbols = warnStockConfig.getSymbols();
        symbols.forEach((symbol,configStr)->{
            try {
                checkEchStockPe(AnalzeLiability.getSymbol(symbol), configStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //计算每一个股票好价格
    private void checkEchStockPe(String symbol, String configStr) throws IOException, URISyntaxException {
        log.info("{}-代码:{}", configStr, symbol);
        Quote quote = QuoteHandler.getQuote(symbol);
        //股息
        Double dividend = quote.getDividend();
        //10年国债收益率
        double bondYield = TenYearTreasuryBondYield.getBondYield();
        Date dividendDate = DividendService.getDividendDate(symbol);
        //市盈率好价格
        double peTtmGoodPrice = quote.getCurrent() / quote.getPe_ttm() * 15;
        double d=0;
        double dividendGoodPrice=0;
        if(dividendDate !=null){
            long l = new Date().getTime() - dividendDate.getTime();
            //股息率
            if(l<0&&Math.abs(l)<30*60*60*24){
                //扣减税后股息，据除息日还有不到30天，个人所得税20%
                d=(dividend - dividend * 0.2)/quote.getCurrent();
                //股息率好价格
                dividendGoodPrice = (dividend - dividend * 0.2) / bondYield * 100;
            }else{
                d=(dividend - dividend * 0.1)/quote.getCurrent();
                //股息率好价格
                dividendGoodPrice = (dividend - dividend * 0.1) / bondYield * 100;
            }
        }

        double min = Math.min(peTtmGoodPrice, dividendGoodPrice);
        if(quote.getCurrent().doubleValue()<min){
            sendMsg(symbol,quote.getName(),quote.getPe_ttm(),d,quote.getDividend_yield(),min, quote.getCurrent(),configStr);
        }
    }

    private void checkFundPe() {
        Map<String, String> symbols = warnFundConfig.getSymbols();
        symbols.forEach((symbol,name)->{
            log.info("{}-代码:{}",name,symbol);
            Map<String, Double> fundPe = getFundPe("SH"+symbol, "3y");
            //当前指数市盈率
            Double pe = fundPe.get("pe");
            //当前市盈率低估参考范围最低值，该值会跟着type不同值不同
            Double lv = fundPe.get("lv");
            log.info("{}-代码:{},当前市盈率是{},当前低估值是{}",name,symbol,pe,lv);
            if(lv.doubleValue()>pe.doubleValue()){
                sendMsg(symbol,name,pe,lv);
            }
        });
    }

    /**
     * 发送低估消息
     * @param symbol
     * @param name
     * @param pe
     * @param lv
     */
    private void sendMsg(String symbol, String name, Double pe, Double lv) {
        log.info("发送消息通知已经触底最低估了");
        String content=name.concat(":").concat(symbol).concat(" 触底低估了\n").concat("当前市盈率：").concat(pe.toString()).concat(",触底参考：").concat(lv.toString());
        String title="市盈率低估触底提醒";
        try {
            emailHelper.sendEmail("503116108@qq.com",title,content);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送低估消息
     * @param symbol
     * @param name
     * @param pe
     * @param dividend_buy 买入股息率
     * @param dividend_r 持有股息率
     * @param goodPrice 好价格
     * @param current 当前价格
     * @param configStr 配置格式：股票名称,合理市盈率最低值，合理市盈率最高值，当动态市盈率高于或者低于合理市盈率时，可以操作买卖
     */
    private void sendMsg(String symbol, String name, Double pe, Double dividend_buy,Double dividend_r,double goodPrice,double current,String configStr) {
        log.info("发送消息通知已经触底最低估了");
        String[] split = configStr.split(",");

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        StringBuilder content=new StringBuilder().append(name).append("(").append(symbol).append(")\n\n").append(" 当前市盈率：").append(decimalFormat.format(pe))
                .append(",买入股息率:").append(decimalFormat.format(dividend_buy*100)).append("%").append(",持有股息率：").append(decimalFormat.format(dividend_r)).append("%\n\n")
                .append("当前好价格：").append(decimalFormat.format(goodPrice)).append(",当前股票价格：").append(current).append("小于好价格,可以适当买入！\n\n");
        if(split.length>0&&split.length==3){
            double peMin=Double.parseDouble(split[1]);
            double peMax=Double.parseDouble(split[2]);
            double peMid;
            if(current<peMin){
                String replace = warnStockConfig.getTemp().replace("${pe_min}", peMin + "").replace("${pe_max}", peMax + "");
                content.append(replace);
            }else if(current<(peMid=(peMin+peMax)/2)){
                String replace = warnStockConfig.getTemp().replace("${pe_min}", peMin + "").replace("${pe_max}", peMax + "").replace("${pe_mid}",peMid+"");
                content.append(replace);
            }
        }

        String title="股票["+name+"]出现好价格拉";
        try {
            emailHelper.sendEmail("503116108@qq.com",title,content.toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

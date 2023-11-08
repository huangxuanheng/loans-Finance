package com.harry.boostrap.startup.analyze;

import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnalzeLiabilityTestTest {

    @Autowired
    private AnalyzeService analyzeService;

    /**
     * 白酒
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void createHtml() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="002304";
        //同行公司股票代码
        String targetSymbol2="600519";
        String targetSymbol3="000858";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    @Test
    public void meiye() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="601088";
        //同行公司股票代码
        String targetSymbol2="601225";
        String targetSymbol3="601898";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    @Test
    public void bank() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600036";
        //同行公司股票代码
        String targetSymbol2="601166";
        String targetSymbol3="601166";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    @Test
    public void gangtie() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600507";
        //同行公司股票代码
        String targetSymbol2="000708";
        String targetSymbol3="000825";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 食品加工
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void meat() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="000895";
        //同行公司股票代码
        String targetSymbol2="603345";
        String targetSymbol3="600073";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 车身附件及饰件
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void car() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600660";
        //同行公司股票代码
        String targetSymbol2="600741";
        String targetSymbol3="002048";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 白色家电
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void baisejiadian() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="000333";
        //同行公司股票代码
        String targetSymbol2="000651";
        String targetSymbol3="600690";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 小家电
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void littleHome() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="002032";
        //同行公司股票代码
        String targetSymbol2="003023";
        String targetSymbol3="300272";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 烘焙食品
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void hongpeishipin() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="603886";
        //同行公司股票代码
        String targetSymbol2="603043";
        String targetSymbol3="603866";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 厨卫电器
     * 002677- 浙江美大，近3年合理市盈率是15.4-18，如果市盈率低于15.4时，可以定投买入，
     * 如果市盈率低于15时，可以翻倍买入。如果低于14时，再翻倍买入，以此类推
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void chuweidianqi() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="002677";
        //同行公司股票代码
        String targetSymbol2="002508";
        String targetSymbol3="002543";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 乳品
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void rupin() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600887";
        //同行公司股票代码
        String targetSymbol2="002732";
        String targetSymbol3="600429";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }
}
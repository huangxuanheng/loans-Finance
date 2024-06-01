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
        String targetSymbol3="601998";
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

    /**
     * 中药
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void zhongyao() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600566";
        //同行公司股票代码
        String targetSymbol2="000538";
        String targetSymbol3="600436";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 中药2
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void zhongyao2() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600750";
        //同行公司股票代码
        String targetSymbol2="000538";
        String targetSymbol3="600436";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 装修建材
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void wrapperBuilder() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="002372";
        //同行公司股票代码
        String targetSymbol2="002271";
        String targetSymbol3="000786";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 航运
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void hangyuan() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="603565";
        //同行公司股票代码
        String targetSymbol2="601919";
        String targetSymbol3="601866";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 调味发酵品
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void tiaoweifajiapin() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="603288";
        //同行公司股票代码
        String targetSymbol2="600872";
        String targetSymbol3="603027";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 其他生物制品
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void qitashengwuzhipin() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="600211";
        //同行公司股票代码
        String targetSymbol2="000661";
        String targetSymbol3="603087";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 新能源发电
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void newEnergyGeneration() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="601985";
        //同行公司股票代码
        String targetSymbol2="003816";
        String targetSymbol3="600905";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }

    /**
     * 光伏设备
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void guangfushebei() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="601012";
        //同行公司股票代码
        String targetSymbol2="600438";
        String targetSymbol3="300274";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }


    /**
     * 火电
     * @throws IOException
     * @throws URISyntaxException
     */
    @Test
    public void fire() throws IOException, URISyntaxException {
        String type = "Q4";
        //五年
        int count = 6;
        //目标好公司股票代码
        String targetSymbol="001286";
        //同行公司股票代码
        String targetSymbol2="600795";
        String targetSymbol3="600642";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }
}
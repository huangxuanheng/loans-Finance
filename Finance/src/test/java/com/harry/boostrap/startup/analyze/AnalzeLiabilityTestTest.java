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
        String targetSymbol="601225";
        //同行公司股票代码
        String targetSymbol2="601088";
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
        String targetSymbol2="601398";
        String targetSymbol3="601939";
        String htmlFileName= "finance.html";
        analyzeService.analysisFinanceToHtml(type, count,targetSymbol,targetSymbol2,targetSymbol3,htmlFileName);
    }
}
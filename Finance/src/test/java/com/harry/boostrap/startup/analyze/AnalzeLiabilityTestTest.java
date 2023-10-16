package com.harry.boostrap.startup.analyze;

import com.harry.boostrap.startup.analyze.enterprise.cash.CashContent;
import com.harry.boostrap.startup.analyze.enterprise.cash.CashHandler;
import com.harry.boostrap.startup.analyze.enterprise.interest.Interest;
import com.harry.boostrap.startup.analyze.enterprise.interest.InterestHandler;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability;
import com.harry.boostrap.startup.analyze.enterprise.liability.AnnualReport;
import com.harry.boostrap.startup.analyze.enterprise.liability.AssetsLiability;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnalzeLiabilityTestTest {

    @Test
    public void createHtml() throws IOException, URISyntaxException {
        String type="Q4";
        int count=5;

        analysisFinance(AnalzeLiability.getSymbol("000895"),AnalzeLiability.getSymbol("000895"),AnalzeLiability.getSymbol("000895"),type,count);
    }

    private void analysisFinance(String targetSymbol, String targetSymbol2, String targetSymbol3,String type,int count)
        throws IOException, URISyntaxException {
        //资产负债
        AnnualReport<AssetsLiability> targetAssetsLiabilitys = AnalzeLiability.getResponseData(targetSymbol, type, count);
        AnnualReport<AssetsLiability> targetAssetsLiabilitys2 = AnalzeLiability.getResponseData(targetSymbol2, type, count);
        AnnualReport<AssetsLiability> targetAssetsLiabilitys3 = AnalzeLiability.getResponseData(targetSymbol3, type, count);
        AnnualReport<Interest> targetInterests = InterestHandler.getInterest(targetSymbol, type, count);
        AnnualReport<Interest> targetInterests2 = InterestHandler.getInterest(targetSymbol2, type, count);
        AnnualReport<Interest> targetInterests3 = InterestHandler.getInterest(targetSymbol3, type, count);
        AnnualReport<CashContent> targetCashFlows = CashHandler.getCashFlow(targetSymbol, type, count);
        AnnualReport<CashContent> targetCashFlows2 = CashHandler.getCashFlow(targetSymbol2, type, count);
        AnnualReport<CashContent> targetCashFlows3 = CashHandler.getCashFlow(targetSymbol3, type, count);
        Map<String,Object>params=new HashMap<>();
        params.put("target_company_name",targetAssetsLiabilitys.getQuote_name());
        params.put("target2_company_name",targetAssetsLiabilitys2.getQuote_name());
        params.put("target3_company_name",targetAssetsLiabilitys3.getQuote_name());

        for (int x=0;x<count;x++){
            //遍历到最后一年时忽略计算
            if(x+1==count){
                break;
            }
            AssetsLiability targetAssetsLiability = targetAssetsLiabilitys.getList().get(x);
            AssetsLiability targetAssetsLiability2 = targetAssetsLiabilitys2.getList().get(x);
            AssetsLiability targetAssetsLiability3 = targetAssetsLiabilitys3.getList().get(x);
            Interest targetInterest = targetInterests.getList().get(x);
            Interest targetInterest2 = targetInterests2.getList().get(x);
            Interest targetInterest3 = targetInterests3.getList().get(x);
            CashContent targetCashFlow = targetCashFlows.getList().get(x);
            CashContent targetCashFlow2 = targetCashFlows2.getList().get(x);
            CashContent targetCashFlow3 = targetCashFlows3.getList().get(x);

            params.put("y"+(x+1),targetAssetsLiability.getReport_name());
            params.put("target"+2+"_y"+(x+1),targetAssetsLiability2.getReport_name());
            params.put("target"+3+"_y"+(x+1),targetAssetsLiability3.getReport_name());


            AssetsLiability preTargetAssetsLiability = targetAssetsLiabilitys.getList().get(x+1);
            AssetsLiability preTargetAssetsLiability2 = targetAssetsLiabilitys2.getList().get(x+1);
            AssetsLiability preTargetAssetsLiability3 = targetAssetsLiabilitys3.getList().get(x+1);
            Interest preTargetInterest = targetInterests.getList().get(x+1);
            Interest preTargetInterest2 = targetInterests2.getList().get(x+1);
            Interest preTargetInterest3 = targetInterests3.getList().get(x+1);
            CashContent preTargetCashFlow = targetCashFlows.getList().get(x+1);
            CashContent preTargetCashFlow2 = targetCashFlows2.getList().get(x+1);
            CashContent preTargetCashFlow3 = targetCashFlows3.getList().get(x+1);
            //以年为单位组装参数
            params.putAll(createHtmlParams(targetAssetsLiability,targetAssetsLiability2,targetAssetsLiability3,
                targetInterest,targetInterest2,targetInterest3,targetCashFlow,targetCashFlow2,targetCashFlow3,
                preTargetAssetsLiability,preTargetAssetsLiability2,preTargetAssetsLiability3,
                preTargetInterest,preTargetInterest2,preTargetInterest3,preTargetCashFlow,preTargetCashFlow2,preTargetCashFlow3
                ));

        }

    }

    private Map<String, ?> createHtmlParams(AssetsLiability targetAssetsLiability,
        AssetsLiability targetAssetsLiability2, AssetsLiability targetAssetsLiability3,
        Interest targetInterest, Interest targetInterest2, Interest targetInterest3,
        CashContent targetCashFlow,
        CashContent targetCashFlow2, CashContent targetCashFlow3,
        AssetsLiability preTargetAssetsLiability, AssetsLiability preTargetAssetsLiability2,
        AssetsLiability preTargetAssetsLiability3, Interest preTargetInterest,
        Interest preTargetInterest2, Interest preTargetInterest3, CashContent preTargetCashFlow,
        CashContent preTargetCashFlow2, CashContent preTargetCashFlow3) {
        Map<String, Object>param=new HashMap<>();


        return param;
    }


}
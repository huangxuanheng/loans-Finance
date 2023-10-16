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

import com.harry.boostrap.startup.analyze.utils.DataCheckNullAndAssigmentUtils;
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
        int count=6;

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
            params.put("target2_y"+(x+1),targetAssetsLiability2.getReport_name());
            params.put("target3_y"+(x+1),targetAssetsLiability3.getReport_name());


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
                preTargetInterest,preTargetInterest2,preTargetInterest3,preTargetCashFlow,preTargetCashFlow2,preTargetCashFlow3,
                    x+1
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
        CashContent preTargetCashFlow2, CashContent preTargetCashFlow3,int num) {
        Map<String, Object>param=new HashMap<>();
        String target = "y" + num + "_";
        String target2 = "target2_y" + num + "_";
        String target3 = "target3_y" + num + "_";
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target,targetAssetsLiability));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target,targetInterest));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target,targetCashFlow));

        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2,targetAssetsLiability2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2,targetInterest2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target2,targetCashFlow2));


        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3,targetAssetsLiability2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3,targetInterest2));
        param.putAll(DataCheckNullAndAssigmentUtils.assignment(target3,targetCashFlow2));

        //总资产增长率
        String total_assets_r=target+"total_assets_r";
        String total_assets_r2=target2+"total_assets_r";
        String total_assets_r3=target3+"total_assets_r";

        param.put(total_assets_r,(targetAssetsLiability.getTotal_assets().get(0)-preTargetAssetsLiability.getTotal_assets().get(0))/preTargetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(total_assets_r2,(targetAssetsLiability2.getTotal_assets().get(0)-preTargetAssetsLiability2.getTotal_assets().get(0))/preTargetAssetsLiability2.getTotal_assets().get(0)*100+"%");
        param.put(total_assets_r3,(targetAssetsLiability3.getTotal_assets().get(0)-preTargetAssetsLiability3.getTotal_assets().get(0))/preTargetAssetsLiability3.getTotal_assets().get(0)*100+"%");

        //负债率
        String total_liab_r=target+"total_liab_r";
        String total_liab_r2=target+"total_liab_r";
        String total_liab_r3=target+"total_liab_r";

        param.put(total_liab_r,(targetAssetsLiability.getTotal_liab().get(0))/targetAssetsLiability.getTotal_assets().get(0)*100+"%");
        param.put(total_liab_r2,(targetAssetsLiability2.getTotal_liab().get(0)/targetAssetsLiability2.getTotal_assets().get(0)*100+"%"));
        param.put(total_liab_r3,(targetAssetsLiability3.getTotal_liab().get(0)/targetAssetsLiability3.getTotal_assets().get(0)*100+"%"));

        //准货币资金
        String total_currency_funds=target+"total_currency_funds";
        String total_currency_funds2=target2+"total_currency_funds";
        String total_currency_funds3=target3+"total_currency_funds";
        double tcf = targetAssetsLiability.getCurrency_funds().get(0) + targetAssetsLiability.getTradable_fnncl_assets().get(0);
        double tcf2 = targetAssetsLiability2.getCurrency_funds().get(0) + targetAssetsLiability2.getTradable_fnncl_assets().get(0);
        double tcf3 = targetAssetsLiability3.getCurrency_funds().get(0) + targetAssetsLiability3.getTradable_fnncl_assets().get(0);

        param.put(total_currency_funds,getStrValue(tcf));
        param.put(total_currency_funds2,getStrValue(tcf2));
        param.put(total_currency_funds3,getStrValue(tcf3));
        //有息负债
        String total_interest_bearing_liabilities=target+"total_interest_bearing_liabilities";
        String total_interest_bearing_liabilities2=target+"total_interest_bearing_liabilities";
        String total_interest_bearing_liabilities3=target+"total_interest_bearing_liabilities";

        return param;
    }

    private String getStrValue(double value){
        double vv=value/1000000000.0;
        if(vv*100>1){
            return vv+"亿";
        }else {
            return value/10000.0+"万";
        }
    }


}
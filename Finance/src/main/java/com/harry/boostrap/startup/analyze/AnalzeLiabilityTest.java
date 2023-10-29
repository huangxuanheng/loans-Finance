package com.harry.boostrap.startup.analyze;

import com.harry.boostrap.startup.analyze.enterprise.liability.AnalzeLiability;
import com.harry.boostrap.startup.analyze.excel.AnalzeLiabilityExportExcelHandler;
import org.assertj.core.util.Lists;

import java.io.IOException;
import java.net.URISyntaxException;


/**
 * @author Harry
 * @date 2021/1/16
 * @des 描述：资产负债表分析测试
 */
public class AnalzeLiabilityTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
//        AnalzeLiability.defaultAnalzeLiability("600197");
//        AnalzeLiabilityExportExcelHandler.createExcel("600660");
//        foodDrink();
//        whiteSpirit();
//        militaryIndustry();
//        nonFerrousMetals();
//        bank();
//        catPart();
//        logisticsIndustry();
//        gold();
//        cat();
//        first();
//        jiadian();
//        guangfu();
//        hause();
//        singleExport("600660");
//        dianzizhizhao();
//        dongLiMei();
        hangyun();
    }

    private static void singleExport(String symbol) throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcelVertical(symbol);
    }

    private static void dianzizhizhao() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("电子制造",
                Lists.newArrayList("002415",
                        "002475","300433","002241","601138"
                ));
    }

    private static void hause() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("房地产",
                Lists.newArrayList("600048",
                        "000002","600606"
                ));
    }

    private static void guangfu() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("光伏概念",
                Lists.newArrayList("601012",
                        "300274","002129","600089","300450","600438"
                ));
    }

    private static void jiadian() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("家用电器",
                Lists.newArrayList("000333",
                        "000651","600690","002508","002242","002032"
                ));
    }

    private static void first() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("农林牧渔",
                Lists.newArrayList("002714",
                        "600438","002311","000876","600298","002385","002041","300087","002891","600189","300313"
                ));
    }

    private static void cat() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("新能源车",
                Lists.newArrayList("300750",
                        "600104","002594","601633","300014","600406","300124","002371","002460","300274","603799",
                        "000625","002074","002709","000733","300623","600711","688388","002407","600110","600151",
                        "002245","603290"
                ));
    }

    private static void gold() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("黄金行业",
                Lists.newArrayList("601899",
                        "600547","600489","600988","601069"));
    }

    private static void logisticsIndustry() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("物流行业",
                Lists.newArrayList("002352",
                        "002120","600233","002468","002245","600153"));
    }

    private static void catPart() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("汽车配件",
                Lists.newArrayList("600660",
                        "600741","000338","002607","002625","601965"));
    }

    private static void bank() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("银行板块",
                Lists.newArrayList("601398",
                        "600036","601166","000001","600926"));
    }

    private static void nonFerrousMetals() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("有色金属",
                Lists.newArrayList("603993",
                        "002460","603799","002466","600219","600456","600711","601600"));
    }

    private static void militaryIndustry() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("国防军工",
                Lists.newArrayList("600893",
                        "600760","000768","601989","600862","600316","000547","000738","600967"));
    }

    private static void whiteSpirit() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("白酒",
                Lists.newArrayList("600519",
                        "000858","000568","600809","002304","000596","600132","600600","000799",
                        "600559","600197"));
    }

    private static void foodDrink() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("食品饮料",
                Lists.newArrayList("603288",
                        "000895","600887","600305","603345",
                        "600872","603027"));
    }

    private static void dongLiMei() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("动力煤",
                Lists.newArrayList("601225",
                        "600985","600188"));
    }

    private static void hangyun() throws IOException, URISyntaxException {
        AnalzeLiabilityExportExcelHandler.createExcel("航运",
                Lists.newArrayList("603565",
                        "601919","601866"));
    }
}

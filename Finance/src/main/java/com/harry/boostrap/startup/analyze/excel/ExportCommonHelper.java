package com.harry.boostrap.startup.analyze.excel;


import lombok.extern.slf4j.Slf4j;


import org.apache.http.client.utils.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.*;

import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.util.StringUtils;


import java.awt.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * @author harry
 * @Date 2020/1/14 20:05
 * email huangxuanheng@163.com
 * @des
 */
@Slf4j
public class ExportCommonHelper {

    /**
     * 导出数据
     * @param columns 需要导出的列头
     * @param datas 需要导出的数据，map集合里面需要将列头的key作为map的key绑定好数据的关系，否则生成的数据将会是错乱的或者不显示
     * @param isHasSerialNumber 是否需要自动生成序号
     * @throws IOException
     */
    public static void export(String symbolName, List<ExportData> columns, List<Map<String, ExportData>>datas, boolean isHasSerialNumber) throws IOException {
        String fileName = "年报分析.xlsx";
        // 工作区
        SXSSFWorkbook wb = createXmlSheet(symbolName,columns, datas, isHasSerialNumber);
        FileOutputStream os=new FileOutputStream(fileName);
        wb.write(os);
        wb.close();
    }

    public static void export(String plate, List<ExportData> headers, List<AnalzeLiabilityExportExcel>exportExcels) throws IOException {
        String fileName = plate+".xlsx";
        // 工作区
        SXSSFWorkbook wb = createXmlSheet(plate,headers,exportExcels);
        FileOutputStream os=new FileOutputStream(fileName);
        wb.write(os);
        wb.close();
    }


    private static void createCell(SXSSFWorkbook wb, SXSSFSheet sheet, CellStyle headStyle, Map<String, CellStyle> cellStyleMap, List<ExportData> columns, List<Map<String, ExportData>> datas, int startColumnIndex, int startRowIndex){
        int columnSize = columns.size();
        int size = datas.size();
        CreationHelper factory = wb.getCreationHelper();
        //表头
        SXSSFRow row = sheet.createRow(startRowIndex);
        Drawing p = sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor = factory.createClientAnchor();

//        ClientAnchor anchor = p.createAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6);

        for (int x=startColumnIndex;x<columnSize+startColumnIndex;x++){
            ExportData header = columns.get(x-startColumnIndex);
            SXSSFCell firstCell = row.createCell(x);
            firstCell.setCellStyle(headStyle);
            firstCell.setCellType(CellType.STRING);
            firstCell.setCellValue(header.getValue().toString());

            if(!StringUtils.isEmpty(header.getComment())){
                //批注
                clientAnchor.setCol1(firstCell.getColumnIndex());
                clientAnchor.setCol2(firstCell.getColumnIndex()+1);
                clientAnchor.setRow1(row.getRowNum());
                clientAnchor.setRow2(row.getRowNum()+3);
                Comment cellComment = p.createCellComment(clientAnchor);
                RichTextString str = factory.createRichTextString(header.getComment());
                cellComment.setString(str);
                cellComment.setAuthor("harry");
                firstCell.setCellComment(cellComment);
            }
        }

        //表具体数据
        for (int x=startColumnIndex;x<size+startColumnIndex;x++){
            //从第一行开始填充数据
            row = sheet.createRow(x+1+startRowIndex-startColumnIndex);
            Map<String, ExportData> data = datas.get(x-startColumnIndex);
            for (int y=startColumnIndex;y<columnSize+startColumnIndex;y++){
                ExportData header = columns.get(y-startColumnIndex);
                ExportData exportData = data.get(header.getKey());
                Object dataValue = exportData.getValue();

                SXSSFCell firstCell = row.createCell(y);
                if(dataValue instanceof String){
                    firstCell.setCellStyle(createCellStyleStr(wb));
                    firstCell.setCellType(CellType.STRING);
                }else if(dataValue instanceof Date){
                    firstCell.setCellStyle(createCellStyleDate(wb));
                    firstCell.setCellType(CellType.STRING);
                }else{
                    firstCell.setCellStyle(createCellStyleNumber(wb));
                    firstCell.setCellType(CellType.NUMERIC);
                }

                if(exportData.getColorType()!=null){
                    Font font = wb.createFont();
                    CellStyle cellStyle = firstCell.getCellStyle();
                    switch (exportData.getColorType()){
                        case RED:
                            font.setColor(IndexedColors.RED.index);
                            break;
                        case YELLOW:
                            font.setColor(IndexedColors.DARK_YELLOW.index);
                            break;
                        case BLUE:
                            font.setColor(IndexedColors.BLUE.index);
                            break;
                        case GREEN:
                            font.setColor(IndexedColors.GREEN.index);
                            break;
                    }
                    cellStyle.setFont(font);
                }

                firstCell.setCellValue(Objects.isNull(dataValue)?"":dataValue.toString());
            }
        }
    }


    /**
     * 生成序列号
     * @param sheet
     * @param headStyle 表头显示风格
     * @param cellStyleMap 数据显示风格
     * @param size 数据对应行记录数
     */
    private static void createSerialNumber(SXSSFSheet sheet, CellStyle headStyle, Map<String, CellStyle> cellStyleMap, int size) {

        SXSSFRow row = sheet.getRow(0);
        SXSSFCell firstCell = row.createCell(0);
        firstCell.setCellStyle(headStyle);
        firstCell.setCellValue("序号");
        for (int x=0;x<size;x++){
            //列值
            row = sheet.getRow(x+1);
            if(row==null){
                row=sheet.createRow(x+1);
            }
            firstCell = row.getCell(0);
            if (firstCell==null){
                firstCell=row.createCell(0);
            }
            firstCell.setCellStyle(cellStyleMap.get("numericStyle"));
            firstCell.setCellType(CellType.STRING);
            firstCell.setCellValue(x+1);
        }
    }

    /**
     *
     *
     * @param symbolName
     * @param columns 列名数据集合，在调用该方法之前，必须要将列顺序排序好，这里不做校验
     * @param datas 行数据，map集合里面是具体每一行的数据
     * @param isHasSerialNumber
     * @return
     */
    private  static SXSSFWorkbook createXmlSheet(String symbolName, List<ExportData> columns, List<Map<String, ExportData>> datas, boolean isHasSerialNumber) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Map<String,CellStyle> cellStyleMap = createXSSFCellStyleMap(wb);

        CellStyle headStyle = createHeaderCellStyle(wb);

        SXSSFSheet sheet = createSheet(wb, symbolName.split("，")[0]);

        //表头
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell firstCell = row.createCell(0);
        firstCell.setCellStyle(headStyle);
        firstCell.setCellType(CellType.STRING);
        firstCell.setCellValue(symbolName);
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, columns.size()-1);
        sheet.addMergedRegion(region);


        //数据长度
        int size = datas.size();
        if(isHasSerialNumber){
            //表头
            createCell(wb,sheet,headStyle,cellStyleMap,columns,datas,1,0);
            createSerialNumber(sheet, headStyle, cellStyleMap, size);
        }else {
            createCell(wb, sheet,headStyle,cellStyleMap,columns,datas,0,1);
        }
        return wb;
    }

    /**
     * 创建sheet工作簿
     * @param sheetName 工作簿名称
     * @param exportExcels
     * @return
     */
    private static SXSSFWorkbook createXmlSheet(String sheetName, List<ExportData> columns, List<AnalzeLiabilityExportExcel> exportExcels) {
        SXSSFWorkbook wb = new SXSSFWorkbook();
        Map<String,CellStyle> cellStyleMap = createXSSFCellStyleMap(wb);

        CellStyle headStyle = createHeaderCellStyle(wb);

        SXSSFSheet sheet = createSheet(wb,sheetName);

        int rows=0;
        for (AnalzeLiabilityExportExcel exportExcel:exportExcels){
            SXSSFRow row = sheet.createRow(rows);
            SXSSFCell firstCell = row.createCell(0);
            firstCell.setCellStyle(headStyle);
            firstCell.setCellType(CellType.STRING);
            firstCell.setCellValue(exportExcel.getQuoteStr());
            CellRangeAddress region = new CellRangeAddress(rows, rows, 0, columns.size()-1);
            sheet.addMergedRegion(region);

            createCell(wb, sheet,headStyle,cellStyleMap,columns,exportExcel.getDataMap(),0,rows+1);
            rows+=exportExcel.getDataMap().size()+3;
        }

        return wb;
    }


    private static Map<String, CellStyle> createXSSFCellStyleMap(SXSSFWorkbook wb) {
        Map<String, CellStyle> cellStyleMap = new HashMap<>();
        //map封装单元格格式
        dataMachine( cellStyleMap,wb);
        return cellStyleMap;
    }
    //导出的输出格式
    private static void dataMachine(Map<String,CellStyle> cellStyleMap,SXSSFWorkbook wb){

        CellStyle numberStyle = createCellStyle(wb);
        numberStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.######"));

        CellStyle numericStyle = createCellStyle(wb);
        numericStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0"));

        CellStyle perNumStyle = createCellStyle(wb);
        perNumStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.0"));

        CellStyle thurNumStyle = createCellStyle(wb);
        thurNumStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.00"));

        CellStyle extNumStyle = createCellStyle(wb);
        extNumStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.000"));

        CellStyle extsNumStyle = createCellStyle(wb);
        extsNumStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.0000"));


        CellStyle stringStyle = createCellStyle(wb);
        stringStyle.setDataFormat(wb.createDataFormat().getFormat("@"));

        CellStyle dateStyle = createCellStyle(wb);
        dateStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        CellStyle usStyle = createCellStyle(wb);
        usStyle.setDataFormat(wb.createDataFormat().getFormat("\"$\"#,##0.00"));

        CellStyle usMilStyle = createCellStyle(wb);
        usMilStyle.setDataFormat(wb.createDataFormat().getFormat("\"$\"#,##0.000"));

        CellStyle ustenThStyle = createCellStyle(wb);
        ustenThStyle.setDataFormat(wb.createDataFormat().getFormat("\"$\"#,##0.0000"));

        CellStyle cnStyle = createCellStyle(wb);
        cnStyle.setDataFormat(wb.createDataFormat().getFormat("\"¥\"#,##0.00"));

        CellStyle cnMilStyle = createCellStyle(wb);
        cnMilStyle.setDataFormat(wb.createDataFormat().getFormat("\"¥\"#,##0.000"));

        CellStyle cntenThStyle = createCellStyle(wb);
        cntenThStyle.setDataFormat(wb.createDataFormat().getFormat("\"¥\"#,##0.0000"));

        CellStyle fnStyle = createCellStyle(wb);
        fnStyle.setDataFormat(wb.createDataFormat().getFormat("\"€\"#,##0.00"));

        CellStyle fnMilStyle = createCellStyle(wb);
        fnMilStyle.setDataFormat(wb.createDataFormat().getFormat("\"€\"#,##0.000"));

        CellStyle fnTenThStyle = createCellStyle(wb);
        fnTenThStyle.setDataFormat(wb.createDataFormat().getFormat("\"€\"#,##0.0000"));

        CellStyle hkStyle = createCellStyle(wb);
        hkStyle.setDataFormat(wb.createDataFormat().getFormat("\"HK$\"#,##0.00"));

        CellStyle hkMilStyle = createCellStyle(wb);
        hkMilStyle.setDataFormat(wb.createDataFormat().getFormat("\"HK$\"#,##0.000"));

        CellStyle hkTenThStyle = createCellStyle(wb);
        hkTenThStyle.setDataFormat(wb.createDataFormat().getFormat("\"HK$\"#,##0.0000"));

        CellStyle tenStyle = createCellStyle(wb);
        tenStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0%"));

        CellStyle perStyle = createCellStyle(wb);
        perStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.0%"));

        CellStyle thurStyle = createCellStyle(wb);
        thurStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.00%"));

        CellStyle extStyle = createCellStyle(wb);
        extStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.000%"));

        CellStyle extsStyle = createCellStyle(wb);
        extsStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.0000%"));

        cellStyleMap.put("numberStyle",numberStyle);
        cellStyleMap.put("numericStyle",numericStyle);
        cellStyleMap.put("perNumStyle",perNumStyle);
        cellStyleMap.put("thurNumStyle",thurNumStyle);
        cellStyleMap.put("extNumStyle",extNumStyle);
        cellStyleMap.put("extsNumStyle",extsNumStyle);
        cellStyleMap.put("stringStyle",stringStyle);
        cellStyleMap.put("dateStyle",dateStyle);
        cellStyleMap.put("usStyle",usStyle);
        cellStyleMap.put("usMilStyle",usMilStyle);
        cellStyleMap.put("ustenThStyle",ustenThStyle);
        cellStyleMap.put("cnStyle",cnStyle);
        cellStyleMap.put("cnMilStyle",cnMilStyle);
        cellStyleMap.put("cntenThStyle",cntenThStyle);
        cellStyleMap.put("fnStyle",fnStyle);
        cellStyleMap.put("fnMilStyle",fnMilStyle);
        cellStyleMap.put("fnTenThStyle",fnTenThStyle);
        cellStyleMap.put("hkStyle",hkStyle);
        cellStyleMap.put("hkMilStyle",hkMilStyle);
        cellStyleMap.put("hkTenThStyle",hkTenThStyle);
        cellStyleMap.put("tenStyle",tenStyle);
        cellStyleMap.put("perStyle",perStyle);
        cellStyleMap.put("thurStyle",thurStyle);
        cellStyleMap.put("extStyle",extStyle);
        cellStyleMap.put("extsStyle",extsStyle);
    }
    /**
     * 创建默认CellStyle
     *
     * @param wb XSSFWorkbook
     * @return CellStyle
     */
    private static CellStyle createCellStyle(SXSSFWorkbook wb) {
        CellStyle attentionStyle = wb.createCellStyle();
        attentionStyle.setAlignment(HorizontalAlignment.CENTER);
        attentionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        attentionStyle.setWrapText(true);
        return attentionStyle;
    }

    private static CellStyle createCellStyleStr(SXSSFWorkbook wb) {
        CellStyle cellStyle = createCellStyle(wb);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("@"));
        return cellStyle;
    }
    private static CellStyle createCellStyleDate(SXSSFWorkbook wb) {
        CellStyle cellStyle = createCellStyle(wb);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        return cellStyle;
    }
    private static CellStyle createCellStyleNumber(SXSSFWorkbook wb) {
        CellStyle cellStyle = createCellStyle(wb);
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("###,##0.######"));
        return cellStyle;
    }

    /**
     * 创建表头格式
     * @param wb
     * @return
     */
    private static CellStyle createHeaderCellStyle(SXSSFWorkbook wb){
        //头标题样式
        CellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //创建字体
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 13);
        headStyle.setFont(font);
        return headStyle;
    }

    /**
     * 创建sheet
     * @param wb
     * @param sheetName
     * @return
     */
    private static SXSSFSheet createSheet(SXSSFWorkbook wb, String sheetName){
        //数据表单
        SXSSFSheet sheet = wb.createSheet(sheetName);
        //设置列宽固定为4个中文字
        sheet.setDefaultColumnWidth(25);
        return sheet;
    }



    //导出列对象

    public static void exportVertical(String plate, List<ExportData> headers, List<AnalzeLiabilityExportExcel>exportExcels) throws IOException {
        String fileName = plate+ "_"+DateUtils.formatDate(new Date(),"yyyy-MM-dd") +".xlsx";
        // 工作区
        SXSSFWorkbook wb = createXmlSheetVertical(headers,exportExcels);
        FileOutputStream os=new FileOutputStream(fileName);
        wb.write(os);
        wb.close();
    }

    private static SXSSFWorkbook createXmlSheetVertical(List<ExportData> headers, List<AnalzeLiabilityExportExcel> exportExcels) {
        SXSSFWorkbook wb = new SXSSFWorkbook();

        for (int x=0;x<exportExcels.size();x++){

            AnalzeLiabilityExportExcel exportExcel = exportExcels.get(x);
            SXSSFSheet sheet = createSheet(wb,exportExcel.getCompanyName());
            SXSSFRow row = sheet.getRow(0);
            if(row==null){
                row = sheet.createRow(0);
            }

            SXSSFCell firstCell = row.createCell(0);
            firstCell.setCellType(CellType.STRING);
            firstCell.setCellValue(exportExcel.getQuoteStr());
            CellStyle cellStyle=wb.createCellStyle();
            cellStyle.setWrapText(true);
            firstCell.setCellStyle(cellStyle);
            CellRangeAddress region = new CellRangeAddress(0, 1, 0, exportExcel.getDataMap().size());
            sheet.addMergedRegion(region);
            createCellVertical(wb, sheet,headers,exportExcel.getDataMap(),0,1);
        }

        return wb;
    }


    private static void createCellVertical(SXSSFWorkbook wb, SXSSFSheet sheet,  List<ExportData> columns, List<Map<String, ExportData>> datas, int startColumnIndex, int startRowIndex){
        int rowSize = columns.size();
        int columnSize = datas.size();
        CreationHelper factory = wb.getCreationHelper();
        //表头

        Drawing p = sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor = factory.createClientAnchor();



        for (int x=startRowIndex;x<rowSize+startRowIndex;x++){
            ExportData header = columns.get(x-startRowIndex);
            SXSSFRow row = sheet.getRow(x + startRowIndex);
            if(row==null){
                row = sheet.createRow(x+startRowIndex);
            }

            SXSSFCell firstCell = row.createCell(startColumnIndex);
//            firstCell.setCellStyle(headStyle);
            firstCell.setCellType(CellType.STRING);
            firstCell.setCellValue(header.getValue().toString());

            if(!StringUtils.isEmpty(header.getComment())){
                //批注
                clientAnchor.setCol1(firstCell.getColumnIndex());
                clientAnchor.setCol2(firstCell.getColumnIndex()+1);
                clientAnchor.setRow1(row.getRowNum());
                clientAnchor.setRow2(row.getRowNum()+3);
                Comment cellComment = p.createCellComment(clientAnchor);
                RichTextString str = factory.createRichTextString(header.getComment());
                cellComment.setString(str);
                cellComment.setAuthor("harry");
                firstCell.setCellComment(cellComment);
            }
        }

        for (int i=startColumnIndex;i<columnSize+startColumnIndex;i++){
            Map<String, ExportData> data = datas.get(i-startColumnIndex);
            //表具体数据
            for (int x=startRowIndex;x<rowSize+startRowIndex;x++){
                SXSSFRow row = sheet.getRow(x+startRowIndex);
                ExportData header = columns.get(x-startRowIndex);

                ExportData exportData = data.get(header.getKey());
                Object dataValue = exportData.getValue();

                SXSSFCell firstCell = row.createCell(i+1);
                if(dataValue instanceof String){
                    firstCell.setCellStyle(createCellStyleStr(wb));
                    firstCell.setCellType(CellType.STRING);
                }else if(dataValue instanceof Date){
                    firstCell.setCellStyle(createCellStyleDate(wb));
                    firstCell.setCellType(CellType.STRING);
                }else{
                    firstCell.setCellStyle(createCellStyleNumber(wb));
                    firstCell.setCellType(CellType.NUMERIC);
                }

                if(exportData.getColorType()!=null){
                    Font font = wb.createFont();
                    CellStyle cellStyle = firstCell.getCellStyle();
                    switch (exportData.getColorType()){
                        case RED:
                            font.setColor(IndexedColors.RED.index);
                            break;
                        case YELLOW:
                            font.setColor(IndexedColors.DARK_YELLOW.index);
                            break;
                        case BLUE:
                            font.setColor(IndexedColors.BLUE.index);
                            break;
                        case GREEN:
                            font.setColor(IndexedColors.GREEN.index);
                            break;
                    }
                    cellStyle.setFont(font);
                }

                firstCell.setCellValue(Objects.isNull(dataValue)?"":dataValue.toString());
            }
        }


    }


}


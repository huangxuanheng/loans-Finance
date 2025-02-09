package com.harry.boostrap.startup.analyze.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HtmlUtils {

    public String createHtmlFile(String templateName, Map<String, Object> param,String type) {
        try {
            ClassPathResource classPathResource=new ClassPathResource(templateName);
            Reader reader=new FileReader(classPathResource.getFile());
            BufferedReader bufferedReader=new BufferedReader(reader);

            StringBuilder sb=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                AtomicReference<String>temp=new AtomicReference<>(line);
                param.forEach((k,v)->{
                    temp.set(temp.get().replace("${"+k+"}",v.toString()));
                });
                sb.append(temp.get());
            }
            bufferedReader.close();
            reader.close();
            String fileName=param.get("target_company_name")+""+type+"-同行对比-"+templateName;
            createHtmlFile(fileName,sb.toString());
            log.info("创建财务报表分析文件成功");
            return sb.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return "";
    }


    public String createHtmlFile2(String templateName, String dynamicLabels,String dynamicData1,String dynamicData2,String dynamicData3,String stockName1,String stockName2,String stockName3) {
        try {
            ClassPathResource classPathResource=new ClassPathResource(templateName);
            Reader reader=new FileReader(classPathResource.getFile());
            BufferedReader bufferedReader=new BufferedReader(reader);

            StringBuilder sb=new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            bufferedReader.close();
            reader.close();
            String fileName=templateName;
            String content = sb.toString();
            content=content.replace("{{ labels }}",dynamicLabels);
            content=content.replace("{{ data1 }}",dynamicData1);
            content=content.replace("{{ data2 }}",dynamicData2);
            content=content.replace("{{ data3 }}",dynamicData3);
            content=content.replace("{{ name1 }}",stockName1);
            content=content.replace("{{ name2 }}",stockName2);
            content=content.replace("{{ name3 }}",stockName3);
            createHtmlFile(fileName,content);
            log.info("创建指数基金/黄金价格 分析图标成功");
            return sb.toString();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return "";
    }

    private void createHtmlFile(String templateName, String emailContentStr)
        throws IOException {
        String newFileName=templateName.substring(0,templateName.indexOf("."))+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+templateName.substring(templateName.indexOf("."));
        OutputStream os =new FileOutputStream(newFileName);
        os.write(emailContentStr.getBytes());
        os.close();
    }
}

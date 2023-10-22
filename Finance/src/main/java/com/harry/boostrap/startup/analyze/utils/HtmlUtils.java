package com.harry.boostrap.startup.analyze.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
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
/*            String templateContent = sb.toString();
            AtomicReference<String>temp=new AtomicReference<>(templateContent);
            param.entrySet().stream().forEach(entry->{
                temp.set(temp.get().replace("${"+entry.getKey()+"}",entry.getValue().toString()));;
            });*/
            String fileName=param.get("target_company_name")+""+type+"-同行对比-"+templateName;
            createHtmlFile(fileName,sb.toString());
            log.info("创建财务报表分析文件成功");
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

    public static String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }
}

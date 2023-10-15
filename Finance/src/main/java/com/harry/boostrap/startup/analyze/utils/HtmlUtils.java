package com.harry.boostrap.startup.analyze.utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
@Slf4j
@Component
public class HtmlUtils {
    @Autowired
    private FreeMarkerConfigurer configurer;
    public String getHtmlFile(String templateName, Map<String, Object> param) {
        String emailContentStr = "";
        try {
            Template template = configurer.getConfiguration().getTemplate(templateName);
            emailContentStr = processTemplateIntoString(template, param);
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage(), e);
        }
        return emailContentStr;
    }

    public static String processTemplateIntoString(Template template, Object model) throws IOException, TemplateException {
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }
}

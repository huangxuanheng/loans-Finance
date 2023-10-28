package com.harry.boostrap.startup.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "warn.stock")
@Configuration
@Setter
@Getter
public class WarnStockConfig {
    private Map<String,String>symbols;
    /**
     * 文本提醒模板
     */
    private String temp;
    private String temp1;
}

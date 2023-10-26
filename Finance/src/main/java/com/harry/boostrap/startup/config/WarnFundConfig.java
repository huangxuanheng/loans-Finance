package com.harry.boostrap.startup.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@ConfigurationProperties(prefix = "warn.fund")
@Configuration
@Setter
@Getter
public class WarnFundConfig {
    private Map<String,String>symbols;

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}

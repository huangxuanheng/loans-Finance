package com.harry.boostrap.startup.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "warn.fund")
@Configuration
@Setter
@Getter
public class WarnFundConfig {
    private Map<String,String>symbols;
}

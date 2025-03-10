package com.sample.url.utility.mapper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "example")
@Data
public class AppProperty {

    private String username;
}

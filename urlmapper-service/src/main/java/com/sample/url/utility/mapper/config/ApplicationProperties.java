package com.sample.url.utility.mapper.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record ApplicationProperties(String domainName) {
}


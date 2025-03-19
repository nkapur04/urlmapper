package com.sample.url.batches.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record ApplicationProperties(String domainName, String daysToExpire) {
}
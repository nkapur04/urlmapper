package com.sample.url.utility.mapper.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
@Slf4j
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5)); // Set max attempts
        retryTemplate.registerListener(new CustomRetryListener());
        log.debug("retry template created");
        return retryTemplate;
    }
}

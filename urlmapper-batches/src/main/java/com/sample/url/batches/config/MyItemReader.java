package com.sample.url.batches.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@JobScope
public class MyItemReader implements ItemReader<String> {
    @BeforeStep
    public void init() {
        log.info("before reader");
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.info("MyItemReader");
        return "Sample";
    }
}

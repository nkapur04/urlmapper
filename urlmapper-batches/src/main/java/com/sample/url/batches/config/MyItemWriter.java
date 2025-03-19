package com.sample.url.batches.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@JobScope
@Component
@Slf4j
public class MyItemWriter implements ItemWriter {
/*    @Override
    public void write(Chunk chunk) throws Exception {
    log.info("MyItemWriter");
    }*/

    @Override
    public void write(List list) throws Exception {
        log.info("MyItemWriter");
    }
}

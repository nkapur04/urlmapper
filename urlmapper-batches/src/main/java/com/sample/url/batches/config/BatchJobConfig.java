package com.sample.url.batches.config;

import com.sample.url.batches.entities.UrlMapperEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReader<String> myItemReader;

    private final ItemWriter<String> myItemWriter;

    private final UpdateDateJobConfig updateDateJobConfig;

    private final BatchProperties batchProperties;

    @Bean
    public Job sampleJob() {
        log.info("Trigger sample Job");
        return jobBuilderFactory.get("sampleJob")
                .incrementer(new RunIdIncrementer())
                .start(actualStep())
                .build();
    }

    @Bean
    public Step firstStep() {
         return stepBuilderFactory.get("firstStep")
                .<String, String>chunk(3)
                .reader(myItemReader)
                .writer(myItemWriter)
                .build();
    }

    @Bean
    public Step actualStep() {

        return stepBuilderFactory.get("actualStep")
                .<UrlMapperEntity, UrlMapperEntity>chunk(batchProperties.chunkSize())
                .reader(updateDateJobConfig.readData(null))
                .processor(updateDateJobConfig.processor())
                .writer(updateDateJobConfig.writer())
                .build();
    }
}

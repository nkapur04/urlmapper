package com.sample.url.batches.config;

import com.sample.url.batches.entities.UrlMapperEntity;
import com.sample.url.batches.repositories.URLMapperRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class UpdateDateJobConfig {

    private final EntityManagerFactory entityManagerFactory;

    private final URLMapperRepo urlMapperRepo;

    @Bean
    @StepScope
    public JpaPagingItemReader<UrlMapperEntity> readData(@Value("#{jobParameters['effectiveCalculatedDate']}") Date effectiveCalculatedDate) {

        Map<String, Object> parameterValues = new HashMap<>();
        LocalDate endDate =LocalDate.of(9999, 12, 31);
        LocalDate localEffectiveCalculatedDate = LocalDate.ofInstant(effectiveCalculatedDate.toInstant(), ZoneId.systemDefault());
        parameterValues.put("localEffectiveCalculatedDate", localEffectiveCalculatedDate);
        parameterValues.put("endDate", endDate);

        return new JpaPagingItemReaderBuilder<UrlMapperEntity>()
                .name("urlMapperReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT url FROM UrlMapperEntity url where url.effectiveStartDate < :localEffectiveCalculatedDate and url.effectiveEndDate = :endDate")
                .parameterValues(parameterValues)
                .pageSize(2)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<UrlMapperEntity, UrlMapperEntity> processor() {
        return urlMapperEntity -> {
            // Update effective end date
            LocalDate today = LocalDate.now();
            log.info("records updating for " + urlMapperEntity);
            urlMapperEntity.setUpdatedBy("BatchUser");
            urlMapperEntity.setUpdatedDate(today);
            urlMapperEntity.setEffectiveEndDate(today.plusDays(1));
            return urlMapperEntity;
        };
    }

    @Bean
    public ItemWriter<UrlMapperEntity> writer() {
        return urlMapperEntity -> urlMapperEntity.forEach(item -> {
            log.info("Writing item: " + item);
            urlMapperRepo.save(item);
        });
    }

}

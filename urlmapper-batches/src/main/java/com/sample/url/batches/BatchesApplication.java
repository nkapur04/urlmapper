package com.sample.url.batches;

import com.sample.url.batches.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
@ConfigurationPropertiesScan
@Slf4j
public class BatchesApplication implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;
    private final ApplicationProperties applicationProperties;

    public static void main(String[] args) {
        SpringApplication.run(BatchesApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args){
        log.info("inside main run");
        /*Job job = (Job) applicationContext.getBean("sampleJob");

        LocalDate currentDate = LocalDate.now();

        log.info("2nd property: "+applicationProperties.daysToExpire());
        LocalDate effectiveCalculatedDate = currentDate.minusDays(Long.valueOf(applicationProperties.daysToExpire()));
        //LocalDate effectiveCalculatedDate = currentDate.minusDays(7);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .addDate("effectiveCalculatedDate", new SimpleDateFormat("yyyy-MM-dd").parse(effectiveCalculatedDate.toString()))
                .addString("runId", UUID.randomUUID().toString())
                .toJobParameters();

        var jobExecution = jobLauncher.run(job, jobParameters);
        var batchStatus = jobExecution.getStatus();
        while (batchStatus.isRunning()) {
            System.out.println("Still running...");
            Thread.sleep(5000L);
        }*/
    }
}

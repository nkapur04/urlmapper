package com.sample.url.batches.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BatchJobScheduler {

    private final JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;
    private final ApplicationProperties applicationProperties;

    @Scheduled(cron = "* * * * * *")
    public void myScheduler() {
        log.info("my scheduler");
        launchBatchJob();
    }

    private void launchBatchJob() {
        {
            log.info("inside main run");
            Job job = (Job) applicationContext.getBean("sampleJob");
            LocalDate currentDate = LocalDate.now();
            log.info("2nd property: " + applicationProperties.daysToExpire());
            LocalDate effectiveCalculatedDate = currentDate.minusDays(Long.valueOf(applicationProperties.daysToExpire()));

            try {
                JobParameters jobParameters = new JobParametersBuilder()
                        .addString("JobID", String.valueOf(System.currentTimeMillis()))
                        .addDate("effectiveCalculatedDate", new SimpleDateFormat("yyyy-MM-dd").parse(effectiveCalculatedDate.toString()))
                        .addString("runId", UUID.randomUUID().toString())
                        .toJobParameters();

                JobExecution jobExecution = jobLauncher.run(job, jobParameters);
                var batchStatus = jobExecution.getStatus();
                while (batchStatus.isRunning()) {
                    System.out.println("Still running...");
                    Thread.sleep(5000L);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package com.sh.year.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final CheckCompleteRuleInfoTasklet checkCompleteRuleInfoTasklet;

    /**
     * 일정을 반복 타입에 맞게 만드는 Job
     */
    @Bean
    public Job createCheckCompleteRuleInfoJob(JobRepository jobRepository, Step createCheckCompleteRuleInfoStep) {
        return new JobBuilder("checkCompleteRuleInfo", jobRepository)
                .start(createCheckCompleteRuleInfoStep)
                .build();
    }

    /**
     * 일정을 반복 타입에 맞게 만드는 Step
     */
    @Bean
    public Step createCheckCompleteRuleInfoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("createScheduleStep", jobRepository)
                .tasklet(checkCompleteRuleInfoTasklet, transactionManager)
                .build();
    }




}

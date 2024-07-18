package com.sh.year.global.batch;

import com.sh.year.domain.goal.goal.delayGoal.domain.DelayGoal;
import com.sh.year.domain.goal.goal.delayGoal.domain.repository.DelayGoalRepository;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleQueryRepositoryImpl;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import com.sh.year.domain.user.domain.Users;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
//@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final CheckDelayGoalTasklet checkDelayGoalTasklet;
    private final RuleRepository ruleRepository;
    private final RuleQueryRepositoryImpl ruleQueryRepository;
    private final DelayGoalRepository delayGoalRepository;

    /**
     * 일정을 반복 타입에 맞게 만드는 Job1
     */
    @Bean
    public Job createCheckCompleteRuleInfoJob(JobRepository jobRepository,
                                              Step createCheckCompleteRuleInfoStep,
                                              Step createCheckDelayGoalStep) {
        return new JobBuilder("checkCompleteRuleInfo", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(createCheckCompleteRuleInfoStep)
                .next(createCheckDelayGoalStep)
                .build();
    }




    /**
     * Step1 : delayGoal 생성
     */
    @Bean
    @JobScope
    public Step createCheckCompleteRuleInfoStep(JobRepository jobRepository,
                                                PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("checkCompleteRuleInfo", jobRepository)
                .<Rule, DelayGoal>chunk(500, transactionManager)
                .reader(trRuleReader())
                .processor(trRuleProcessor())
                .writer(trRuleWriter())
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<Rule> trRuleReader(){

        return new RepositoryItemReaderBuilder<Rule>()
                .name("trRuleReader")
                .repository(ruleRepository)
                .methodName("findAll")
                .pageSize(500)
                .arguments(List.of())
                .sorts(Collections.singletonMap("ruleId", Sort.Direction.ASC))
                .build();
    }

    /**
     * 여기서 rci, rai 비교 후 delayGoal 생성
     */
    @Bean
    @StepScope
    public ItemProcessor<Rule, DelayGoal> trRuleProcessor() {
        return new ItemProcessor<Rule, DelayGoal>() {
            @Override
            public DelayGoal process(Rule rule) throws Exception {


                LocalDate yesterday = LocalDate.now().minusDays(1);
                int year = yesterday.getYear();
                int month = yesterday.getMonthValue();
                int day = yesterday.getDayOfMonth();

                Rule rci = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(year, month, rule.getRuleId()).orElse(null);
                Rule rai = ruleQueryRepository.findRuleAlertInfoUsingYearAndMonth(year, month, rule.getRuleId()).orElse(null);

                if(rci != null && rai != null){
                    byte[] compDays = rci.getRuleCompleteInfoList().get(0).getCompleteDay();
                    byte[] alertDay = rai.getRuleAlertInfoList().get(0).getAlertDay();
                    // 알림일일 때
                    if(alertDay[day] == 1){
                        // 달성하지 못했다면
                        if(compDays[day] == 0){
                            // delayGoal 생성
                            Users users = rule.getSmallGoal().getBigGoal().getUsers();
                            return DelayGoal.createDelayGoal(rule, users);
                        }
                    }
                }

                return null; // null일 경우 writer에 넘기지 낳음
            }
        };
    }

    @Bean
    @StepScope
    public RepositoryItemWriter<DelayGoal> trRuleWriter() {
        return new RepositoryItemWriterBuilder<DelayGoal>()
                .repository(delayGoalRepository)
                .methodName("save")
                .build();

//        return null;

    }


    /**
     * ==========================================================================================
     * ==========================================================================================
     * ==========================================================================================
     */

    /**
     * Step2 : failGoal 생성
     */
    @Bean
    @JobScope
    public Step createCheckDelayGoalStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("checkDelayGoal", jobRepository)
                .tasklet(checkDelayGoalTasklet, transactionManager)
                .build();
    }










}

package com.sh.year.global.batch;

import com.sh.year.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@EnableScheduling // 추가
@RequiredArgsConstructor
public class BatchClient {

    private final JobLauncher jobLauncher;
    private final Job createJob;


    /**
     * step1 : delayGoal 생성
     * step2 : failGoal 생성
     *
     * @cron 매일 밤 자정에 생성
     *
     * 지연goal 성공은 createdAy -1
     */
//    @Scheduled(cron = "0/10 * * * * *") // 10초마다 실행
//    @Scheduled(cron = "0/1 * * * * ?")

    @LogTrace
    @Scheduled(cron = "1 0 0 * * *") // 00시 00분 1초에 시작
    public void runCheckCompleteRuleInfo() {
        log.info("===========================================");
        log.info("delayGoal, failGoal 배치 시작.");
        log.info("===========================================");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("date", LocalDate.now())
                .toJobParameters();

        try {
            jobLauncher.run(createJob, jobParameters);
        } catch (Exception e) {
            log.error("delayGoal, failGoal 생성 배치 실패. : ", e);
        }
    }


}

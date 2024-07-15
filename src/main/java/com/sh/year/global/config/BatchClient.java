package com.sh.year.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling // 추가
@RequiredArgsConstructor
public class BatchClient {

    private final JobLauncher jobLauncher;
    private final Job createCheckCompleteRuleInfoJob;


    /**
     * delay, fail goal 생성
     * @cron 매일 밤 자정에 생성
     */
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "0/1 * * * * ?")
    @Scheduled(cron = "0/10 * * * * *") // 10초마다 실행
    public void runCreateRepeatSchedule() {
        log.info("===========================================");
        log.info("일정에 대한 반복 배치를 시작합니다.");
        log.info("===========================================");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(createCheckCompleteRuleInfoJob, jobParameters);
        } catch (Exception e) {
            log.error("일정을 생성하는 배치를 실패하였습니다. : ", e);
        }
    }
}

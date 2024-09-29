package com.sh.year.global.batch;

import com.sh.year.domain.rule.delayrule.domain.repository.DelayRuleQueryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


/**
 * spring batch는 독립적인 transaction을 가진다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DelayToFailRuleTasklet implements Tasklet {

    private final DelayRuleQueryRepositoryImpl delayRuleQueryRepository;


    /**
     * Reader + Writer로만 구성해서 Chunk로 구현하기
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("====== DELAY -> FAIL 상태 변경 Batch========");
        LocalDate yesterday = LocalDate.now().minusDays(1);

        delayRuleQueryRepository.bulkUpdateAboutStatusDelayToFail(yesterday);

        return RepeatStatus.FINISHED;
    }


}

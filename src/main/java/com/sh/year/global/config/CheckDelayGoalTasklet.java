package com.sh.year.global.config;

import com.sh.year.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


/**
 * spring batch는 독립적인 transaction을 가진다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CheckDelayGoalTasklet implements Tasklet {

    /**
     * 여기서 로직 실행
     */
    @LogTrace
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("==============");
        log.info("====== 여긴 fail =======");
        log.info("==============");
        return RepeatStatus.FINISHED;
    }

    /**
     * 시나리오ee
     *
     * 00시 1초(자정 딱 지나서)
     * LocalDate.now().minusDays(1)로 년, 월, 일날을 가져온다.(이전날임)
     *
     * 그럼 해당하는 rai와 rci를 긁어와서 비교한다.
     * 비교해서 if(!(rai[] && rci[]))일 경우 delayGoal에 넣는다.
     *      user는 타고 올라가야겠네
     *
     *
     */
}

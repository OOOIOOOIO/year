package com.sh.year.domain.goal.goal.application;

import com.sh.year.domain.goal.goal.api.dto.req.GoalReqDto;
import com.sh.year.domain.goal.goal.domain.Goal;
import com.sh.year.domain.goal.goal.domain.repository.GoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.domain.repository.GoalRepository;
import com.sh.year.domain.goal.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalQueryRepositoryImpl goalQueryRepository;
    private final UsersRepository usersRepository;


    /**
     * 상세목표 보기
     */
    @GetMapping("/info/{goalId}")
    public void getGoalInfo(@PathVariable(value = "goalId") Long goalId){



    }

    /**
     * 목표 저장
     */
    public Long saveGoal(UserInfoFromHeaderDto userInfoFromHeaderDto, GoalReqDto goalReqDto) {

        Users users = getUsers(userInfoFromHeaderDto);

        Goal goal = Goal.createGoal(goalReqDto, users);

        int routine = goalReqDto.getRuleReqDto().getRoutine();

        if (routine == 2) {


        }
        else if (routine == 3){

        }
        else{ // routine == 1
            Rule rule = Rule.createRule(goalReqDto.getRuleReqDto(), goal); // cascade(with goal)

        }

        Goal savedGoal = goalRepository.save(goal);

        return savedGoal.getGoalId();

    }



    /**
     * 목표 수정
     */
    @PutMapping("/{goalId}")
    public void updateGoal(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 삭제
     */
    @DeleteMapping("/{goalId}")
    public void deleteGoal(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 공유 설정
     */
    @PutMapping("/share/{goalId}")
    public void updateShareStatus(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 내 목표들 보기
     */
    @GetMapping("/list")
    public void getGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){

    }


    private Users getUsers(UserInfoFromHeaderDto userInfoFromHeaderDto) {
        log.info("======getUsers=====");
        log.error(""+userInfoFromHeaderDto.getUserId());
        return usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }

}

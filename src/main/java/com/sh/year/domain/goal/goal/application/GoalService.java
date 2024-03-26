package com.sh.year.domain.goal.goal.application;

import com.sh.year.domain.goal.goal.api.dto.req.GoalReqDto;
import com.sh.year.domain.goal.goal.api.dto.req.RuleReqDto;
import com.sh.year.domain.goal.goal.domain.Goal;
import com.sh.year.domain.goal.goal.domain.repository.GoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.domain.repository.GoalRepository;
import com.sh.year.domain.goal.rule.domain.Rule;
import com.sh.year.domain.goal.rule.domain.RuleRepeatDates;
import com.sh.year.domain.goal.rule.domain.repository.RuleRepeatDatesRepository;
import com.sh.year.domain.goal.rule.domain.repository.RuleRepository;
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

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final GoalQueryRepositoryImpl goalQueryRepository;
    private final UsersRepository usersRepository;
    private final RuleRepository ruleRepository;
    private final RuleRepeatDatesRepository ruleRepeatDatesRepository;


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


        if(routine == 1){ // routine == 1
            Rule rule = Rule.createRule(goalReqDto.getRuleReqDto(), goal);
            goal.addRule(rule); // cascade(with goal)

        }
        else { // routine == 2 && 3
            Rule rule = Rule.createRule(goalReqDto.getRuleReqDto(), goal);
            List<Integer> ruleRepeatList = goalReqDto.getRuleReqDto().getRuleRepeatList();

            for(Integer date : ruleRepeatList){
                RuleRepeatDates weeklyDates = RuleRepeatDates.createRepeatDates(date);
                rule.addRepeatDates(weeklyDates); // cascade(with rule)

            }

            goal.addRule(rule); // cascade(with goal)


        }



        Goal savedGoal = goalRepository.save(goal); // 마지막으로 저장!

        return savedGoal.getGoalId();
    }



    /**
     * 목표 수정
     */
    @PutMapping("/{goalId}")
    public void updateGoal(@PathVariable(value = "goalId") Long goalId,
                           GoalReqDto goalReqDto){

        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoal));

        Rule rule = goal.getRule(); // fetch join 생각

        RuleReqDto ruleReqDto = goalReqDto.getRuleReqDto();

        int originalRoutine = rule.getRoutine();

        int updateRoutine = ruleReqDto.getRoutine();


        if(originalRoutine == 1){

            if(updateRoutine == 1){
                // 그냥 update 치면 됨
                rule.updateRule(ruleReqDto);

            }
            else { // routine : 2, 3

                // repeatDates 추가
                List<Integer> ruleRepeatList = ruleReqDto.getRuleRepeatList();
                for (Integer day : ruleRepeatList) {
                    RuleRepeatDates repeatDates = RuleRepeatDates.createRepeatDates(day);
                    rule.addRepeatDates(repeatDates);

                }

                // 그냥 update 치면 됨
                rule.updateRule(ruleReqDto);

            }
        }
        else { // routine : 2, 3
            if(updateRoutine == 1){
                // 그냥 update +
                rule.updateRule(ruleReqDto);

                // repeatDates 삭제
                List<RuleRepeatDates> ruleRepeatDatesList = ruleRepeatDatesRepository.findAllByRule(rule);
                for (RuleRepeatDates ruleRepeatDates : ruleRepeatDatesList) {
                    ruleRepeatDatesRepository.delete(ruleRepeatDates);

                }

            }
            else{ // routine : 2, 3

                // repeatDates 삭제
                List<RuleRepeatDates> ruleRepeatDatesList = ruleRepeatDatesRepository.findAllByRule(rule);
                for (RuleRepeatDates ruleRepeatDates : ruleRepeatDatesList) {
                    ruleRepeatDatesRepository.delete(ruleRepeatDates);

                }


                // repeatDates 추가
                List<Integer> ruleRepeatList = ruleReqDto.getRuleRepeatList();
                for (Integer day : ruleRepeatList) {
                    RuleRepeatDates repeatDates = RuleRepeatDates.createRepeatDates(day);
                    rule.addRepeatDates(repeatDates); // save

                }

                // 그냥 update
                rule.updateRule(ruleReqDto);


            }


        }

        // 공통 goal update
        goal.updateGoal(goalReqDto);

    }

    /**
     * 목표 삭제
     */
    @DeleteMapping("/{goalId}")
    public void deleteGoal(@PathVariable(value = "goalId") Long goalId){
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoal));

        goalRepository.delete(goal);

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

        return usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }

}

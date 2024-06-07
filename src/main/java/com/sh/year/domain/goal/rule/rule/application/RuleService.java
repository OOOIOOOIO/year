package com.sh.year.domain.goal.rule.rule.application;

import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RuleService {

    private final RuleRepository ruleRepository;

    public void updateRuleCompleteInfo(){


    }



}

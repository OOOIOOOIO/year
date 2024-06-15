package com.sh.year.api.main.controller;

import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.application.BigGoalService;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main View", description = "Main View API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainApiController {

    private final BigGoalService bigGoalService;

    /**
     * 큰목표 상세보기
     */
    @Operation(
            summary = "Main view API",
            description = "큰 목표들과 Today 보여줄 목표 보여주기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Main view"
    )
    @GetMapping("")
    public ResponseEntity<BigGoalResDto> getGoalInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){


        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}

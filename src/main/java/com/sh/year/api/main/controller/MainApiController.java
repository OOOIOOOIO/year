package com.sh.year.api.main.controller;

import com.sh.year.api.main.application.MainApiService;
import com.sh.year.api.main.controller.dto.res.MainResDto;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Main View", description = "Main View API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainApiController {

    private final MainApiService mainApiService;
    /**
     * 큰목표 상세보기
     */
    @Operation(
            summary = "Main view API",
            description = "큰 목표들과 Today 보여줄 목표 보여주기"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Main view 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<MainResDto> mainView(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
                                               @PageableDefault(size = 5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        MainResDto mainResDto = mainApiService.mainView(userInfoFromTokenDto, pageable);
        return new ResponseEntity<>(mainResDto, HttpStatus.OK);
    }


}

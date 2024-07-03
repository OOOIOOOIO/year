package com.sh.year.domain.user.api;

import com.sh.year.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.year.domain.user.application.UsersService;
import com.sh.year.global.log.LogTrace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    /**
     * 카카오 최초 로그인 후 사용자 정보 수정
     */
    @Operation(
            summary = "Update User Info",
            description = "유저 정보 수정 "
    )
    @ApiResponse(
            responseCode = "200",
            description = "유저 정보 수정에 성공하였습니다."
    )
    @LogTrace
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserInfo(@PathVariable("userId") Long userId,
                                         UserInfoUpdateReqDto userInfoUpdateReqDto){

        usersService.updateUserInfo(userId, userInfoUpdateReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }







}

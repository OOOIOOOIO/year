package com.sh.year.domain.user.api;

import com.sh.year.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.year.domain.user.application.UsersService;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User", description = "User API")
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
    @PutMapping("")
    public ResponseEntity<String> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                                 UserInfoUpdateReqDto userInfoUpdateReqDto){

        usersService.updateUserInfo(userInfoFromHeaderDto.getUserId(), userInfoUpdateReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /**
     * 유저 확인
     */
    @Operation(
            summary = "Check User Existence",
            description = "유저 존재 유무 확인 "
    )
    @ApiResponse(
            responseCode = "200",
            description = "존재 시 1, 없을 시 0 반환"
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<Integer> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        int userExist = usersService.isUserExist(userInfoFromHeaderDto);

        return new ResponseEntity<>(userExist, HttpStatus.OK);
    }






}

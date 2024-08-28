package com.sh.year.domain.user.api;

import com.sh.year.domain.user.api.dto.UserInfoResDto;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "User", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;


    /**
     * 마이페이지, 회원 정보 불러오기
     */
    @Operation(
            summary = "마이페이지, 회원 정보 조회 API",
            description = "회원 정보 조회 "
    )
    @ApiResponse(
            responseCode = "200",
            description = "회원 정보 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("")
    public ResponseEntity<UserInfoResDto> getUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        UserInfoResDto userInfo = usersService.getUserInfo(userInfoFromHeaderDto.getUserId());

        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }


    /**
     * 카카오 최초 로그인 후 & 사용자 정보 수정
     */
    @Operation(
            summary = "최초 로그인 후 & MyPage사용자 정보 수정 API",
            description = "최초 로그인 후 & MyPage사용자 정보 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "유저 정보 수정에 성공하였습니다."
    )
    @LogTrace
    @PutMapping("")
    public ResponseEntity<String> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                                 @RequestPart(value = "img", required = false) MultipartFile file,
                                                 @RequestPart(value = "userInfo") UserInfoUpdateReqDto userInfoUpdateReqDto){

        usersService.updateUserInfo(userInfoFromHeaderDto.getUserId(), userInfoUpdateReqDto, file);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    /**
     * 유저 유무 확인
     */
    @Operation(
            summary = "유저가 존재하는지 확인 API",
            description = "유저 존재 유무 확인 "
    )
    @ApiResponse(
            responseCode = "200",
            description = "존재 시 1, 없을 시 0 반환"
    )
    @LogTrace
    @GetMapping("/check")
    public ResponseEntity<Integer> updateUserInfo(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        int userExist = usersService.isUserExist(userInfoFromHeaderDto);

        return new ResponseEntity<>(userExist, HttpStatus.OK);
    }






}

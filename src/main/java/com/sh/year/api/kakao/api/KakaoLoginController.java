package com.sh.year.api.kakao.api;


import com.sh.year.api.jwt.application.TokenService;
import com.sh.year.api.kakao.api.dto.KakaoLoginResDto;
import com.sh.year.api.kakao.api.dto.KakaoUserInfoResDto;
import com.sh.year.domain.user.application.UsersService;
import com.sh.year.global.jwt.JwtUtils;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.util.kakao.KakaoLoginClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Kakao Login Controller", description = "Kakao Login API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class KakaoLoginController {

    private final KakaoLoginClient kakaoLoginClient;
    private final JwtUtils jwtUtils;
    private final UsersService usersService;
    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";


    /**
     * 신규
     * - isExist : false
     *
     * 기존
     * - isExist : true
     * @param code
     * @return
     */
    @Operation(
            summary = "Kakao Login API, RefreshToken 만료시 재로그인",
            description = "Kakao Login API, RefreshToken 만료시 재로그인"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Kakao 로그인에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/kakao")
    public ResponseEntity<KakaoLoginResDto> kakaoLogin(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoLoginClient.getAccessToken(code);

        KakaoUserInfoResDto userInfo = kakaoLoginClient.getUserInfo(kakaoAccessToken);

        // 이미 가입했는지 확인
        Long userExist = usersService.isUserExist(userInfo);
        boolean isExist = false;
        Long userId = -1L;

        if(userExist != -1L){
            isExist = true;
            userId = userExist; //기존 userId
        }
        else{
            // 유저저장
            userId = usersService.saveUserInfo(userInfo); //신규 userId
        }


        // token 발급
        String jwtAccessToken = BEARER + jwtUtils.generateAccessToken(userId, userInfo.getEmail(), userInfo.getProvider());
        String jwtRefreshToken = jwtUtils.generateRefreshToken(userId, userInfo.getEmail(), userInfo.getProvider());

        // redis 저장
        tokenService.uploadAccessTokenToRedis(jwtAccessToken, userId);
        tokenService.uploadRefreshTokenToRedis(jwtRefreshToken, userId);

        return new ResponseEntity<>(new KakaoLoginResDto(jwtAccessToken, jwtRefreshToken, isExist), HttpStatus.OK);
    }

}

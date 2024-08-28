package com.sh.year.domain.user.application;


import com.sh.year.api.kakao.api.dto.KakaoUserInfoResDto;
import com.sh.year.domain.user.api.dto.UserInfoResDto;
import com.sh.year.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.year.domain.user.domain.Role;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersQueryRepositoryImpl;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersQueryRepositoryImpl usersQueryRepository;

    @Value("${file.path}")
    private String filePath;

    /**
     * 회원 정보 불러오기
     *
     */
    public UserInfoResDto getUserInfo(Long userId){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        UrlResource profileImg = getProfileImg(users.getProfileImg());

        UserInfoResDto userInfoResDto = new UserInfoResDto(users, profileImg);

        return userInfoResDto;

    }




    /**
     * 회원 저장
     */
    public Long saveUserInfo(KakaoUserInfoResDto kakaoUserInfoResDto) {
        Users user = Users.createUser(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider(), Role.USER);

        Users savedUser = usersRepository.save(user);

        return savedUser.getUserId();
    }

    /**
     * 회원 정보 수정
     * imgFileName = userId + @ + originalFileName
     */
    public void updateUserInfo(Long userId, UserInfoUpdateReqDto userInfoUpdateReqDto, MultipartFile file) {
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        String newFileName = null;

        // upload할 img가 있다면
        if(file != null){
            //만약 이미지가 존재하지 않는다면
            if(users.getProfileImg() == null){
                // 그냥 저장
                newFileName = saveFile(file, userId);
            }
            else{
                // local에서 img 삭제 후 저장
                try {
                    File prevFile = new File(filePath +"/"+ URLDecoder.decode(users.getProfileImg(), "UTF-8"));
                    prevFile.delete();
                }
                catch (UnsupportedEncodingException e) {
                    log.error("파일 삭제 에러 : " + e.getMessage());
                }

                newFileName = saveFile(file, userId);

            }

            log.info("==== save file name : " + newFileName);
        }



        users.updateUserInfo(userInfoUpdateReqDto, newFileName);

    }


    public int isUserExist(UserInfoFromHeaderDto userInfoFromHeaderDto) {
        if(usersQueryRepository.findByEmailAndProvider(userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider()).isEmpty()){
            return 0;
        }

        return 1;
    }


    public Long isUserExist(KakaoUserInfoResDto kakaoUserInfoResDto) {
        Optional<Users> user = usersQueryRepository.findByEmailAndProvider(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider());

        if(!user.isEmpty()){
            return user.get().getUserId();
        }

        return -1L;
    }


    /**
     * 파일 저장
     */
    private String saveFile(MultipartFile file, Long userId){
        String originalFileName = file.getOriginalFilename();
        String newFileName = userId + "@" + originalFileName.substring(originalFileName.lastIndexOf("\\")+1);

        File saveFile = new File(filePath, newFileName);

        try{
            file.transferTo(saveFile);
        }
        catch (Exception e){
            log.error("===== profile img save 에러  : " + e.getMessage());
        }

        return newFileName;
    }


    /**
     * 파일 조회
     */
    private UrlResource getProfileImg(String serverProfileImg ) {
        UrlResource profileImg = null;
        try {
            profileImg = new UrlResource("file:" + filePath + "/" + serverProfileImg);

        }catch (Exception e){
            log.error("profile img 불러오기 실패 : " + e.getMessage());
        }

        return profileImg;
    }




}

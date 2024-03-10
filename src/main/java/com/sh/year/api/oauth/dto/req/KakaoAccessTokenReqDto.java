package com.sh.year.api.oauth.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccessTokenReqDto {

    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    private String client_secret;

    @Builder
    public KakaoAccessTokenReqDto(String grant_type, String client_id, String redirect_uri, String code, String client_secret) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.client_secret = client_secret;
    }

    public MultiValueMap<String, String> toMultiValueMap(){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type",this.grant_type);
        multiValueMap.add("client_id",this.client_id);
        multiValueMap.add("redirect_uri", this.redirect_uri);
        multiValueMap.add("code", this.code);
        multiValueMap.add("client_secret",this.client_secret);

        return multiValueMap;
    }
}

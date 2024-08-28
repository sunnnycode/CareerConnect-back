package com.careerconnect.backend.domain.token.business;


import com.careerconnect.backend.common.annotation.Business;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.token.controller.model.TokenResponse;
import com.careerconnect.backend.domain.token.converter.TokenConverter;
import com.careerconnect.backend.domain.token.service.TokenService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;


    /**
     * 1. user entity user Id 추출
     * 2. access, refresh token 발행
     * 3. converter -> token response로 변경
     */

    public TokenResponse issueToken(User userEntity){

        return Optional.ofNullable(userEntity)
                .map(user -> {
                    return user.getUsername();
                })
                .map(user -> {
                    String loginId = userEntity.getLoginId();
                    var accessToken = tokenService.issueAccessToken(loginId);
                    var refreshToken = tokenService.issueRefreshToken(loginId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(
                        ()-> new ApiException(ErrorCode.NULL_POINT)
                );


    }

    public String  validationAccessToken(String accessToken){
        var loginId = tokenService.validationToken(accessToken);
        return loginId;
    }

}
package com.careerconnect.backend.domain.user.token.business;


import com.careerconnect.backend.common.annotation.Business;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.user.token.controller.model.TokenResponse;
import com.careerconnect.backend.domain.user.token.converter.TokenConverter;
import com.careerconnect.backend.domain.user.token.service.TokenService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Business
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;

    private final User user;

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
                .map(userId -> {
                    var accessToken = tokenService.issueAccessToken(userId);
                    var refreshToken = tokenService.issueRefreshToken(userId);
                    return tokenConverter.toResponse(accessToken, refreshToken);
                })
                .orElseThrow(
                        ()-> new ApiException(ErrorCode.NULL_POINT)
                );


    }

//    public CustomUserDetail validationAccessToken(String accessToken){
//        //var userId = tokenService.validationToken(accessToken);
//        tokenService.validationToken(accessToken);
//        String username = extractUsernameFromToken(accessToken);
//        String location = extractUsernameFromToken(accessToken);
//        return new CustomUserDetail(username, location);
//    }

//    private String extractUsernameFromToken(String accessToken) {
//        return user.getUsername();
//        // Extract username from token
//    }
//
//    private String extractLocationFromToken(String accessToken) {
//        // Extract location from token
//        return user.getLocation();
//    }
}
package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.annotation.UserSession;
import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.token.business.TokenBusiness;
import com.careerconnect.backend.domain.user.business.UserBusiness;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="POST")
public class UserApiController {

    private final UserBusiness userBusiness;
    private final TokenBusiness tokenBusiness;


    @GetMapping("/info")
    public Api<UserResponse> info(@RequestHeader("Authorization") String accessToken) {
        String loginId = tokenBusiness.validationAccessToken(accessToken);
        if (loginId == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
        }

        // 로그인 ID를 사용하여 사용자 정보를 조회
        UserResponse response = userBusiness.info(loginId);
        return Api.OK(response);
    }

    @GetMapping("/username")
    public Api<String> getUsername(@RequestHeader("Authorization") String accessToken) {
        // 토큰을 검증하여 로그인 ID를 가져옵니다.
        String loginId = tokenBusiness.validationAccessToken(accessToken);
        if (loginId == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
        }

        // 로그인 ID를 사용하여 사용자 이름을 조회
        String username = userBusiness.getUsernameByLoginId(loginId);
        return Api.OK(username);
    }

}
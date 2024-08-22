package com.careerconnect.backend.domain.user.business;


import com.careerconnect.backend.common.annotation.Business;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.user.dto.*;
import com.careerconnect.backend.domain.user.service.UserService;
import com.careerconnect.backend.domain.user.token.business.TokenBusiness;
import com.careerconnect.backend.domain.user.token.controller.model.TokenResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Business
public class UserBusiness {
    // UserService로 이동 전 로직 처리

    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenBusiness tokenBusiness;

    /**
     * 1. 회원가입(request) -> entity
     * 2. entity -> 데이터베이스에 저장
     * 3. 저장된 엔티티 -> response
     * 4. response 반환
     */
    public UserResponse register(UserRegisterRequest request) {
        var entity = userMapper.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userMapper.toResponse(newEntity);
        return response;
    }


    /**
     * 1. userId, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성(토큰 비즈니스의 이슈토큰)
     * 4. token response
     */
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getUserId(), request.getPasswordHash());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }


    // 로그인 후 세션에 저장된 회원의 userId를 이용하여 response(닉네임, 가입날짜 등) 정보를 받고자 하는 메소드
    public UserResponse info(
            User user
    ) {
        var userEntity = userService.getUserWithThrow(user.getUserId());
        var response = userMapper.toResponse(userEntity);
        return response;

    }
}


package com.careerconnect.backend.domain.user.business;


import com.careerconnect.backend.common.annotation.Business;
import com.careerconnect.backend.domain.user.dto.*;
import com.careerconnect.backend.domain.user.service.UserService;
import com.careerconnect.backend.domain.user.token.business.TokenBusiness;
import com.careerconnect.backend.domain.user.token.controller.model.TokenResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;

    private final UserMapper userMapper;

    private final TokenBusiness tokenBusiness;


    /**
     * 사용자에 대한 가입처리 로직
     * 1. request -> entity
     * 2. entity -> save
     * 3. save Entuty -> response
     * 4. response return
     */
    public UserResponse register(UserRegisterRequest request) {

        var entity = userMapper.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userMapper.toResponse(newEntity);
        return response;


    }

    /**
     * 1. email, password 를 가지고 사용자 체크
     * 2. user entity 로그인 확인
     * 3. token 생성
     * 4. token response
     */
    public TokenResponse login(UserLoginRequest request) {
        var userEntity = userService.login(request.getUserId(), request.getPasswordHash());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;

    }

//    public UserResponse me(
//            UserDto userdto
//    ) {
//        var userEntity = userService.getUserWithThrow(userdto.getId());
//        var response = userMapper.toResponse(userEntity);
//        return response;
//
//
//    }
}


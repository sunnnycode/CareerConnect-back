package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.annotation.UserSession;
import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.db.user.User;
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

    // 로그인 한 회원(본인) 정보 확인
    @GetMapping("/info")
    public Api<UserResponse> info(
            @UserSession User user
    ){

        var response = userBusiness.info(user);
        return Api.OK(response);
    }

}
package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.annotation.UserSession;
import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.domain.user.business.UserBusiness;
import com.careerconnect.backend.domain.user.dto.UserDto;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="POST")
public class UserApiController {

    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
            @UserSession UserDto userDto
    ){
        var response = userBusiness.me(userDto);
        return Api.OK(response);
    }
}
package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.annotation.UserSession;
import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.domain.user.business.UserBusiness;
import com.careerconnect.backend.domain.user.dto.UserDto;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import com.careerconnect.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="POST")
public class UserApiController {

    private final UserBusiness userBusiness;

    //ㅁㅅ코드
//    @GetMapping("/info")
//    public Api<UserResponse> info(@RequestHeader("Authorization") String authorization){
//        UserResponse response = userService.info(authorization);
//        return Api.OK(response);
//    }

//    @GetMapping("/me")
//    public Api<UserResponse> me(
//            @UserSession User user
//    ){
//
//        var response = userBusiness.me(user);
//        return Api.OK(response);
//    }

}
package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.domain.user.business.UserBusiness;
import com.careerconnect.backend.domain.user.dto.UserLoginRequest;
import com.careerconnect.backend.domain.user.dto.UserRegisterRequest;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import com.careerconnect.backend.domain.user.service.UserService;
import com.careerconnect.backend.domain.token.controller.model.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; // 추가된 부분
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="*") // allowedHeaders 수정
public class UserOpenApiController {

    private final UserService userService;
    private final UserBusiness userBusiness;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Api<UserResponse>> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        var response = userBusiness.register(request);
        return ResponseEntity.ok(Api.OK(response));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Api<TokenResponse>> login(
            @Valid @RequestBody UserLoginRequest request
    ){
        var response = userBusiness.login(request);
        return ResponseEntity.ok(Api.OK(response));
    }

    // 특정 사용자 검색
    @GetMapping("/list/{username}")
    public ResponseEntity<Api<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse userResponse = userService.findByUsername(username);
        if (userResponse == null) {
            throw new ApiException(ErrorCode.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }
        return ResponseEntity.ok(Api.OK(userResponse));
    }
}

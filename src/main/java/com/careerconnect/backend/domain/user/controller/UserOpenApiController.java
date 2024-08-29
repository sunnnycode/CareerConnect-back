package com.careerconnect.backend.domain.user.controller;

import com.careerconnect.backend.common.api.Api;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.domain.user.business.UserBusiness;
import com.careerconnect.backend.domain.user.dto.UserLoginRequest;
import com.careerconnect.backend.domain.user.dto.UserRegisterRequest;
import com.careerconnect.backend.domain.user.dto.UserDto; // UserDto 사용
import com.careerconnect.backend.domain.user.dto.UserResponse;
import com.careerconnect.backend.domain.user.service.UserService;
import com.careerconnect.backend.domain.token.controller.model.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="*")
public class UserOpenApiController {

    private final UserService userService;
    private final UserBusiness userBusiness;

    @PostMapping("/register")
    public ResponseEntity<Api<UserResponse>> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        var response = userBusiness.register(request);  // response가 UserResponse 타입이어야 함
        return ResponseEntity.ok(Api.OK(response));
    }


    @PostMapping("/login")
    public ResponseEntity<Api<TokenResponse>> login(
            @Valid @RequestBody UserLoginRequest request
    ){
        var response = userBusiness.login(request);
        return ResponseEntity.ok(Api.OK(response));
    }

    @GetMapping("/list/{username}")
    public ResponseEntity<Api<UserDto>> getUserByUsername(@PathVariable String username) { // UserDto 사용
        UserDto userDto = userService.searchByUsername(username); // UserDto 반환
        if (userDto == null) {
            throw new ApiException(ErrorCode.NOT_FOUND, "존재하지 않는 사용자입니다.");
        }
        return ResponseEntity.ok(Api.OK(userDto));
    }
}

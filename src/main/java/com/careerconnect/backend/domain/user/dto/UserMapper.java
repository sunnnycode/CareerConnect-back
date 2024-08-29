package com.careerconnect.backend.domain.user.dto;

import com.careerconnect.backend.common.annotation.Converter;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserMapper {

    public User toEntity(UserRegisterRequest request){

        // Request 데이터 유효성 검사 추가
        if(request == null) {
            throw new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null");
        }

        // 엔티티로 변환
        return User.builder()
                .loginId(request.getLoginId())
                .username(request.getUsername())
                .passwordHash(request.getPasswordHash())
                .build();
    }

    public UserResponse toResponse(User user) {

        return Optional.ofNullable(user)
                .map(it -> UserResponse.builder()
                        .id(user.getId())
                        .loginId(user.getLoginId())
                        .username(user.getUsername())
                        .passwordHash(user.getPasswordHash())
                        .createdAt(user.getCreatedAt())
                        .lastLogin(user.getLastLogin())
                        .isActive(user.getIsActive())
                        .build())
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }
}

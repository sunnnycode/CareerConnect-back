package com.careerconnect.backend.domain.user.dto;

import com.careerconnect.backend.common.annotation.Converter;
import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserMapper {

    public User toEntity(UserRegisterRequest request){

        // to entity
        return Optional.ofNullable(request)
                .map(it -> {

                    return User.builder()
                            .userId(request.getUserId())
                            .username(request.getUsername())
                            .passwordHash(request.getPasswordHash())
                            .build();

                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserRegisterRequest Null"));
    }

    // to response
    public UserResponse toResponse(User user) {

        return Optional.ofNullable(user)
                .map(it ->{

                    return UserResponse.builder()
                            .userId(user.getUserId())
                            .username(user.getUsername())
                            .passwordHash(user.getPasswordHash())
                            .build();
                })
                .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }
}

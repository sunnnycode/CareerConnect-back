package com.careerconnect.backend.domain.user.service;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.error.UserErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.db.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;


// User 도메인 로직을 처리 하는 서비스
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public User register(User user){
        return Optional.ofNullable(user)
                .map(it ->{
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }

    public User login(
            String userId,
            String passwordHash
    ){
        var entity = getUserWithThrow(userId, passwordHash);
        return entity;
    }

    public User getUserWithThrow(
            String userId,
            String passwordHash
    ){
        return userRepository.findByUserIdAndPasswordHash(userId, passwordHash)
                .orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public User getUserWithThrow(
            String userId
    ){
        return userRepository.findInfoByUserId(
                userId
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
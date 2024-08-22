package com.careerconnect.backend.domain.user.service;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.error.UserErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.db.user.UserRepository;
import com.careerconnect.backend.domain.user.dto.UserRegisterRequest;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리 하는 서비스
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //ㅁㅅ코드
//    public void register(UserRegisterRequest request){
//        User user = new User();
//        user = new ModelMapper().map(request, user.getClass());
//        user.setCreated_at(LocalDateTime.now());
//        user.setPassword_hash(request.getPassword_hash());
//        userRepository.save(user);
//    }
//
//    public UserResponse info(String authorization) {
//        String token = authorization.substring(7);
//        UserEntity entity = Optional.ofNullable(userRepository.findByUsername(username))
//                .orElseThrow(()->new ApiException(ErrorCode.BAD_REQUEST,"정보가 없습니다."));
//        log.debug("user entity : ",entity);
//        UserInfoResponse response = new ModelMapper().map(entity,UserInfoResponse.class);
//        log.debug("user info response : ",response);
//        return response;
//
//    }


    public User register(User user){
        return Optional.ofNullable(user)
                .map(it ->{
                    //user.setStatus(UserStatus.REGISTERED);
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

//    public User getUserWithThrow(
//            int id
//    ){
//        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
//                id,
//                UserStatus.REGISTERED
//        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
//    }
}
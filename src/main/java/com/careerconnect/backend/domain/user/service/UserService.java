//package com.careerconnect.backend.domain.user.service;
//
//import com.careerconnect.backend.common.error.ErrorCode;
//import com.careerconnect.backend.common.error.UserErrorCode;
//import com.careerconnect.backend.common.exception.ApiException;
//import com.careerconnect.backend.db.user.User;
//import com.careerconnect.backend.db.user.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
///**
// * User 도메인 로직을 처리 하는 서비스
// */
//@RequiredArgsConstructor
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//
//    public User register(User user){
//        return Optional.ofNullable(user)
//                .map(it ->{
//                    user.setStatus(UserStatus.REGISTERED);
//                    return userRepository.save(user);
//                })
//                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
//    }
//
//    public User login(
//            String email,
//            String password
//    ){
//        var entity = getUserWithThrow(email, password);
//        return entity;
//    }
//
//    public User getUserWithThrow(
//            String email,
//            String password
//    ){
//        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
//                email,
//                password,
//                UserStatus.REGISTERED
//        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
//    }
//
//    public User getUserWithThrow(
//            int id
//    ){
//        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
//                id,
//                UserStatus.REGISTERED
//        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
//    }
//}
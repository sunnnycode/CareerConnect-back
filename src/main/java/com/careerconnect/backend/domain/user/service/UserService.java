package com.careerconnect.backend.domain.user.service;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.db.user.UserRepository;
import com.careerconnect.backend.domain.user.dto.UserLoginRequest;
import com.careerconnect.backend.domain.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        user.setCreatedAt(LocalDateTime.now());  // createdAt 설정
        user.setIsActive(true);  // isActive 설정
        return userRepository.save(user);
    }

    public User login(UserLoginRequest loginRequest) {
        User user = userRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        boolean passwordMatch = passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash());
        if (!passwordMatch) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        user.setLastLogin(LocalDateTime.now()); // 로그인 성공 시 lastLogin 업데이트
        userRepository.save(user);

        return user;
    }

    public User getUserWithThrow(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

    public String findUsernameByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    public UserResponse findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "존재하지 않는 사용자입니다."));
        return UserResponse.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .passwordHash(user.getPasswordHash())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .isActive(user.getIsActive())
                .build();
    }
}

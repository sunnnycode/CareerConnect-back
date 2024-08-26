package com.careerconnect.backend.domain.user.service;

import com.careerconnect.backend.common.error.ErrorCode;
import com.careerconnect.backend.common.exception.ApiException;
import com.careerconnect.backend.db.user.User;
import com.careerconnect.backend.db.user.UserRepository;
import com.careerconnect.backend.domain.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();  // BCryptPasswordEncoder 초기화
    }

    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        return userRepository.save(user);
    }

    public User login(UserLoginRequest loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));

        boolean passwordMatch = passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash());
        if (!passwordMatch) {
            throw new ApiException(ErrorCode.BAD_REQUEST);
        }

        return user;
    }

    public User getUserWithThrow(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }
}

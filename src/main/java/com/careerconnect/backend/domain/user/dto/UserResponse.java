package com.careerconnect.backend.domain.user.dto;


import com.careerconnect.backend.db.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private int id;

    private String userId;

    private String username;

    private String passwordHash;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    private Boolean isActive;



}

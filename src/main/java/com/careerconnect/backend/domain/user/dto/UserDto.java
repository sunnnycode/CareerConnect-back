package com.careerconnect.backend.domain.user.dto;

import com.careerconnect.backend.db.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserDto {

    private int id;

    private String username;

    private String email;

    private String password_hash;

    private LocalDateTime created_at;

    private LocalDateTime last_login;

    private Boolean is_active;




}

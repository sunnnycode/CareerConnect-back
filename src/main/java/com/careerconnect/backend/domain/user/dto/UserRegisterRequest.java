package com.careerconnect.backend.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {


    @NotBlank
    private String userId;

    @NotBlank
    private String username;

    @NotBlank
    private String passwordHash;


}


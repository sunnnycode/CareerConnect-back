package com.careerconnect.backend.domain.user.dto;


import com.careerconnect.backend.db.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private int id;

    private String username;

    private String email;

    //지울 것
    private String password;

    private String phoneNumber;

    private String location;


}

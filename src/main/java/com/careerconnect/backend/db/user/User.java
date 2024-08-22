package com.careerconnect.backend.db.user;


import com.careerconnect.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@SuperBuilder
@Component
public class User extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;

    private Boolean isActive;


//
//    // getter, setter
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//

}

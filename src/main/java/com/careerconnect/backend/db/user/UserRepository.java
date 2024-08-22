package com.careerconnect.backend.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserIdAndPasswordHash(String userId, String passwordHash);

    Optional<User> findInfoByUserId(String userId);

}

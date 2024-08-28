package com.careerconnect.backend.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLoginIdAndPasswordHash(String loginId, String passwordHash);

    Optional<User> findInfoByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findUsernameByLoginId(String loginId);
}

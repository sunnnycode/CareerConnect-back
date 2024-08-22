package com.careerconnect.backend.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

    Optional<User> findByUserIdAndPasswordHash(String userId, String passwordHash);

    //// select * from user where id = ? and status = ? order by id desc limit 1
    //Optional<User> findFirstByIdAndStatusOrderByIdDesc(String user_id, String password_hash);

    //// select * from user where email = ? and password = ? and status = ? order by id desc limit 1
    //Optional<User> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String user_id, String password_hash);
}

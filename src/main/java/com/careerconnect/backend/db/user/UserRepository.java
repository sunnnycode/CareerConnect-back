package com.careerconnect.backend.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);

//    // select * from user where id = ? and status = ? order by id desc limit 1
//    Optional<User> findFirstByIdAndStatusOrderByIdDesc(int id, UserStatus status);
//
//    // select * from user where email = ? and password = ? and status = ? order by id desc limit 1
//    Optional<User> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password, UserStatus status);

}

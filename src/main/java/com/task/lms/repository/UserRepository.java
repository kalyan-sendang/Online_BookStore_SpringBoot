package com.task.lms.repository;


import com.task.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserName(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameOrEmail(String username, String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);
}

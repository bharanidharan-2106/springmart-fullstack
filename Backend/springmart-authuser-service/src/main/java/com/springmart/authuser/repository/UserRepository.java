package com.springmart.authuser.repository;

import com.springmart.authuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUuid(String uuid);
    List<User> findByRole(String role);
    List<User> findByRoleAndStatus(String role, String status);
}
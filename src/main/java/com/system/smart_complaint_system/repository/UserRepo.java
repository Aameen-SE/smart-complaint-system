package com.system.smart_complaint_system.repository;

import com.system.smart_complaint_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}

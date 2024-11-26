package com.penny.penny_backend.repository;


import com.penny.penny_backend.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import com.penny.penny_backend.domain.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByUsername(String username);
    //Optional<User> findByUserId(String username);
}



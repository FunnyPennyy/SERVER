package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, Long> {
    Optional<TeacherAccount> findByAccountNum(String accountNum);
}

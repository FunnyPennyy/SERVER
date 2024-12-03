package com.penny.penny_backend.repository;

import com.penny.penny_backend.domain.Account;
import com.penny.penny_backend.domain.TeacherAccount;
import com.penny.penny_backend.domain.TeacherAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, Long> {
    Optional<TeacherAccount> findByAccountNum(String accountNum);
    Optional<TeacherAccount> findByTeacher_TeacherId(Long teacherId);
}
//
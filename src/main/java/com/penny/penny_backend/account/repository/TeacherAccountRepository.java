package com.penny.penny_backend.account.repository;

import com.penny.penny_backend.account.entity.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, Long> {
}

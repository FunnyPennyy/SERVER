package com.penny.penny_backend.account.repository;

import com.penny.penny_backend.account.entity.TeacherAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherAccountHistoryRepository extends JpaRepository<TeacherAccountHistory, Long> {
}
